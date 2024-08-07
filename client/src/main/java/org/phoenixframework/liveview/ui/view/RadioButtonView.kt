package org.phoenixframework.liveview.ui.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import org.phoenixframework.liveview.constants.Attrs.attrColors
import org.phoenixframework.liveview.constants.Attrs.attrSelected
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDisabledSelectedColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDisabledUnselectedColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrSelectedColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrUnselectedColor
import org.phoenixframework.liveview.extensions.toColor
import org.phoenixframework.liveview.foundation.data.core.CoreAttribute
import org.phoenixframework.liveview.foundation.domain.ComposableTreeNode
import org.phoenixframework.liveview.foundation.ui.base.CommonComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.PushEvent
import org.phoenixframework.liveview.ui.theme.ThemeHolder.Companion.DISABLED_CONTENT_ALPHA

/**
 * Material Design radio button.
 * ```
 * <Row verticalAlignment="center">
 *   <RadioButton phx-value="A" phx-change="setRadioOption" selected={"#{@radioOption == "A"}"} />
 *   <Text>A</Text>
 *   <RadioButton phx-value="B" phx-change="setRadioOption" selected={"#{@radioOption == "B"}"} />
 *   <Text>B</Text>
 *   <RadioButton phx-value="C" phx-change="setRadioOption" selected={"#{@radioOption == "C"}"} />
 *   <Text>C</Text>
 * </Row>
 * ```
 */
internal class RadioButtonView private constructor(builder: Properties) :
    ChangeableView<Any, RadioButtonView.Properties>(builder) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val changeValueEventName = props.changeableProps.onChange
        val enabled = props.changeableProps.enabled

        val selected = props.selected
        val colors = props.colors

        RadioButton(
            modifier = props.commonProps.modifier,
            selected = selected,
            onClick = {
                if (!selected) {
                    changeValueEventName?.let {
                        pushOnChangeEvent(pushEvent, it, props.commonProps.phxValue)
                    }
                }
            },
            enabled = enabled,
            colors = getRadioButtonColors(colors),
        )
    }

    @Composable
    private fun getRadioButtonColors(colors: ImmutableMap<String, String>?): RadioButtonColors {
        val defaultValue = RadioButtonDefaults.colors()
        return if (colors == null) {
            defaultValue
        } else {
            RadioButtonDefaults.colors(
                selectedColor = colors[colorAttrSelectedColor]?.toColor()
                    ?: MaterialTheme.colorScheme.primary,
                unselectedColor = colors[colorAttrUnselectedColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurfaceVariant,
                disabledSelectedColor = colors[colorAttrDisabledSelectedColor]?.toColor()
                    ?: colors[colorAttrSelectedColor]?.toColor()
                        ?.copy(alpha = DISABLED_CONTENT_ALPHA)
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = DISABLED_CONTENT_ALPHA),
                disabledUnselectedColor = colors[colorAttrDisabledUnselectedColor]?.toColor()
                    ?: colors[colorAttrUnselectedColor]?.toColor()
                        ?.copy(alpha = DISABLED_CONTENT_ALPHA)
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = DISABLED_CONTENT_ALPHA),
            )
        }
    }

    @Stable
    internal data class Properties(
        val colors: ImmutableMap<String, String>? = null,
        val selected: Boolean = false,
        override val changeableProps: ChangeableProperties = ChangeableProperties(),
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : IChangeableProperties

    internal object Factory : ChangeableView.Factory() {
        /**
         * Creates a `RadioButtonView` object based on the attributes of the input `Attributes` object.
         * RadioButtonView co-relates to the RadioButton composable
         * @param attributes the `Attributes` object to create the `RadioButtonView` object from
         * @return a `RadioButtonView` object based on the attributes of the input `Attributes` object
         */
        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>,
            pushEvent: PushEvent?,
            scope: Any?
        ): RadioButtonView = RadioButtonView(
            attributes.fold(Properties()) { props, attribute ->
                handleChangeableAttribute(props.changeableProps, attribute)?.let {
                    props.copy(changeableProps = it)
                } ?: run {
                    when (attribute.name) {
                        attrColors -> colors(props, attribute.value)
                        attrSelected -> selected(props, attribute.value)
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
         * Whether this radio button is selected or not.
         *
         * ```
         * <RadioButton selected="true" />
         * ```
         * @param selected true if the RadioButton must be selected, false otherwise.
         */
        private fun selected(props: Properties, selected: String): Properties {
            return props.copy(selected = selected.toBoolean())
        }

        /**
         * Set RadioButton colors.
         * ```
         * <RadioButton
         *   colors="{'selectedColor': '#FFFF0000', 'unselectedColor': '#FF00FF00'}"/>
         * ```
         * @param colors an JSON formatted string, containing the checkbox colors. The color keys
         * supported are: `selectedColor`, `unselectedColor`, `disabledSelectedColor, and
         * `disabledUnselectedColor`.
         */
        private fun colors(props: Properties, colors: String): Properties {
            return if (colors.isNotEmpty()) {
                props.copy(colors = colorsFromString(colors)?.toImmutableMap())
            } else props
        }
    }
}