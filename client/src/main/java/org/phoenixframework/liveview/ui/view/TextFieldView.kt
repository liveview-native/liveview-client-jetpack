package org.phoenixframework.liveview.ui.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.coroutines.flow.map
import org.phoenixframework.liveview.constants.Attrs.attrAutoCorrectEnabled
import org.phoenixframework.liveview.constants.Attrs.attrCapitalization
import org.phoenixframework.liveview.constants.Attrs.attrColors
import org.phoenixframework.liveview.constants.Attrs.attrImeAction
import org.phoenixframework.liveview.constants.Attrs.attrIsError
import org.phoenixframework.liveview.constants.Attrs.attrKeyboardType
import org.phoenixframework.liveview.constants.Attrs.attrMaxLines
import org.phoenixframework.liveview.constants.Attrs.attrMinLines
import org.phoenixframework.liveview.constants.Attrs.attrPhxClick
import org.phoenixframework.liveview.constants.Attrs.attrPhxValue
import org.phoenixframework.liveview.constants.Attrs.attrReadOnly
import org.phoenixframework.liveview.constants.Attrs.attrShape
import org.phoenixframework.liveview.constants.Attrs.attrSingleLine
import org.phoenixframework.liveview.constants.Attrs.attrTextStyle
import org.phoenixframework.liveview.constants.Attrs.attrValue
import org.phoenixframework.liveview.constants.Attrs.attrVisualTransformation
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrCursorColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDisabledBorderColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDisabledContainerColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDisabledIndicatorColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDisabledLabelColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDisabledLeadingIconColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDisabledPlaceholderColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDisabledPrefixColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDisabledSuffixColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDisabledSupportingTextColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDisabledTextColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDisabledTrailingIconColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrErrorBorderColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrErrorContainerColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrErrorCursorColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrErrorIndicatorColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrErrorLabelColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrErrorLeadingIconColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrErrorPlaceholderColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrErrorPrefixColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrErrorSuffixColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrErrorSupportingTextColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrErrorTextColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrErrorTrailingIconColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrFocusedBorderColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrFocusedContainerColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrFocusedIndicatorColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrFocusedLabelColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrFocusedLeadingIconColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrFocusedPlaceholderColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrFocusedPrefixColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrFocusedSuffixColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrFocusedSupportingTextColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrFocusedTextColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrFocusedTrailingIconColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrSelectionBackgroundColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrSelectionHandleColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrUnfocusedBorderColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrUnfocusedContainerColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrUnfocusedIndicatorColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrUnfocusedLabelColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrUnfocusedLeadingIconColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrUnfocusedPlaceholderColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrUnfocusedPrefixColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrUnfocusedSuffixColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrUnfocusedSupportingTextColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrUnfocusedTextColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrUnfocusedTrailingIconColor
import org.phoenixframework.liveview.constants.ComposableTypes
import org.phoenixframework.liveview.constants.ImeActionValues
import org.phoenixframework.liveview.constants.KeyboardCapitalizationValues
import org.phoenixframework.liveview.constants.KeyboardTypeValues
import org.phoenixframework.liveview.constants.Templates.templateLabel
import org.phoenixframework.liveview.constants.Templates.templateLeadingIcon
import org.phoenixframework.liveview.constants.Templates.templatePlaceholder
import org.phoenixframework.liveview.constants.Templates.templatePrefix
import org.phoenixframework.liveview.constants.Templates.templateSuffix
import org.phoenixframework.liveview.constants.Templates.templateSupportingText
import org.phoenixframework.liveview.constants.Templates.templateTrailingIcon
import org.phoenixframework.liveview.constants.VisualTransformationValues
import org.phoenixframework.liveview.extensions.toColor
import org.phoenixframework.liveview.foundation.data.core.CoreAttribute
import org.phoenixframework.liveview.foundation.domain.ComposableTreeNode
import org.phoenixframework.liveview.foundation.ui.base.CommonComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.LocalParentDataHolder
import org.phoenixframework.liveview.foundation.ui.base.PushEvent
import org.phoenixframework.liveview.foundation.ui.view.onClickFromString
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView
import org.phoenixframework.liveview.ui.theme.ThemeHolder.Companion.DISABLED_CONTAINER_ALPHA
import org.phoenixframework.liveview.ui.theme.ThemeHolder.Companion.DISABLED_CONTENT_ALPHA
import org.phoenixframework.liveview.ui.theme.shapeFromString
import org.phoenixframework.liveview.ui.theme.textStyleFromString

/**
 * Text fields allow users to enter text into a UI. They typically appear in forms and dialogs.
 * A Text field can have a few specific templates for its children:
 * - `label`: optional label to be displayed inside the text field container.
 * - `placeholder`: the optional placeholder to be displayed when the text field is in focus and
 * the input text is empty.
 * - `leadingIcon`: the optional leading icon to be displayed at the beginning of the text field
 * container.
 * - `trailingIcon`: the optional trailing icon to be displayed at the end of the text field
 * container.
 * - `prefix`: the optional prefix to be displayed before the input text in the text field.
 * - `suffix`: the optional suffix to be displayed after the input text in the text field.
 * - `supportingText`: the optional supporting text to be displayed below the text field.
 *
 * ```
 * <TextField phx-value={"#{@userName}"} phx-change="setName">
 *   <Text template="label">Label</Text>
 *   <Text template="placeholder">Placeholder</Text>
 *   <Icon template="leadingIcon" imageVector="filled:Add"/>
 *   <Icon template="trailingIcon" imageVector="filled:ChevronLeft"/>
 *   <Text template="prefix">Pre</Text>
 *   <Text template="suffix">Suf</Text>
 *   <Text template="supportingText">Supporting text</Text>
 * </TextField>
 * ```
 * You can instantiate both `TextField` and `OutlinedTextField`.
 */
internal class TextFieldView private constructor(props: Properties) :
    ChangeableView<String, TextFieldView.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?, paddingValues: PaddingValues?, pushEvent: PushEvent
    ) {
        val enabled = props.changeableProps.enabled
        val changeValueEventName = props.changeableProps.onChange
        val readOnly = props.readOnly
        val textStyle = props.textStyle
        val isError = props.isError
        val visualTransformation = props.visualTransformation
        val singleLine = props.singleLine
        val maxLines = props.maxLines
        val minLines = props.minLines
        val shape = props.shape
        val colors = props.colors
        val keyboardOptions = props.keyboardOptions
        val keyboardActions = props.keyboardActions

        val label = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateLabel }
        }
        val placeholder = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templatePlaceholder }
        }
        val leadingIcon = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateLeadingIcon }
        }
        val trailingIcon = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateTrailingIcon }
        }
        val prefix = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templatePrefix }
        }
        val suffix = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateSuffix }
        }
        val supportingText = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateSupportingText }
        }
        val phxValue = props.commonProps.phxValue
        val stringValue = remember(phxValue) {
            phxValue?.let {
                when (it) {
                    is String -> it
                    is Map<*, *> -> it[KEY_PHX_VALUE]?.toString() ?: ""
                    else -> ""
                }
            } ?: ""
        }
        var textFieldValue by remember {
            mutableStateOf(TextFieldValue(stringValue))
        }
        when (composableNode?.node?.tag) {
            ComposableTypes.outlinedTextField -> {
                OutlinedTextField(
                    value = if (readOnly) TextFieldValue(stringValue) else textFieldValue,
                    onValueChange = { value ->
                        textFieldValue = value
                    },
                    modifier = props.commonProps.modifier,
                    enabled = enabled,
                    readOnly = readOnly,
                    textStyle = textStyleFromString(textStyle),
                    label = label?.let {
                        {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        }
                    },
                    placeholder = placeholder?.let {
                        {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        }
                    },
                    leadingIcon = leadingIcon?.let {
                        {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        }
                    },
                    trailingIcon = trailingIcon?.let {
                        {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        }
                    },
                    prefix = prefix?.let {
                        {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        }
                    },
                    suffix = suffix?.let {
                        {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        }
                    },
                    supportingText = supportingText?.let {
                        {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        }
                    },
                    isError = isError,
                    visualTransformation = visualTransformation,
                    keyboardOptions = keyboardOptions,
                    keyboardActions = keyboardActions,
                    singleLine = singleLine,
                    maxLines = maxLines,
                    minLines = minLines,
                    shape = shape ?: OutlinedTextFieldDefaults.shape,
                    colors = getOutlinedTextFieldColors(colors)
                )
            }

            ComposableTypes.textField -> {
                TextField(
                    value = if (readOnly) TextFieldValue(stringValue) else textFieldValue,
                    onValueChange = { value ->
                        textFieldValue = value
                    },
                    modifier = props.commonProps.modifier,
                    enabled = enabled,
                    readOnly = readOnly,
                    textStyle = textStyleFromString(textStyle),
                    label = label?.let {
                        {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        }
                    },
                    placeholder = placeholder?.let {
                        {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        }
                    },
                    leadingIcon = leadingIcon?.let {
                        {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        }
                    },
                    trailingIcon = trailingIcon?.let {
                        {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        }
                    },
                    prefix = prefix?.let {
                        {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        }
                    },
                    suffix = suffix?.let {
                        {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        }
                    },
                    supportingText = supportingText?.let {
                        {
                            PhxLiveView(it, pushEvent, composableNode, null)
                        }
                    },
                    isError = isError,
                    visualTransformation = visualTransformation,
                    keyboardOptions = keyboardOptions,
                    keyboardActions = keyboardActions,
                    singleLine = singleLine,
                    maxLines = maxLines,
                    minLines = minLines,
                    shape = shape ?: TextFieldDefaults.shape,
                    colors = getTextFieldColors(colors)
                )
            }
        }

        val parentDataHolder = LocalParentDataHolder.current
        LaunchedEffect(composableNode?.id) {
            val initialValue = mergeValue(stringValue)
            parentDataHolder?.setValue(composableNode, initialValue)
            snapshotFlow { textFieldValue }
                .map { it.text }
                .onChangeable()
                .collect {
                    val newValue = mergeValue(it)
                    pushOnChangeEvent(pushEvent, changeValueEventName, newValue)
                    parentDataHolder?.setValue(composableNode, newValue)
                }
        }
    }

    private fun mergeValue(text: String): Any? {
        return mergeValueWithPhxValue(KEY_PHX_VALUE, text)
    }

    @Composable
    private fun getOutlinedTextFieldColors(textFieldColors: ImmutableMap<String, String>?): TextFieldColors {
        val defaultValue = OutlinedTextFieldDefaults.colors()
        return if (textFieldColors == null) {
            defaultValue
        } else {
            OutlinedTextFieldDefaults.colors(
                focusedTextColor = textFieldColors[colorAttrFocusedTextColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = textFieldColors[colorAttrUnfocusedTextColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface,
                disabledTextColor = textFieldColors[colorAttrDisabledTextColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = DISABLED_CONTENT_ALPHA),
                errorTextColor = textFieldColors[colorAttrErrorTextColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface,
                focusedContainerColor = textFieldColors[colorAttrFocusedContainerColor]?.toColor()
                    ?: Color.Transparent,
                unfocusedContainerColor = textFieldColors[colorAttrUnfocusedContainerColor]?.toColor()
                    ?: Color.Transparent,
                disabledContainerColor = textFieldColors[colorAttrDisabledContainerColor]?.toColor()
                    ?: Color.Transparent,
                errorContainerColor = textFieldColors[colorAttrErrorContainerColor]?.toColor()
                    ?: Color.Transparent,
                cursorColor = textFieldColors[colorAttrCursorColor]?.toColor()
                    ?: MaterialTheme.colorScheme.primary,
                errorCursorColor = textFieldColors[colorAttrErrorCursorColor]?.toColor()
                    ?: MaterialTheme.colorScheme.error,
                selectionColors = TextSelectionColors(
                    textFieldColors[colorAttrSelectionHandleColor]?.toColor()
                        ?: LocalTextSelectionColors.current.handleColor,
                    textFieldColors[colorAttrSelectionBackgroundColor]?.toColor()
                        ?: LocalTextSelectionColors.current.backgroundColor,
                ),
                focusedBorderColor = textFieldColors[colorAttrFocusedBorderColor]?.toColor()
                    ?: MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = textFieldColors[colorAttrUnfocusedBorderColor]?.toColor()
                    ?: MaterialTheme.colorScheme.outline,
                disabledBorderColor = textFieldColors[colorAttrDisabledBorderColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = DISABLED_CONTAINER_ALPHA),
                errorBorderColor = textFieldColors[colorAttrErrorBorderColor]?.toColor()
                    ?: MaterialTheme.colorScheme.error,
                focusedLeadingIconColor = textFieldColors[colorAttrFocusedLeadingIconColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedLeadingIconColor = textFieldColors[colorAttrUnfocusedLeadingIconColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                disabledLeadingIconColor = textFieldColors[colorAttrDisabledLeadingIconColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = DISABLED_CONTENT_ALPHA),
                errorLeadingIconColor = textFieldColors[colorAttrErrorLeadingIconColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                focusedTrailingIconColor = textFieldColors[colorAttrFocusedTrailingIconColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedTrailingIconColor = textFieldColors[colorAttrUnfocusedTrailingIconColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                disabledTrailingIconColor = textFieldColors[colorAttrDisabledTrailingIconColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = DISABLED_CONTENT_ALPHA),
                errorTrailingIconColor = textFieldColors[colorAttrErrorTrailingIconColor]?.toColor()
                    ?: MaterialTheme.colorScheme.error,
                focusedLabelColor = textFieldColors[colorAttrFocusedLabelColor]?.toColor()
                    ?: MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = textFieldColors[colorAttrUnfocusedLabelColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                disabledLabelColor = textFieldColors[colorAttrDisabledLabelColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = DISABLED_CONTENT_ALPHA),
                errorLabelColor = textFieldColors[colorAttrErrorLabelColor]?.toColor()
                    ?: MaterialTheme.colorScheme.error,
                focusedPlaceholderColor = textFieldColors[colorAttrFocusedPlaceholderColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedPlaceholderColor = textFieldColors[colorAttrUnfocusedPlaceholderColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                disabledPlaceholderColor = textFieldColors[colorAttrDisabledPlaceholderColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = DISABLED_CONTENT_ALPHA),
                errorPlaceholderColor = textFieldColors[colorAttrErrorPlaceholderColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                focusedSupportingTextColor = textFieldColors[colorAttrFocusedSupportingTextColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedSupportingTextColor = textFieldColors[colorAttrUnfocusedSupportingTextColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                disabledSupportingTextColor = textFieldColors[colorAttrDisabledSupportingTextColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = DISABLED_CONTENT_ALPHA),
                errorSupportingTextColor = textFieldColors[colorAttrErrorSupportingTextColor]?.toColor()
                    ?: MaterialTheme.colorScheme.error,
                focusedPrefixColor = textFieldColors[colorAttrFocusedPrefixColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedPrefixColor = textFieldColors[colorAttrUnfocusedPrefixColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                disabledPrefixColor = textFieldColors[colorAttrDisabledPrefixColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = DISABLED_CONTENT_ALPHA),
                errorPrefixColor = textFieldColors[colorAttrErrorPrefixColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                focusedSuffixColor = textFieldColors[colorAttrFocusedSuffixColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedSuffixColor = textFieldColors[colorAttrUnfocusedSuffixColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                disabledSuffixColor = textFieldColors[colorAttrDisabledSuffixColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = DISABLED_CONTENT_ALPHA),
                errorSuffixColor = textFieldColors[colorAttrErrorSuffixColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }

    companion object {
        @Composable
        fun getTextFieldColors(textFieldColors: ImmutableMap<String, String>?): TextFieldColors {
            val defaultValue = TextFieldDefaults.colors()
            return if (textFieldColors == null) {
                defaultValue
            } else {
                TextFieldDefaults.colors(
                    focusedTextColor = textFieldColors[colorAttrFocusedTextColor]?.toColor()
                        ?: MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = textFieldColors[colorAttrUnfocusedTextColor]?.toColor()
                        ?: MaterialTheme.colorScheme.onSurface,
                    disabledTextColor = textFieldColors[colorAttrDisabledTextColor]?.toColor()
                        ?: MaterialTheme.colorScheme.onSurface.copy(alpha = DISABLED_CONTENT_ALPHA),
                    errorTextColor = textFieldColors[colorAttrErrorTextColor]?.toColor()
                        ?: MaterialTheme.colorScheme.onSurface,
                    focusedContainerColor = textFieldColors[colorAttrFocusedContainerColor]?.toColor()
                        ?: MaterialTheme.colorScheme.surfaceVariant,
                    unfocusedContainerColor = textFieldColors[colorAttrUnfocusedContainerColor]?.toColor()
                        ?: MaterialTheme.colorScheme.surfaceVariant,
                    disabledContainerColor = textFieldColors[colorAttrDisabledContainerColor]?.toColor()
                        ?: MaterialTheme.colorScheme.surfaceVariant,
                    errorContainerColor = textFieldColors[colorAttrErrorContainerColor]?.toColor()
                        ?: MaterialTheme.colorScheme.surfaceVariant,
                    cursorColor = textFieldColors[colorAttrCursorColor]?.toColor()
                        ?: MaterialTheme.colorScheme.primary,
                    errorCursorColor = textFieldColors[colorAttrErrorCursorColor]?.toColor()
                        ?: MaterialTheme.colorScheme.error,
                    selectionColors = TextSelectionColors(
                        textFieldColors[colorAttrSelectionHandleColor]?.toColor()
                            ?: LocalTextSelectionColors.current.handleColor,
                        textFieldColors[colorAttrSelectionBackgroundColor]?.toColor()
                            ?: LocalTextSelectionColors.current.backgroundColor,
                    ),
                    focusedIndicatorColor = textFieldColors[colorAttrFocusedIndicatorColor]?.toColor()
                        ?: MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = textFieldColors[colorAttrUnfocusedIndicatorColor]?.toColor()
                        ?: MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledIndicatorColor = textFieldColors[colorAttrDisabledIndicatorColor]?.toColor()
                        ?: MaterialTheme.colorScheme.onSurface.copy(alpha = DISABLED_CONTENT_ALPHA),
                    errorIndicatorColor = textFieldColors[colorAttrErrorIndicatorColor]?.toColor()
                        ?: MaterialTheme.colorScheme.error,
                    focusedLeadingIconColor = textFieldColors[colorAttrFocusedLeadingIconColor]?.toColor()
                        ?: MaterialTheme.colorScheme.onSurfaceVariant,
                    unfocusedLeadingIconColor = textFieldColors[colorAttrUnfocusedLeadingIconColor]?.toColor()
                        ?: MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledLeadingIconColor = textFieldColors[colorAttrDisabledLeadingIconColor]?.toColor()
                        ?: MaterialTheme.colorScheme.onSurface.copy(alpha = DISABLED_CONTENT_ALPHA),
                    errorLeadingIconColor = textFieldColors[colorAttrErrorLeadingIconColor]?.toColor()
                        ?: MaterialTheme.colorScheme.onSurfaceVariant,
                    focusedTrailingIconColor = textFieldColors[colorAttrFocusedTrailingIconColor]?.toColor()
                        ?: MaterialTheme.colorScheme.onSurfaceVariant,
                    unfocusedTrailingIconColor = textFieldColors[colorAttrUnfocusedTrailingIconColor]?.toColor()
                        ?: MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledTrailingIconColor = textFieldColors[colorAttrDisabledTrailingIconColor]?.toColor()
                        ?: MaterialTheme.colorScheme.onSurface.copy(alpha = DISABLED_CONTENT_ALPHA),
                    errorTrailingIconColor = textFieldColors[colorAttrErrorTrailingIconColor]?.toColor()
                        ?: MaterialTheme.colorScheme.error,
                    focusedLabelColor = textFieldColors[colorAttrFocusedLabelColor]?.toColor()
                        ?: MaterialTheme.colorScheme.primary,
                    unfocusedLabelColor = textFieldColors[colorAttrUnfocusedLabelColor]?.toColor()
                        ?: MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledLabelColor = textFieldColors[colorAttrDisabledLabelColor]?.toColor()
                        ?: MaterialTheme.colorScheme.onSurface.copy(alpha = DISABLED_CONTENT_ALPHA),
                    errorLabelColor = textFieldColors[colorAttrErrorLabelColor]?.toColor()
                        ?: MaterialTheme.colorScheme.error,
                    focusedPlaceholderColor = textFieldColors[colorAttrFocusedPlaceholderColor]?.toColor()
                        ?: MaterialTheme.colorScheme.onSurfaceVariant,
                    unfocusedPlaceholderColor = textFieldColors[colorAttrUnfocusedPlaceholderColor]?.toColor()
                        ?: MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledPlaceholderColor = textFieldColors[colorAttrDisabledPlaceholderColor]?.toColor()
                        ?: MaterialTheme.colorScheme.onSurface.copy(alpha = DISABLED_CONTENT_ALPHA),
                    errorPlaceholderColor = textFieldColors[colorAttrErrorPlaceholderColor]?.toColor()
                        ?: MaterialTheme.colorScheme.onSurfaceVariant,
                    focusedSupportingTextColor = textFieldColors[colorAttrFocusedSupportingTextColor]?.toColor()
                        ?: MaterialTheme.colorScheme.onSurfaceVariant,
                    unfocusedSupportingTextColor = textFieldColors[colorAttrUnfocusedSupportingTextColor]?.toColor()
                        ?: MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledSupportingTextColor = textFieldColors[colorAttrDisabledSupportingTextColor]?.toColor()
                        ?: MaterialTheme.colorScheme.onSurface.copy(alpha = DISABLED_CONTENT_ALPHA),
                    errorSupportingTextColor = textFieldColors[colorAttrErrorSupportingTextColor]?.toColor()
                        ?: MaterialTheme.colorScheme.error,
                    focusedPrefixColor = textFieldColors[colorAttrFocusedPrefixColor]?.toColor()
                        ?: MaterialTheme.colorScheme.onSurfaceVariant,
                    unfocusedPrefixColor = textFieldColors[colorAttrUnfocusedPrefixColor]?.toColor()
                        ?: MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledPrefixColor = textFieldColors[colorAttrDisabledPrefixColor]?.toColor()
                        ?: MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = DISABLED_CONTENT_ALPHA),
                    errorPrefixColor = textFieldColors[colorAttrErrorPrefixColor]?.toColor()
                        ?: MaterialTheme.colorScheme.onSurfaceVariant,
                    focusedSuffixColor = textFieldColors[colorAttrFocusedSuffixColor]?.toColor()
                        ?: MaterialTheme.colorScheme.onSurfaceVariant,
                    unfocusedSuffixColor = textFieldColors[colorAttrUnfocusedSuffixColor]?.toColor()
                        ?: MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledSuffixColor = textFieldColors[colorAttrDisabledSuffixColor]?.toColor()
                        ?: MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = DISABLED_CONTENT_ALPHA),
                    errorSuffixColor = textFieldColors[colorAttrErrorSuffixColor]?.toColor()
                        ?: MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }

    @Stable
    internal data class Properties(
        val value: String = "",
        val readOnly: Boolean = false,
        val textStyle: String? = null,
        val isError: Boolean = false,
        val visualTransformation: VisualTransformation = VisualTransformation.None,
        val singleLine: Boolean = false,
        val maxLines: Int = Int.MAX_VALUE,
        val minLines: Int = 1,
        val shape: CornerBasedShape? = null,
        val colors: ImmutableMap<String, String>? = null,
        val keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
        val keyboardActions: KeyboardActions = KeyboardActions.Default,
        override val changeableProps: ChangeableProperties = ChangeableProperties(),
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : IChangeableProperties

    internal object Factory : ChangeableView.Factory() {

        /**
         * Creates a `TextFieldView` object based on the attributes and text of the input `Attributes`
         * object. TextFieldView co-relates to the TextField composable
         *
         * @param attributes the `Attributes` object to create the `TextFieldView` object from
         * @return a `TextFieldView` object based on the attributes and text of the input `Attributes`
         * object
         */
        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>, pushEvent: PushEvent?, scope: Any?
        ): TextFieldView = TextFieldView(
            attributes.fold(Properties()) { props, attribute ->
                handleChangeableAttribute(props.changeableProps, attribute)?.let {
                    props.copy(changeableProps = it)
                } ?: run {
                    when (attribute.name) {
                        attrAutoCorrectEnabled -> autoCorrectEnabled(props, attribute.value)
                        attrCapitalization -> capitalization(props, attribute.value)
                        attrColors -> colors(props, attribute.value)
                        attrImeAction -> imeAction(props, attribute.value)
                        attrIsError -> isError(props, attribute.value)
                        attrKeyboardType -> keyboardType(props, attribute.value)
                        attrMaxLines -> maxLines(props, attribute.value)
                        attrMinLines -> minLines(props, attribute.value)
                        attrPhxClick -> onKeyboardAction(props, attribute.value, pushEvent)
                        attrReadOnly -> readOnly(props, attribute.value)
                        attrShape -> shape(props, attribute.value)
                        attrSingleLine -> singleLine(props, attribute.value)
                        // FIXME style attribute is used for modifiers, so I renamed to textStyle
                        attrTextStyle -> textStyle(props, attribute.value)
                        attrVisualTransformation -> visualTransformation(props, attribute.value)
                        attrValue -> props.copy(
                            commonProps = super.setPhxValueFromAttr(
                                props.commonProps,
                                attrPhxValue,
                                attribute.value
                            )
                        )

                        else -> props.copy(
                            commonProps = handleCommonAttributes(
                                props.commonProps,
                                attribute,
                                pushEvent,
                                scope
                            )
                        )
                    }
                }
            })

        /**
         * This function allows to set a click event to the virtual keyboard action button (e.g.:
         * "Go", "Search", "Done", etc.). When the input service emits an IME action, the
         * corresponding callback is called.
         *
         * @param event event name defined on the server to handle the IME action.
         * @param pushEvent function responsible to dispatch the server call.
         */
        private fun onKeyboardAction(
            props: Properties,
            event: String,
            pushEvent: PushEvent?
        ): Properties {
            val action = onClickFromString(pushEvent, event, props.commonProps.phxValue)
            return props.copy(
                keyboardActions = KeyboardActions(
                    onDone = { action.invoke() },
                    onGo = { action.invoke() },
                    onNext = { action.invoke() },
                    onPrevious = { action.invoke() },
                    onSearch = { action.invoke() },
                    onSend = { action.invoke() },
                )
            )
        }

        /**
         * Controls the editable state of the text field. When true, the text field cannot be
         * modified. However, a user can focus it and copy text from it. Read-only text fields are
         * usually used to display pre-filled forms that a user cannot edit.
         *
         * @param readOnly true if the text field is read only, false otherwise.
         */
        private fun readOnly(props: Properties, readOnly: String): Properties {
            return props.copy(readOnly = readOnly.toBoolean())
        }

        /**
         * The style to be applied to the input text. Use the material design text styles.
         * See the available values at [org.phoenixframework.liveview.constants.ThemeTextStyleValues].
         * ```
         * <TextField style="labelSmall" />
         * ```
         * @param textStyle style to be applied to the text field.
         */
        private fun textStyle(props: Properties, textStyle: String): Properties {
            return props.copy(textStyle = textStyle)
        }

        /**
         * Indicates if the text field's current value is in error state. If set to true, the label,
         * bottom indicator and trailing icon by default will be displayed in error color.
         * ```
         * <TextField isError="true" />
         * ```
         * @param isError true if the text field is in error state, false otherwise.
         */
        private fun isError(props: Properties, isError: String): Properties {
            return props.copy(isError = isError.toBoolean())
        }

        /**
         * When true, this text field becomes a single horizontally scrolling text field instead of
         * wrapping onto multiple lines. The keyboard will be informed to not show the return key as
         * the ImeAction. Note that maxLines parameter will be ignored as the maxLines attribute
         * will be automatically set to 1.
         * ```
         * <TextField singleLine="true" />
         * ```
         * @param singleLine true if the text field is in error state, false otherwise.
         */
        private fun singleLine(props: Properties, singleLine: String): Properties {
            return props.copy(
                singleLine = singleLine.toBoolean(),
                maxLines = if (props.singleLine) 1 else Int.MAX_VALUE
            )
        }

        /**
         * The maximum height in terms of maximum number of visible lines. It is required that
         * 1 <= minLines <= maxLines. This parameter is ignored when singleLine is true.
         * ```
         * <TextField maxLines="3" />
         * ```
         * @param maxLines maximum number of visible lines.
         */
        private fun maxLines(props: Properties, maxLines: String): Properties {
            return props.copy(maxLines = maxLines.toIntOrNull() ?: Int.MAX_VALUE)
        }

        /**
         *  The minimum height in terms of minimum number of visible lines. It is required that
         *  1 <= minLines <= maxLines. This parameter is ignored when singleLine is true.
         * ```
         * <TextField minLines="2" />
         * ```
         * @param minLines minimum number of visible lines.
         */
        private fun minLines(props: Properties, minLines: String): Properties {
            return props.copy(minLines = minLines.toIntOrNull() ?: 1)
        }

        /**
         * Defines the shape of this text field's container
         * ```
         * <TextField shape="RectangleShape" />
         * ```
         * @param shape text field container's shape. See the supported values at
         * [org.phoenixframework.liveview.constants.ShapeValues], or use an integer
         * representing the curve size applied to all four corners.
         */
        private fun shape(props: Properties, shape: String): Properties {
            return props.copy(shape = shapeFromString(shape))
        }

        /**
         * Transforms the visual representation of the input value. For example, you can use
         * "password" to create a password text field. By default, no visual transformation is
         * applied.
         * ```
         * <TextField visualTransformation="password" />
         * ```
         * @param transformation `password` for password text fields, or `none` for a regular one
         * (default).
         */
        private fun visualTransformation(props: Properties, transformation: String): Properties {
            return props.copy(
                visualTransformation = when (transformation) {
                    VisualTransformationValues.password ->
                        PasswordVisualTransformation()

                    else -> VisualTransformation.None
                }
            )
        }

        /**
         * Set TextField colors.
         * ```
         * <TextField
         *   colors="{'focusedContainerColor': '#FFFF0000', 'focusedTextColor': '#FF00FF00'}">
         *   ...
         * </Button>
         * ```
         * @param colors an JSON formatted string, containing the text field colors. The color keys
         * supported are: `focusedTextColor`, `unfocusedTextColor`, `disabledTextColor,
         * `errorTextColor`, `focusedContainerColor`, `unfocusedContainerColor`,
         * `disabledContainerColor`, `errorContainerColor`, `cursorColor`, `errorCursorColor`,
         * `selectionHandleColor`, `selectionBackgroundColor`, `focusedLeadingIconColor`,
         * `unfocusedLeadingIconColor`, `disabledLeadingIconColor`,
         * `errorLeadingIconColor`, `focusedTrailingIconColor`, `unfocusedTrailingIconColor`,
         * `disabledTrailingIconColor`, `errorTrailingIconColor`, `focusedLabelColor`,
         * `unfocusedLabelColor`, `disabledLabelColor`, `errorLabelColor`, `focusedPlaceholderColor`,
         * `unfocusedPlaceholderColor`, `disabledPlaceholderColor`, `errorPlaceholderColor`,
         * `focusedSupportingTextColor`, `unfocusedSupportingTextColor`,
         * `disabledSupportingTextColor`, `errorSupportingTextColor`, `focusedPrefixColor`,
         * `unfocusedPrefixColor`, `disabledPrefixColor`, `errorPrefixColor`, `focusedSuffixColor`,
         * `unfocusedSuffixColor`, `disabledSuffixColor`, and `errorSuffixColor`.
         * - `TextField` specific are: `focusedIndicatorColor`, `unfocusedIndicatorColor`,
         * `disabledIndicatorColor`, and `errorIndicatorColor`.
         * - `OutlinedTextField` specific are: `focusedBorderColor`, `unfocusedBorderColor,
         * `disabledBorderColor`, and `errorBorderColor`
         */
        private fun colors(props: Properties, colors: String): Properties {
            return if (colors.isNotEmpty()) {
                return props.copy(colors = colorsFromString(colors)?.toImmutableMap())
            } else props
        }

        /**
         * Informs the keyboard whether to automatically capitalize characters, words or sentences.
         * Only applicable to only text based KeyboardTypes such as KeyboardType.Text,
         * KeyboardType.Ascii. It will not be applied to KeyboardTypes such as KeyboardType.Number.
         * ```
         * <TextField capitalization="Words" />
         * ```
         * @param capitalization capitalization type. See the supported values at
         * [org.phoenixframework.liveview.constants.KeyboardCapitalizationValues].
         */
        private fun capitalization(props: Properties, capitalization: String): Properties {
            val keyboardOptions = props.keyboardOptions
            return props.copy(
                keyboardOptions = when (capitalization) {
                    KeyboardCapitalizationValues.characters ->
                        keyboardOptions.copy(capitalization = KeyboardCapitalization.Characters)

                    KeyboardCapitalizationValues.words ->
                        keyboardOptions.copy(capitalization = KeyboardCapitalization.Words)

                    KeyboardCapitalizationValues.sentences ->
                        keyboardOptions.copy(capitalization = KeyboardCapitalization.Sentences)

                    else -> keyboardOptions.copy(capitalization = KeyboardCapitalization.None)
                }
            )
        }

        /**
         * Informs the keyboard whether to enable auto correct. Only applicable to text based
         * KeyboardTypes such as KeyboardType.Email, KeyboardType.Uri.
         * It will not be applied to KeyboardTypes such as KeyboardType.Number.
         * Most of keyboard implementations ignore this value for KeyboardTypes such as
         * KeyboardType.Text.
         * ```
         * <TextField autoCorrectEnabled="true" />
         * ```
         * @param autoCorrect true to enable auto correct, false otherwise.
         */
        private fun autoCorrectEnabled(props: Properties, autoCorrect: String): Properties {
            val keyboardOptions = props.keyboardOptions
            return props.copy(
                keyboardOptions = keyboardOptions.copy(
                    autoCorrectEnabled = autoCorrect.toBoolean()
                )
            )
        }

        /**
         * The keyboard type to be used in this text field. Note that this input type is honored by
         * keyboard and shows corresponding keyboard but this is not guaranteed. For example, some
         * keyboards may send non-ASCII character even if you set KeyboardType.Ascii.
         * ```
         * <TextField keyboardType="Email" />
         * ```
         * @param keyboardType the keyboard type. See the supported values at
         * [org.phoenixframework.liveview.constants.KeyboardTypeValues]
         */
        private fun keyboardType(props: Properties, keyboardType: String): Properties {
            val keyboardOptions = props.keyboardOptions
            return props.copy(
                keyboardOptions = when (keyboardType) {
                    KeyboardTypeValues.ascii -> keyboardOptions.copy(keyboardType = KeyboardType.Ascii)
                    KeyboardTypeValues.number -> keyboardOptions.copy(keyboardType = KeyboardType.Number)
                    KeyboardTypeValues.phone -> keyboardOptions.copy(keyboardType = KeyboardType.Phone)
                    KeyboardTypeValues.uri -> keyboardOptions.copy(keyboardType = KeyboardType.Uri)
                    KeyboardTypeValues.email -> keyboardOptions.copy(keyboardType = KeyboardType.Email)
                    KeyboardTypeValues.password -> keyboardOptions.copy(keyboardType = KeyboardType.Password)
                    KeyboardTypeValues.numberPassword -> keyboardOptions.copy(keyboardType = KeyboardType.NumberPassword)
                    KeyboardTypeValues.decimal -> keyboardOptions.copy(keyboardType = KeyboardType.Decimal)
                    else -> keyboardOptions.copy(keyboardType = KeyboardType.Text)
                }
            )
        }

        /**
         * The IME action. This IME action is honored by keyboard and may show specific icons on the
         * keyboard. For example, search icon may be shown if ImeAction. Search is specified. When
         * ImeOptions.singleLine is false, the keyboard might show return key rather than the
         * action requested here.
         * ```
         * <TextField imeAction="Search" />
         * ```
         * @param imeAction IME action. See supported values at
         * [org.phoenixframework.liveview.constants.ImeActionValues].
         */
        private fun imeAction(props: Properties, imeAction: String): Properties {
            val keyboardOptions = props.keyboardOptions
            return props.copy(
                keyboardOptions = when (imeAction) {
                    ImeActionValues.none -> keyboardOptions.copy(imeAction = ImeAction.None)
                    ImeActionValues.go -> keyboardOptions.copy(imeAction = ImeAction.Go)
                    ImeActionValues.search -> keyboardOptions.copy(imeAction = ImeAction.Search)
                    ImeActionValues.send -> keyboardOptions.copy(imeAction = ImeAction.Send)
                    ImeActionValues.previous -> keyboardOptions.copy(imeAction = ImeAction.Previous)
                    ImeActionValues.next -> keyboardOptions.copy(imeAction = ImeAction.Next)
                    ImeActionValues.done -> keyboardOptions.copy(imeAction = ImeAction.Done)
                    else -> keyboardOptions.copy(imeAction = ImeAction.Default)
                }
            )
        }
    }
}