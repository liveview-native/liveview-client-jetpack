package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.graphics.Color
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.data.mappers.JsonParser
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.privateField
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode

/**
 * Material Design Switch.
 * ```
 * <Switch checked={"#{@isChecked}"} phx-change="toggleCheck" />
 * ```
 */
internal class SwitchDTO private constructor(builder: Builder) :
    ChangeableDTO<Boolean>(builder) {
    private val colors = builder.colors?.toImmutableMap()

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        var stateValue by remember(composableNode) {
            mutableStateOf(value)
        }
        Switch(
            checked = stateValue,
            onCheckedChange = {
                stateValue = it
            },
            modifier = modifier,
            enabled = enabled,
            colors = getSwitchColors(colors),
        )

        LaunchedEffect(composableNode) {
            changeValueEventName?.let { event ->
                snapshotFlow { stateValue }
                    .onChangeable()
                    .collect { value ->
                        pushOnChangeEvent(pushEvent, event, value)
                    }
            }
        }
    }

    @Composable
    private fun getSwitchColors(colors: ImmutableMap<String, String>?): SwitchColors {
        val defaultValue = SwitchDefaults.colors()
        return if (colors == null) {
            defaultValue
        } else {
            fun value(key: String) = colors[key]?.toColor() ?: Color(defaultValue.privateField(key))

            SwitchDefaults.colors(
                checkedThumbColor = value("checkedThumbColor"),
                checkedTrackColor = value("checkedTrackColor"),
                checkedBorderColor = value("checkedBorderColor"),
                checkedIconColor = value("checkedIconColor"),
                uncheckedThumbColor = value("uncheckedThumbColor"),
                uncheckedTrackColor = value("uncheckedTrackColor"),
                uncheckedBorderColor = value("uncheckedBorderColor"),
                uncheckedIconColor = value("uncheckedIconColor"),
                disabledCheckedThumbColor = value("disabledCheckedThumbColor"),
                disabledCheckedTrackColor = value("disabledCheckedTrackColor"),
                disabledCheckedBorderColor = value("disabledCheckedBorderColor"),
                disabledCheckedIconColor = value("disabledCheckedIconColor"),
                disabledUncheckedThumbColor = value("disabledUncheckedThumbColor"),
                disabledUncheckedTrackColor = value("disabledUncheckedTrackColor"),
                disabledUncheckedBorderColor = value("disabledUncheckedBorderColor"),
                disabledUncheckedIconColor = value("disabledUncheckedIconColor"),
            )
        }
    }

    internal class Builder : ChangeableDTOBuilder<Boolean>(false) {

        var colors: Map<String, String>? = null
            private set

        /**
         * Set Switch colors.
         * ```
         * <Switch
         *   colors="{'checkedThumbColor': '#FFFF0000', 'checkedTrackColor': '#FF00FF00'}"/>
         * ```
         * @param colors an JSON formatted string, containing the checkbox colors. The color keys
         * supported are: `checkedThumbColor`, `checkedTrackColor`, `checkedBorderColor,
         * `checkedIconColor`, `uncheckedThumbColor`, `uncheckedTrackColor`, `uncheckedBorderColor`,
         * `uncheckedIconColor`, `disabledCheckedThumbColor`, `disabledCheckedTrackColor`,
         * `disabledCheckedBorderColor`, `disabledCheckedIconColor`, `disabledUncheckedThumbColor`,
         * `disabledUncheckedTrackColor`, `disabledUncheckedBorderColor`, and
         * `disabledUncheckedIconColor`.
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

        fun build() = SwitchDTO(this)
    }
}

internal object SwitchDtoFactory : ComposableViewFactory<SwitchDTO, SwitchDTO.Builder>() {
    /**
     * Creates a `SwitchDTO` object based on the attributes of the input `Attributes` object.
     * SwitchDTO co-relates to the Switch composable
     * @param attributes the `Attributes` object to create the `SwitchDTO` object from
     * @return a `SwitchDTO` object based on the attributes of the input `Attributes` object
     */
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?
    ): SwitchDTO = SwitchDTO.Builder().also {
        attributes.fold(
            it
        ) { builder, attribute ->
            if (builder.handleChangeableAttribute(attribute)) {
                builder
            } else {
                when (attribute.name) {
                    "checked" -> builder.value(attribute.value.toBoolean())
                    "colors" -> builder.colors(attribute.value)
                    else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
                } as SwitchDTO.Builder
            }
        }
    }.build()
}