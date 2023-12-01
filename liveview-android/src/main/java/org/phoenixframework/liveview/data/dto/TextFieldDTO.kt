package org.phoenixframework.liveview.data.dto

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
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.coroutines.flow.map
import org.phoenixframework.liveview.data.constants.Attrs.attrAutoCorrect
import org.phoenixframework.liveview.data.constants.Attrs.attrCapitalization
import org.phoenixframework.liveview.data.constants.Attrs.attrColors
import org.phoenixframework.liveview.data.constants.Attrs.attrImeAction
import org.phoenixframework.liveview.data.constants.Attrs.attrIsError
import org.phoenixframework.liveview.data.constants.Attrs.attrKeyboardType
import org.phoenixframework.liveview.data.constants.Attrs.attrMaxLines
import org.phoenixframework.liveview.data.constants.Attrs.attrMinLines
import org.phoenixframework.liveview.data.constants.Attrs.attrPhxClick
import org.phoenixframework.liveview.data.constants.Attrs.attrReadOnly
import org.phoenixframework.liveview.data.constants.Attrs.attrShape
import org.phoenixframework.liveview.data.constants.Attrs.attrSingleLine
import org.phoenixframework.liveview.data.constants.Attrs.attrStyle
import org.phoenixframework.liveview.data.constants.Attrs.attrText
import org.phoenixframework.liveview.data.constants.Attrs.attrVisualTransformation
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrCursorColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledBorderColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledIndicatorColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledLabelColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledLeadingIconColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledPlaceholderColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledPrefixColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledSuffixColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledSupportingTextColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledTextColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledTrailingIconColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrErrorBorderColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrErrorContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrErrorCursorColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrErrorIndicatorColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrErrorLabelColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrErrorLeadingIconColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrErrorPlaceholderColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrErrorPrefixColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrErrorSuffixColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrErrorSupportingTextColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrErrorTextColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrErrorTrailingIconColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrFocusedBorderColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrFocusedContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrFocusedIndicatorColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrFocusedLabelColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrFocusedLeadingIconColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrFocusedPlaceholderColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrFocusedPrefixColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrFocusedSuffixColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrFocusedSupportingTextColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrFocusedTextColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrFocusedTrailingIconColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrSelectionBackgroundColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrSelectionHandleColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrUnfocusedBorderColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrUnfocusedContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrUnfocusedIndicatorColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrUnfocusedLabelColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrUnfocusedLeadingIconColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrUnfocusedPlaceholderColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrUnfocusedPrefixColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrUnfocusedSuffixColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrUnfocusedSupportingTextColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrUnfocusedTextColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrUnfocusedTrailingIconColor
import org.phoenixframework.liveview.data.constants.Templates.templateLabel
import org.phoenixframework.liveview.data.constants.Templates.templateLeadingIcon
import org.phoenixframework.liveview.data.constants.Templates.templatePlaceholder
import org.phoenixframework.liveview.data.constants.Templates.templatePrefix
import org.phoenixframework.liveview.data.constants.Templates.templateSuffix
import org.phoenixframework.liveview.data.constants.Templates.templateSupportingText
import org.phoenixframework.liveview.data.constants.Templates.templateTrailingIcon
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.data.mappers.JsonParser
import org.phoenixframework.liveview.domain.ThemeHolder.disabledContainerAlpha
import org.phoenixframework.liveview.domain.ThemeHolder.disabledContentAlpha
import org.phoenixframework.liveview.domain.base.ComposableTypes
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView
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
 * <TextField text={"#{@userName}"} phx-change="setName">
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
internal class TextFieldDTO private constructor(builder: Builder) : ChangeableDTO<String>(builder) {
    private val readOnly = builder.readOnly
    private val textStyle = builder.textStyle
    private val isError = builder.isError
    private val visualTransformation = builder.visualTransformation
    private val singleLine = builder.singleLine
    private val maxLines = builder.maxLines
    private val minLines = builder.minLines
    private val shape = builder.shape
    private val colors = builder.colors?.toImmutableMap()
    private val keyboardOptions = builder.keyboardOptions
    private val keyboardActions = builder.keyboardActions

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?, paddingValues: PaddingValues?, pushEvent: PushEvent
    ) {
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
        var textFieldValue by remember {
            mutableStateOf(TextFieldValue(value))
        }
        when (composableNode?.node?.tag) {
            ComposableTypes.outlinedTextField -> {
                OutlinedTextField(
                    value = if (readOnly) TextFieldValue(value) else textFieldValue,
                    onValueChange = { value ->
                        textFieldValue = value
                    },
                    modifier = modifier,
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
                    colors = getOutlinedTextFieldColors(colors)
                )
            }

            ComposableTypes.textField -> {
                TextField(
                    value = if (readOnly) TextFieldValue(value) else textFieldValue,
                    onValueChange = { value ->
                        textFieldValue = value
                    },
                    modifier = modifier,
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

        LaunchedEffect(composableNode) {
            changeValueEventName?.let { event ->
                snapshotFlow { textFieldValue }.map { it.text }.onChangeable().collect { value ->
                    pushOnChangeEvent(pushEvent, event, value)
                }
            }
        }
    }

    @Composable
    private fun getTextFieldColors(textFieldColors: ImmutableMap<String, String>?): TextFieldColors {
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
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = disabledContentAlpha),
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
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = disabledContentAlpha),
                errorIndicatorColor = textFieldColors[colorAttrErrorIndicatorColor]?.toColor()
                    ?: MaterialTheme.colorScheme.error,
                focusedLeadingIconColor = textFieldColors[colorAttrFocusedLeadingIconColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedLeadingIconColor = textFieldColors[colorAttrUnfocusedLeadingIconColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                disabledLeadingIconColor = textFieldColors[colorAttrDisabledLeadingIconColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = disabledContentAlpha),
                errorLeadingIconColor = textFieldColors[colorAttrErrorLeadingIconColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                focusedTrailingIconColor = textFieldColors[colorAttrFocusedTrailingIconColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedTrailingIconColor = textFieldColors[colorAttrUnfocusedTrailingIconColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                disabledTrailingIconColor = textFieldColors[colorAttrDisabledTrailingIconColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = disabledContentAlpha),
                errorTrailingIconColor = textFieldColors[colorAttrErrorTrailingIconColor]?.toColor()
                    ?: MaterialTheme.colorScheme.error,
                focusedLabelColor = textFieldColors[colorAttrFocusedLabelColor]?.toColor()
                    ?: MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = textFieldColors[colorAttrUnfocusedLabelColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                disabledLabelColor = textFieldColors[colorAttrDisabledLabelColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = disabledContentAlpha),
                errorLabelColor = textFieldColors[colorAttrErrorLabelColor]?.toColor()
                    ?: MaterialTheme.colorScheme.error,
                focusedPlaceholderColor = textFieldColors[colorAttrFocusedPlaceholderColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedPlaceholderColor = textFieldColors[colorAttrUnfocusedPlaceholderColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                disabledPlaceholderColor = textFieldColors[colorAttrDisabledPlaceholderColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = disabledContentAlpha),
                errorPlaceholderColor = textFieldColors[colorAttrErrorPlaceholderColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                focusedSupportingTextColor = textFieldColors[colorAttrFocusedSupportingTextColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedSupportingTextColor = textFieldColors[colorAttrUnfocusedSupportingTextColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                disabledSupportingTextColor = textFieldColors[colorAttrDisabledSupportingTextColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = disabledContentAlpha),
                errorSupportingTextColor = textFieldColors[colorAttrErrorSupportingTextColor]?.toColor()
                    ?: MaterialTheme.colorScheme.error,
                focusedPrefixColor = textFieldColors[colorAttrFocusedPrefixColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedPrefixColor = textFieldColors[colorAttrUnfocusedPrefixColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                disabledPrefixColor = textFieldColors[colorAttrDisabledPrefixColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = disabledContentAlpha),
                errorPrefixColor = textFieldColors[colorAttrErrorPrefixColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                focusedSuffixColor = textFieldColors[colorAttrFocusedSuffixColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedSuffixColor = textFieldColors[colorAttrUnfocusedSuffixColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                disabledSuffixColor = textFieldColors[colorAttrDisabledSuffixColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = disabledContentAlpha),
                errorSuffixColor = textFieldColors[colorAttrErrorSuffixColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
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
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = disabledContentAlpha),
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
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = disabledContainerAlpha),
                errorBorderColor = textFieldColors[colorAttrErrorBorderColor]?.toColor()
                    ?: MaterialTheme.colorScheme.error,
                focusedLeadingIconColor = textFieldColors[colorAttrFocusedLeadingIconColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedLeadingIconColor = textFieldColors[colorAttrUnfocusedLeadingIconColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                disabledLeadingIconColor = textFieldColors[colorAttrDisabledLeadingIconColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = disabledContentAlpha),
                errorLeadingIconColor = textFieldColors[colorAttrErrorLeadingIconColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                focusedTrailingIconColor = textFieldColors[colorAttrFocusedTrailingIconColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedTrailingIconColor = textFieldColors[colorAttrUnfocusedTrailingIconColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                disabledTrailingIconColor = textFieldColors[colorAttrDisabledTrailingIconColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = disabledContentAlpha),
                errorTrailingIconColor = textFieldColors[colorAttrErrorTrailingIconColor]?.toColor()
                    ?: MaterialTheme.colorScheme.error,
                focusedLabelColor = textFieldColors[colorAttrFocusedLabelColor]?.toColor()
                    ?: MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = textFieldColors[colorAttrUnfocusedLabelColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                disabledLabelColor = textFieldColors[colorAttrDisabledLabelColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = disabledContentAlpha),
                errorLabelColor = textFieldColors[colorAttrErrorLabelColor]?.toColor()
                    ?: MaterialTheme.colorScheme.error,
                focusedPlaceholderColor = textFieldColors[colorAttrFocusedPlaceholderColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedPlaceholderColor = textFieldColors[colorAttrUnfocusedPlaceholderColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                disabledPlaceholderColor = textFieldColors[colorAttrDisabledPlaceholderColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = disabledContentAlpha),
                errorPlaceholderColor = textFieldColors[colorAttrErrorPlaceholderColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                focusedSupportingTextColor = textFieldColors[colorAttrFocusedSupportingTextColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedSupportingTextColor = textFieldColors[colorAttrUnfocusedSupportingTextColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                disabledSupportingTextColor = textFieldColors[colorAttrDisabledSupportingTextColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = disabledContentAlpha),
                errorSupportingTextColor = textFieldColors[colorAttrErrorSupportingTextColor]?.toColor()
                    ?: MaterialTheme.colorScheme.error,
                focusedPrefixColor = textFieldColors[colorAttrFocusedPrefixColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedPrefixColor = textFieldColors[colorAttrUnfocusedPrefixColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                disabledPrefixColor = textFieldColors[colorAttrDisabledPrefixColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = disabledContentAlpha),
                errorPrefixColor = textFieldColors[colorAttrErrorPrefixColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                focusedSuffixColor = textFieldColors[colorAttrFocusedSuffixColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedSuffixColor = textFieldColors[colorAttrUnfocusedSuffixColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                disabledSuffixColor = textFieldColors[colorAttrDisabledSuffixColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = disabledContentAlpha),
                errorSuffixColor = textFieldColors[colorAttrErrorSuffixColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }

    internal class Builder : ChangeableDTOBuilder<String>("") {
        var readOnly: Boolean = false
            private set
        var textStyle: String? = null
            private set
        var isError: Boolean = false
            private set
        var visualTransformation: VisualTransformation = VisualTransformation.None
            private set
        var singleLine: Boolean = false
            private set
        var maxLines: Int = Int.MAX_VALUE
            private set
        var minLines: Int = 1
            private set
        var shape: CornerBasedShape? = null
            private set
        var colors: Map<String, String>? = null
            private set
        var keyboardOptions: KeyboardOptions = KeyboardOptions.Default
            private set
        var keyboardActions: KeyboardActions = KeyboardActions.Default
            private set

        /**
         * This function allows to set a click event to the virtual keyboard action button (e.g.:
         * "Go", "Search", "Done", etc.). When the input service emits an IME action, the
         * corresponding callback is called.
         *
         * @param event event name defined on the server to handle the IME action.
         * @param pushEvent function responsible to dispatch the server call.
         */
        fun onKeyboardAction(event: String, pushEvent: PushEvent?) = apply {
            val action = {
                pushEvent?.invoke(EVENT_TYPE_CLICK, event, value, null)
            }
            this.keyboardActions = KeyboardActions(
                onDone = { action.invoke() },
                onGo = { action.invoke() },
                onNext = { action.invoke() },
                onPrevious = { action.invoke() },
                onSearch = { action.invoke() },
                onSend = { action.invoke() },
            )
        }

        /**
         * Controls the editable state of the text field. When true, the text field cannot be
         * modified. However, a user can focus it and copy text from it. Read-only text fields are
         * usually used to display pre-filled forms that a user cannot edit.
         *
         * @param readOnly true if the text field is read only, false otherwise.
         */
        fun readOnly(readOnly: String) = apply {
            this.readOnly = readOnly.toBoolean()
        }

        /**
         * The style to be applied to the input text. Use the material design text styles.
         * M3 has five distinct type styles: Display, headline, title, body, and label. These styles
         * can be used in 4 sizes: small, medium and large. So you must use style+size (e.g.:
         * `displayLarge`, `headlineMedium`, `titleSmall`, etc.).
         * ```
         * <TextField style="labelSmall" />
         * ```
         * @param textStyle style to be applied to the text field
         */
        fun textStyle(textStyle: String) = apply {
            this.textStyle = textStyle
        }

        /**
         * Indicates if the text field's current value is in error state. If set to true, the label,
         * bottom indicator and trailing icon by default will be displayed in error color.
         * ```
         * <TextField is-error="true" />
         * ```
         * @param isError true if the text field is in error state, false otherwise.
         */
        fun isError(isError: String) = apply {
            this.isError = isError.toBoolean()
        }

        /**
         * When true, this text field becomes a single horizontally scrolling text field instead of
         * wrapping onto multiple lines. The keyboard will be informed to not show the return key as
         * the ImeAction. Note that maxLines parameter will be ignored as the maxLines attribute
         * will be automatically set to 1.
         * ```
         * <TextField single-line="true" />
         * ```
         * @param singleLine true if the text field is in error state, false otherwise.
         */
        fun singleLine(singleLine: String) = apply {
            this.singleLine = singleLine.toBoolean()
            this.maxLines = if (this.singleLine) 1 else Int.MAX_VALUE
        }

        /**
         * The maximum height in terms of maximum number of visible lines. It is required that
         * 1 <= minLines <= maxLines. This parameter is ignored when singleLine is true.
         * ```
         * <TextField max-lines="3" />
         * ```
         * @param maxLines maximum number of visible lines.
         */
        fun maxLines(maxLines: String) = apply {
            this.maxLines = maxLines.toIntOrNull() ?: Int.MAX_VALUE
        }

        /**
         *  The minimum height in terms of minimum number of visible lines. It is required that
         *  1 <= minLines <= maxLines. This parameter is ignored when singleLine is true.
         * ```
         * <TextField min-lines="2" />
         * ```
         * @param minLines minimum number of visible lines.
         */
        fun minLines(minLines: String) = apply {
            this.minLines = minLines.toIntOrNull() ?: 1
        }

        /**
         * Defines the shape of this text field's container
         * ```
         * <TextField shape="rectangle" />
         * ```
         * @param shape text field container's shape. Supported values are: `circle`,
         * `rectangle`, or an integer representing the curve size applied to all four corners.
         */
        fun shape(shape: String) = apply {
            this.shape = shapeFromString(shape)
        }

        /**
         * Transforms the visual representation of the input value. For example, you can use
         * "password" to create a password text field. By default, no visual transformation is
         * applied.
         * ```
         * <TextField visual-transformation="password" />
         * ```
         * @param transformation `password` for password text fields, or `none` for a regular one
         * (default).
         */
        fun visualTransformation(transformation: String) = apply {
            when (transformation) {
                "password" -> this.visualTransformation = PasswordVisualTransformation()
                else -> VisualTransformation.None
            }
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
        fun colors(colors: String) = apply {
            if (colors.isNotEmpty()) {
                try {
                    this.colors = JsonParser.parse(colors)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        /**
         * Informs the keyboard whether to automatically capitalize characters, words or sentences.
         * Only applicable to only text based KeyboardTypes such as KeyboardType.Text,
         * KeyboardType.Ascii. It will not be applied to KeyboardTypes such as KeyboardType.Number.
         * ```
         * <TextField capitalization="words" />
         * ```
         * @param capitalization capitalization type. supported values are: `characters`, `words`,
         * `sentences`, and `none` (default).
         */
        fun capitalization(capitalization: String) = apply {
            keyboardOptions = when (capitalization) {
                "characters" -> keyboardOptions.copy(capitalization = KeyboardCapitalization.Characters)

                "words" -> keyboardOptions.copy(capitalization = KeyboardCapitalization.Words)

                "sentences" -> keyboardOptions.copy(capitalization = KeyboardCapitalization.Sentences)

                else -> keyboardOptions.copy(capitalization = KeyboardCapitalization.None)
            }
        }

        /**
         * Informs the keyboard whether to enable auto correct. Only applicable to text based
         * KeyboardTypes such as KeyboardType.Email, KeyboardType.Uri.
         * It will not be applied to KeyboardTypes such as KeyboardType.Number.
         * Most of keyboard implementations ignore this value for KeyboardTypes such as
         * KeyboardType.Text.
         * ```
         * <TextField auto-correct="true" />
         * ```
         * @param autoCorrect true to enable auto correct, false otherwise.
         */
        fun autoCorrect(autoCorrect: String) = apply {
            keyboardOptions = keyboardOptions.copy(autoCorrect = autoCorrect.toBoolean())
        }

        /**
         * The keyboard type to be used in this text field. Note that this input type is honored by
         * keyboard and shows corresponding keyboard but this is not guaranteed. For example, some
         * keyboards may send non-ASCII character even if you set KeyboardType.Ascii.
         * ```
         * <TextField keyboard-type="email" />
         * ```
         * @param keyboardType the keyboard type. The supported values are: `ascii`, `number`,
         * `phone`, `uri`, `email`, `password`, `numberPassword`, `decimal`, and `text` (default)
         */
        fun keyboardType(keyboardType: String) = apply {
            keyboardOptions = when (keyboardType) {
                "ascii" -> keyboardOptions.copy(keyboardType = KeyboardType.Ascii)
                "number" -> keyboardOptions.copy(keyboardType = KeyboardType.Number)
                "phone" -> keyboardOptions.copy(keyboardType = KeyboardType.Phone)
                "uri" -> keyboardOptions.copy(keyboardType = KeyboardType.Uri)
                "email" -> keyboardOptions.copy(keyboardType = KeyboardType.Email)
                "password" -> keyboardOptions.copy(keyboardType = KeyboardType.Password)
                "numberPassword" -> keyboardOptions.copy(keyboardType = KeyboardType.NumberPassword)
                "decimal" -> keyboardOptions.copy(keyboardType = KeyboardType.Decimal)
                else -> keyboardOptions.copy(keyboardType = KeyboardType.Text)
            }
        }

        /**
         * The IME action. This IME action is honored by keyboard and may show specific icons on the
         * keyboard. For example, search icon may be shown if ImeAction. Search is specified. When
         * ImeOptions.singleLine is false, the keyboard might show return key rather than the
         * action requested here.
         * ```
         * <TextField ime-action="search" />
         * ```
         * @param imeAction IME action. Supported values are: `none`, `go`, `search`, `previous`,
         * `next`, `done`, and `default` (default).
         */
        fun imeAction(imeAction: String) = apply {
            keyboardOptions = when (imeAction) {
                "none" -> keyboardOptions.copy(imeAction = ImeAction.None)
                "go" -> keyboardOptions.copy(imeAction = ImeAction.Go)
                "search" -> keyboardOptions.copy(imeAction = ImeAction.Search)
                "send" -> keyboardOptions.copy(imeAction = ImeAction.Send)
                "previous" -> keyboardOptions.copy(imeAction = ImeAction.Previous)
                "next" -> keyboardOptions.copy(imeAction = ImeAction.Next)
                "done" -> keyboardOptions.copy(imeAction = ImeAction.Done)
                else -> keyboardOptions.copy(imeAction = ImeAction.Default)
            }
        }

        fun build() = TextFieldDTO(this)
    }
}

internal object TextFieldDtoFactory : ComposableViewFactory<TextFieldDTO, TextFieldDTO.Builder>() {

    /**
     * Creates a `TextFieldDTO` object based on the attributes and text of the input `Attributes`
     * object. TextFieldDTO co-relates to the TextField composable
     *
     * @param attributes the `Attributes` object to create the `TextFieldDTO` object from
     * @return a `TextFieldDTO` object based on the attributes and text of the input `Attributes`
     * object
     */
    override fun buildComposableView(
        attributes: Array<CoreAttribute>, pushEvent: PushEvent?, scope: Any?
    ): TextFieldDTO = TextFieldDTO.Builder().also {
        attributes.fold(it) { builder, attribute ->
            if (builder.handleChangeableAttribute(attribute)) {
                builder
            } else {
                when (attribute.name) {
                    attrAutoCorrect -> builder.autoCorrect(attribute.value)
                    attrCapitalization -> builder.capitalization(attribute.value)
                    attrColors -> builder.colors(attribute.value)
                    attrImeAction -> builder.imeAction(attribute.value)
                    attrIsError -> builder.isError(attribute.value)
                    attrKeyboardType -> builder.keyboardType(attribute.value)
                    attrMaxLines -> builder.maxLines(attribute.value)
                    attrMinLines -> builder.minLines(attribute.value)
                    attrPhxClick -> builder.onKeyboardAction(attribute.value, pushEvent)
                    attrReadOnly -> builder.readOnly(attribute.value)
                    attrShape -> builder.shape(attribute.value)
                    attrSingleLine -> builder.singleLine(attribute.value)
                    attrStyle -> builder.textStyle(attribute.value)
                    attrText -> builder.value(attribute.value)
                    attrVisualTransformation -> builder.visualTransformation(attribute.value)
                    else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
                } as TextFieldDTO.Builder
            }
        }
    }.build()
}