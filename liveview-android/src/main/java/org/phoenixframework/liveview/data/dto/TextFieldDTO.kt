package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
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
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.map
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.data.mappers.JsonParser
import org.phoenixframework.liveview.domain.base.ComposableBuilder.Companion.ATTR_CLICK
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.privateField
import org.phoenixframework.liveview.domain.extensions.throttleLatest
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView
import org.phoenixframework.liveview.ui.theme.shapeFromString
import org.phoenixframework.liveview.ui.theme.textStyleFromString


internal class TextFieldDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {
    private val text = builder.value
    private val onChange = builder.onChange
    private val debounce = builder.debounce
    private val throttle = builder.throttle
    private val enabled = builder.enabled
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
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val label = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.tag == TextFieldDtoFactory.label }
        }
        val placeholder = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.tag == TextFieldDtoFactory.placeholder }
        }
        val leadingIcon = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.tag == TextFieldDtoFactory.leadingIcon }
        }
        val trailingIcon = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.tag == TextFieldDtoFactory.trailingIcon }
        }
        val prefix = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.tag == TextFieldDtoFactory.prefix }
        }
        val suffix = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.tag == TextFieldDtoFactory.suffix }
        }
        val supportingText = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.tag == TextFieldDtoFactory.supportingText }
        }
        var textFieldValue by remember {
            mutableStateOf(TextFieldValue(text))
        }
        TextField(
            value = textFieldValue,
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

        // Sending the updates to the server respecting phx-debounce and phx-throttle attributes
        LaunchedEffect(composableNode?.id) {
            snapshotFlow { textFieldValue }
                .map { it.text }
                .distinctUntilChanged()
                .drop(1) // Ignoring the first emission when the component is displayed
                .debounce(debounce ?: 0)
                .throttleLatest(throttle)
                .collect { text ->
                    onChange?.let { event ->
                        pushEvent.invoke("change", event, text, null)
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
            fun value(key: String) = textFieldColors[key]?.toColor()
                ?: Color(defaultValue.privateField(key))

            TextFieldDefaults.colors(
                focusedTextColor = value("focusedTextColor"),
                unfocusedTextColor = value("unfocusedTextColor"),
                disabledTextColor = value("disabledTextColor"),
                errorTextColor = value("errorTextColor"),
                focusedContainerColor = value("focusedContainerColor"),
                unfocusedContainerColor = value("unfocusedContainerColor"),
                disabledContainerColor = value("disabledContainerColor"),
                errorContainerColor = value("errorContainerColor"),
                cursorColor = value("cursorColor"),
                errorCursorColor = value("errorCursorColor"),
                selectionColors = TextSelectionColors(
                    textFieldColors["selectionHandleColor"]?.toColor()
                        ?: Color(
                            defaultValue.privateField<TextSelectionColors>("textSelectionColors")
                                .privateField("handleColor")
                        ),
                    textFieldColors["selectionBackgroundColor"]?.toColor()
                        ?: Color(
                            defaultValue.privateField<TextSelectionColors>("textSelectionColors")
                                .privateField("backgroundColor")
                        ),
                ),
                focusedIndicatorColor = value("focusedIndicatorColor"),
                unfocusedIndicatorColor = value("unfocusedIndicatorColor"),
                disabledIndicatorColor = value("disabledIndicatorColor"),
                errorIndicatorColor = value("errorIndicatorColor"),
                focusedLeadingIconColor = value("focusedLeadingIconColor"),
                unfocusedLeadingIconColor = value("unfocusedLeadingIconColor"),
                disabledLeadingIconColor = value("disabledLeadingIconColor"),
                errorLeadingIconColor = value("errorLeadingIconColor"),
                focusedTrailingIconColor = value("focusedTrailingIconColor"),
                unfocusedTrailingIconColor = value("unfocusedTrailingIconColor"),
                disabledTrailingIconColor = value("disabledTrailingIconColor"),
                errorTrailingIconColor = value("errorTrailingIconColor"),
                focusedLabelColor = value("focusedLabelColor"),
                unfocusedLabelColor = value("unfocusedLabelColor"),
                disabledLabelColor = value("disabledLabelColor"),
                errorLabelColor = value("errorLabelColor"),
                focusedPlaceholderColor = value("focusedPlaceholderColor"),
                unfocusedPlaceholderColor = value("unfocusedPlaceholderColor"),
                disabledPlaceholderColor = value("disabledPlaceholderColor"),
                errorPlaceholderColor = value("errorPlaceholderColor"),
                focusedSupportingTextColor = value("focusedSupportingTextColor"),
                unfocusedSupportingTextColor = value("unfocusedSupportingTextColor"),
                disabledSupportingTextColor = value("disabledSupportingTextColor"),
                errorSupportingTextColor = value("errorSupportingTextColor"),
                focusedPrefixColor = value("focusedPrefixColor"),
                unfocusedPrefixColor = value("unfocusedPrefixColor"),
                disabledPrefixColor = value("disabledPrefixColor"),
                errorPrefixColor = value("errorPrefixColor"),
                focusedSuffixColor = value("focusedSuffixColor"),
                unfocusedSuffixColor = value("unfocusedSuffixColor"),
                disabledSuffixColor = value("disabledSuffixColor"),
                errorSuffixColor = value("errorSuffixColor"),
            )
        }
    }

    internal class Builder : ChangeableDTOBuilder<TextFieldDTO, String>("") {
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

        fun onKeyboardAction(event: String, pushEvent: PushEvent?) = apply {
            val action = {
                pushEvent?.invoke(EVENT_CLICK_TYPE, event, value, null)
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


        fun readOnly(readOnly: String) = apply {
            this.readOnly = readOnly.toBoolean()
        }

        fun textStyle(textStyle: String) = apply {
            this.textStyle = textStyle
        }

        fun isError(isError: String) = apply {
            this.isError = isError.toBoolean()
        }

        fun singleLine(singleLine: String) = apply {
            this.singleLine = singleLine.toBoolean()
            this.maxLines = if (this.singleLine) 1 else Int.MAX_VALUE
        }

        fun maxLines(maxLines: String) = apply {
            this.maxLines = maxLines.toIntOrNull() ?: Int.MAX_VALUE
        }

        fun minLines(minLines: String) = apply {
            this.minLines = minLines.toIntOrNull() ?: 1
        }

        fun shape(shape: String) = apply {
            this.shape = shapeFromString(shape)
        }

        fun visualTransformation(transformation: String) = apply {
            when (transformation) {
                "password" -> this.visualTransformation = PasswordVisualTransformation()
                else -> VisualTransformation.None
            }
        }

        fun colors(colors: String) = apply {
            if (colors.isNotEmpty()) {
                try {
                    this.colors = JsonParser.parse(colors)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        fun capitalization(capitalization: String) = apply {
            keyboardOptions = when (capitalization) {
                "characters" ->
                    keyboardOptions.copy(capitalization = KeyboardCapitalization.Characters)

                "words" ->
                    keyboardOptions.copy(capitalization = KeyboardCapitalization.Words)

                "sentences" ->
                    keyboardOptions.copy(capitalization = KeyboardCapitalization.Sentences)

                else ->
                    keyboardOptions.copy(capitalization = KeyboardCapitalization.None)
            }
        }

        fun autoCorrect(autoCorrect: String) = apply {
            keyboardOptions = keyboardOptions.copy(autoCorrect = autoCorrect.toBoolean())
        }

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

        override fun build() = TextFieldDTO(this)
    }
}

internal object TextFieldDtoFactory : ComposableViewFactory<TextFieldDTO, TextFieldDTO.Builder>() {
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?
    ): TextFieldDTO = TextFieldDTO.Builder().apply {
        processChangeableAttributes(attributes)
    }.also {
        attributes.fold(it) { builder, attribute ->
            when (attribute.name) {
                ATTR_CLICK -> builder.onKeyboardAction(attribute.value, pushEvent)
                "text" -> builder.value(attribute.value)
                "readOnly" -> builder.readOnly(attribute.value)
                "textStyle" -> builder.textStyle(attribute.value)
                "isError" -> builder.isError(attribute.value)
                "visualTransformation" -> builder.visualTransformation(attribute.value)
                "singleLine" -> builder.singleLine(attribute.value)
                "maxLines" -> builder.maxLines(attribute.value)
                "minLines" -> builder.minLines(attribute.value)
                "shape" -> builder.shape(attribute.value)
                "colors" -> builder.colors(attribute.value)
                "capitalization" -> builder.capitalization(attribute.value)
                "autoCorrect" -> builder.autoCorrect(attribute.value)
                "keyboardType" -> builder.keyboardType(attribute.value)
                "imeAction" -> builder.imeAction(attribute.value)
                else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
            } as TextFieldDTO.Builder
        }
    }.build()

    override fun subTags(): Map<String, ComposableViewFactory<*, *>> {
        return mapOf(
            label to BoxDtoFactory,
            placeholder to BoxDtoFactory,
            leadingIcon to IconDtoFactory,
            trailingIcon to IconDtoFactory,
            prefix to BoxDtoFactory,
            suffix to BoxDtoFactory,
            supportingText to TextDtoFactory,
        )
    }

    internal const val label = "Label"
    internal const val placeholder = "Placeholder"
    internal const val leadingIcon = "LeadingIcon"
    internal const val trailingIcon = "TrailingIcon"
    internal const val prefix = "Prefix"
    internal const val suffix = "Suffix"
    internal const val supportingText = "SupportingText"
}