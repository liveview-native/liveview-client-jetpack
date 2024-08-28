package org.phoenixframework.liveview.ui.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.coroutines.flow.map
import org.phoenixframework.liveview.constants.Attrs.attrChecked
import org.phoenixframework.liveview.constants.Attrs.attrColors
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrCheckedBorderColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrCheckedIconColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrCheckedThumbColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrCheckedTrackColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDisabledCheckedBorderColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDisabledCheckedIconColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDisabledCheckedThumbColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDisabledCheckedTrackColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDisabledUncheckedBorderColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDisabledUncheckedIconColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDisabledUncheckedThumbColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDisabledUncheckedTrackColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrUncheckedBorderColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrUncheckedIconColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrUncheckedThumbColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrUncheckedTrackColor
import org.phoenixframework.liveview.constants.Templates.templateThumb
import org.phoenixframework.liveview.extensions.toColor
import org.phoenixframework.liveview.foundation.data.core.CoreAttribute
import org.phoenixframework.liveview.foundation.domain.ComposableTreeNode
import org.phoenixframework.liveview.foundation.ui.base.CommonComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.PushEvent
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView
import org.phoenixframework.liveview.ui.theme.ThemeHolder.Companion.DISABLED_CONTAINER_ALPHA
import org.phoenixframework.liveview.ui.theme.ThemeHolder.Companion.DISABLED_CONTENT_ALPHA

/**
 * Material Design Switch.
 * ```
 * <Switch checked={"#{@isChecked}"} phx-change="toggleCheck" />
 * ```
 */
internal class SwitchView private constructor(props: Properties) :
    ChangeableView<Boolean, SwitchView.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?, paddingValues: PaddingValues?, pushEvent: PushEvent
    ) {
        val enabled = props.changeableProps.enabled
        val changeValueEventName = props.changeableProps.onChange
        val colors = props.colors
        val checked = props.checked

        val thumbContent = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateThumb }
        }
        var stateValue by remember(composableNode) {
            mutableStateOf(checked)
        }
        Switch(
            checked = stateValue,
            onCheckedChange = {
                stateValue = it
            },
            modifier = props.commonProps.modifier,
            enabled = enabled,
            thumbContent = thumbContent?.let {
                {
                    PhxLiveView(it, pushEvent, composableNode, null)
                }
            },
            colors = getSwitchColors(colors),
        )

        LaunchedEffect(composableNode) {
            changeValueEventName?.let { event ->
                snapshotFlow { stateValue }.onChangeable().map { isChecked ->
                    mergeValueWithPhxValue(KEY_CHECKED, isChecked)
                }.collect { value ->
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
            SwitchDefaults.colors(
                checkedThumbColor = colors[colorAttrCheckedThumbColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onPrimary,
                checkedTrackColor = colors[colorAttrCheckedTrackColor]?.toColor()
                    ?: MaterialTheme.colorScheme.primary,
                checkedBorderColor = colors[colorAttrCheckedBorderColor]?.toColor()
                    ?: Color.Transparent,
                checkedIconColor = colors[colorAttrCheckedIconColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onPrimaryContainer,
                uncheckedThumbColor = colors[colorAttrUncheckedThumbColor]?.toColor()
                    ?: MaterialTheme.colorScheme.outline,
                uncheckedTrackColor = colors[colorAttrUncheckedTrackColor]?.toColor()
                    ?: MaterialTheme.colorScheme.surfaceVariant,
                uncheckedBorderColor = colors[colorAttrUncheckedBorderColor]?.toColor()
                    ?: MaterialTheme.colorScheme.outline,
                uncheckedIconColor = colors[colorAttrUncheckedIconColor]?.toColor()
                    ?: MaterialTheme.colorScheme.surfaceVariant,
                disabledCheckedThumbColor = colors[colorAttrDisabledCheckedThumbColor]?.toColor()
                    ?: MaterialTheme.colorScheme.surface.copy(alpha = 1.0f)
                        .compositeOver(MaterialTheme.colorScheme.surface),
                disabledCheckedTrackColor = colors[colorAttrDisabledCheckedTrackColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = DISABLED_CONTAINER_ALPHA)
                        .compositeOver(MaterialTheme.colorScheme.surface),
                disabledCheckedBorderColor = colors[colorAttrDisabledCheckedBorderColor]?.toColor()
                    ?: Color.Transparent,
                disabledCheckedIconColor = colors[colorAttrDisabledCheckedIconColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = DISABLED_CONTENT_ALPHA)
                        .compositeOver(MaterialTheme.colorScheme.surface),
                disabledUncheckedThumbColor = colors[colorAttrDisabledUncheckedThumbColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = DISABLED_CONTENT_ALPHA)
                        .compositeOver(MaterialTheme.colorScheme.surface),
                disabledUncheckedTrackColor = colors[colorAttrDisabledUncheckedTrackColor]?.toColor()
                    ?: MaterialTheme.colorScheme.surfaceVariant.copy(alpha = DISABLED_CONTAINER_ALPHA)
                        .compositeOver(MaterialTheme.colorScheme.surface),
                disabledUncheckedBorderColor = colors[colorAttrDisabledUncheckedBorderColor]?.toColor()
                    ?: MaterialTheme.colorScheme.onSurface.copy(alpha = DISABLED_CONTAINER_ALPHA)
                        .compositeOver(MaterialTheme.colorScheme.surface),
                disabledUncheckedIconColor = colors[colorAttrDisabledUncheckedIconColor]?.toColor()
                    ?: MaterialTheme.colorScheme.surfaceVariant.copy(alpha = DISABLED_CONTENT_ALPHA)
                        .compositeOver(MaterialTheme.colorScheme.surface),
            )
        }
    }

    companion object {
        private const val KEY_CHECKED = "checked"
    }

    @Stable
    internal data class Properties(
        val checked: Boolean = false,
        val colors: ImmutableMap<String, String>? = null,
        override val changeableProps: ChangeableProperties = ChangeableProperties(),
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : IChangeableProperties

    internal object Factory : ChangeableView.Factory() {
        /**
         * Creates a `SwitchView` object based on the attributes of the input `Attributes` object.
         * SwitchView co-relates to the Switch composable
         * @param attributes the `Attributes` object to create the `SwitchView` object from
         * @return a `SwitchView` object based on the attributes of the input `Attributes` object
         */
        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>, pushEvent: PushEvent?, scope: Any?
        ): SwitchView = SwitchView(
            attributes.fold(Properties()) { props, attribute ->
                handleChangeableAttribute(props.changeableProps, attribute)?.let {
                    props.copy(changeableProps = it)
                } ?: run {
                    when (attribute.name) {
                        attrChecked -> checked(props, attribute.value)
                        attrColors -> colors(props, attribute.value)
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

        private fun checked(props: Properties, checked: String): Properties {
            return props.copy(checked = checked.toBoolean())
        }

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
        private fun colors(props: Properties, colors: String): Properties {
            return if (colors.isNotEmpty()) {
                props.copy(colors = colorsFromString(colors)?.toImmutableMap())
            } else props
        }
    }
}