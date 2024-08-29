package org.phoenixframework.liveview.ui.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import org.phoenixframework.liveview.constants.Attrs.attrBorder
import org.phoenixframework.liveview.constants.Attrs.attrChecked
import org.phoenixframework.liveview.constants.Attrs.attrColor
import org.phoenixframework.liveview.constants.Attrs.attrContentColor
import org.phoenixframework.liveview.constants.Attrs.attrEnabled
import org.phoenixframework.liveview.constants.Attrs.attrPhxChange
import org.phoenixframework.liveview.constants.Attrs.attrPhxClick
import org.phoenixframework.liveview.constants.Attrs.attrSelected
import org.phoenixframework.liveview.constants.Attrs.attrShadowElevation
import org.phoenixframework.liveview.constants.Attrs.attrShape
import org.phoenixframework.liveview.constants.Attrs.attrTonalElevation
import org.phoenixframework.liveview.extensions.isNotEmptyAndIsDigitsOnly
import org.phoenixframework.liveview.extensions.paddingIfNotNull
import org.phoenixframework.liveview.extensions.toColor
import org.phoenixframework.liveview.foundation.data.core.CoreAttribute
import org.phoenixframework.liveview.foundation.domain.ComposableTreeNode
import org.phoenixframework.liveview.foundation.ui.base.CommonComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableView
import org.phoenixframework.liveview.foundation.ui.base.ComposableViewFactory
import org.phoenixframework.liveview.foundation.ui.base.PushEvent
import org.phoenixframework.liveview.foundation.ui.view.onClickFromString
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView
import org.phoenixframework.liveview.ui.theme.shapeFromString

/**
 * Material surface is the central metaphor in material design. Each surface exists at a given
 * elevation, which influences how that piece of surface visually relates to other surfaces and how
 * that surface is modified by tonal variance.
 * A surface can be:
 * - simple: it's just a surface with no action or state
 * - clickable: the surface has a clickable action
 * - selectable: the surface has a selected state and a click action, but this action only works
 * when the component is not selected.
 * - checkable: the surface has a checked state and a change action that receives the new check
 * state as parameter.
 * ```
 * <Surface shape="12" color="Blue" contentColor="White">
 *   <Text style="padding(16.dp)">Simple Surface</Text>
 * </Surface>
 *
 * <Surface shape="12" color="Blue" contentColor="White"
 *   phx-click="onClick">
 *   <Text style="padding(32.dp)">Clickable Surface</Text>
 * </Surface>
 *
 * <Surface shape="12" color="Blue" contentColor="White"
 *   selected={"#{@isSelected}"} phx-click="selectItem">
 *   <Text style="padding(32.dp)">Selectable Surface</Text>
 * </Surface>
 *
 * <Surface shape="12" color="Blue" contentColor="White"
 *   checked={"#{@isChecked}"} phx-change="toggleCheck">
 *   <Text style="padding(32.dp)">Checkable Surface</Text>
 * </Surface>
 * ```
 */
internal class SurfaceView private constructor(props: Properties) :
    ComposableView<SurfaceView.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val shape = props.shape
        val colorValue = props.color
        val contentColor = props.contentColor
        val tonalElevation = props.tonalElevation
        val shadowElevation = props.shadowElevation
        val border = props.border
        val onClick = props.onClick
        val enabled = props.enabled
        val selected = props.selected
        val checked = props.checked
        val onChange = props.onChange

        val color = colorValue ?: MaterialTheme.colorScheme.surface
        val newModifier = props.commonProps.modifier
            .paddingIfNotNull(paddingValues)
        when {
            onClick != null && selected == null ->
                Surface(
                    onClick = onClickFromString(
                        pushEvent,
                        onClick,
                        props.commonProps.phxValue
                    ),
                    modifier = newModifier,
                    enabled = enabled ?: true,
                    shape = shape ?: RectangleShape,
                    color = color,
                    contentColor = contentColor ?: contentColorFor(color),
                    tonalElevation = tonalElevation ?: 0.dp,
                    shadowElevation = shadowElevation ?: 0.dp,
                    border = border,
                    // TODO interactionSource: MutableInteractionSource,
                    content = {
                        composableNode?.children?.forEach {
                            PhxLiveView(it, pushEvent, composableNode, null, this)
                        }
                    }
                )

            onClick != null && selected != null ->
                Surface(
                    selected = selected,
                    onClick = onClickFromString(
                        pushEvent,
                        onClick,
                        props.commonProps.phxValue
                    ),
                    modifier = newModifier,
                    enabled = enabled ?: true,
                    shape = shape ?: RectangleShape,
                    color = color,
                    contentColor = contentColor ?: contentColorFor(color),
                    tonalElevation = tonalElevation ?: 0.dp,
                    shadowElevation = shadowElevation ?: 0.dp,
                    border = border,
                    // TODO interactionSource: MutableInteractionSource,
                    content = {
                        composableNode?.children?.forEach {
                            PhxLiveView(it, pushEvent, composableNode, null, this)
                        }
                    }
                )

            checked != null ->
                Surface(
                    checked = checked,
                    onCheckedChange = {
                        if (onChange != null) {
                            pushEvent.invoke(
                                EVENT_TYPE_CHANGE,
                                onChange,
                                mergeValueWithPhxValue(KEY_CHECKED, it),
                                null
                            )
                        }
                    },
                    modifier = newModifier,
                    enabled = enabled ?: true,
                    shape = shape ?: RectangleShape,
                    color = color,
                    contentColor = contentColor ?: contentColorFor(color),
                    tonalElevation = tonalElevation ?: 0.dp,
                    shadowElevation = shadowElevation ?: 0.dp,
                    border = border,
                    // TODO interactionSource: MutableInteractionSource,
                    content = {
                        composableNode?.children?.forEach {
                            PhxLiveView(it, pushEvent, composableNode, null, this)
                        }
                    }
                )

            else ->
                Surface(
                    modifier = newModifier,
                    shape = shape ?: RectangleShape,
                    color = color,
                    contentColor = contentColor ?: contentColorFor(color),
                    tonalElevation = tonalElevation ?: 0.dp,
                    shadowElevation = shadowElevation ?: 0.dp,
                    border = border,
                    content = {
                        composableNode?.children?.forEach {
                            PhxLiveView(it, pushEvent, composableNode, null, this)
                        }
                    }
                )
        }
    }

    companion object {
        const val KEY_CHECKED = "checked"
    }

    @Stable
    internal data class Properties(
        val border: BorderStroke? = null,
        val checked: Boolean? = null,
        val color: Color? = null,
        val contentColor: Color? = null,
        val enabled: Boolean? = null,
        val onChange: String? = null,
        val onClick: String? = null,
        val selected: Boolean? = null,
        val shadowElevation: Dp? = null,
        val shape: Shape? = null,
        val tonalElevation: Dp? = null,
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : ComposableProperties

    internal object Factory : ComposableViewFactory<SurfaceView>() {
        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>,
            pushEvent: PushEvent?,
            scope: Any?
        ): SurfaceView = SurfaceView(attributes.fold(Properties()) { props, attribute ->
            when (attribute.name) {
                attrBorder -> border(props, attribute.value)
                attrChecked -> checked(props, attribute.value)
                attrColor -> color(props, attribute.value)
                attrContentColor -> contentColor(props, attribute.value)
                attrEnabled -> enabled(props, attribute.value)
                attrPhxClick -> onClick(props, attribute.value)
                attrPhxChange -> onChange(props, attribute.value)
                attrSelected -> selected(props, attribute.value)
                attrShadowElevation -> shadowElevation(props, attribute.value)
                attrShape -> shape(props, attribute.value)
                attrTonalElevation -> tonalElevation(props, attribute.value)
                else -> props.copy(
                    commonProps = handleCommonAttributes(
                        props.commonProps,
                        attribute,
                        pushEvent,
                        scope
                    )
                )
            }
        })

        /**
         * Optional border to draw on top of the surface.
         */
        private fun border(props: Properties, border: String): Properties {
            return props.copy(border = borderFromString(border))
        }

        /**
         * Callback to be invoked when the toggleable Surface is clicked.
         */
        private fun checked(props: Properties, checked: String): Properties {
            return props.copy(checked = checked.toBoolean())
        }

        /**
         * The background color. Use "Transparent" to have no color.
         */
        private fun color(props: Properties, color: String): Properties {
            return props.copy(color = color.toColor())
        }

        /**
         * The preferred content color provided by this Surface to its children.
         */
        private fun contentColor(props: Properties, color: String): Properties {
            return props.copy(contentColor = color.toColor())
        }

        /**
         * Controls the enabled state of the surface. When false, this surface will not be clickable.
         */
        private fun enabled(props: Properties, enabled: String): Properties {
            return props.copy(enabled = enabled.toBoolean())
        }

        private fun onChange(props: Properties, onChange: String): Properties {
            return props.copy(onChange = onChange)
        }

        /**
         * Callback to be called when the surface is clicked.
         */
        private fun onClick(props: Properties, onClick: String): Properties {
            return props.copy(onClick = onClick)
        }

        /**
         * Whether or not this Surface is selected.
         */
        private fun selected(props: Properties, selected: String): Properties {
            return props.copy(selected = selected.toBoolean())
        }

        /**
         * The size of the shadow below the surface. To prevent shadow creep, only apply shadow
         * elevation when absolutely necessary, such as when the surface requires visual separation
         * from a patterned background. Note that It will not affect z index of the Surface. If you
         * want to change the drawing order you can use Modifier.zIndex.
         */
        private fun shadowElevation(props: Properties, shadow: String): Properties {
            return if (shadow.isNotEmptyAndIsDigitsOnly()) {
                props.copy(shadowElevation = shadow.toInt().dp)
            } else props
        }

        /**
         * Defines the surface's shape as well its shadow. A shadow is only displayed if the
         * tonalElevation is greater than zero.
         *
         * @param shape surface's shape. See the supported values at
         * [org.phoenixframework.liveview.constants.ShapeValues], or use an integer
         * representing the curve size applied for all four corners.
         */
        private fun shape(props: Properties, shape: String): Properties {
            return if (shape.isNotEmpty()) {
                props.copy(shape = shapeFromString(shape))
            } else props
        }

        /**
         * A higher the elevation will result in a darker color in light theme and lighter color
         * in dark theme.
         */
        private fun tonalElevation(props: Properties, elevation: String): Properties {
            return if (elevation.isNotEmptyAndIsDigitsOnly()) {
                props.copy(tonalElevation = elevation.toInt().dp)
            } else props
        }
    }
}