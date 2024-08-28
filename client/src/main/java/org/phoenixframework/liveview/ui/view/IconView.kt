package org.phoenixframework.liveview.ui.view

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.collections.immutable.ImmutableList
import org.phoenixframework.liveview.constants.Attrs.attrContentDescription
import org.phoenixframework.liveview.constants.Attrs.attrImageVector
import org.phoenixframework.liveview.constants.Attrs.attrTint
import org.phoenixframework.liveview.constants.IconPrefixValues
import org.phoenixframework.liveview.extensions.paddingIfNotNull
import org.phoenixframework.liveview.extensions.toColor
import org.phoenixframework.liveview.foundation.data.core.CoreAttribute
import org.phoenixframework.liveview.foundation.domain.ComposableTreeNode
import org.phoenixframework.liveview.foundation.ui.base.CommonComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableView
import org.phoenixframework.liveview.foundation.ui.base.ComposableViewFactory
import org.phoenixframework.liveview.foundation.ui.base.PushEvent

/**
 * A Material Design icon component that draws a local imageVector using tint. Icon is an
 * opinionated component designed to be used with single-color icons so that they can be tinted
 * correctly for the component they are placed in. For generic images that should not be tinted,
 * and do not follow the recommended icon size, use the generic Image instead. For a clickable icon,
 * see IconButton.
 * ```
 * <Icon imageVector="filled:Add" />
 * ```
 */
internal class IconView private constructor(props: Properties) :
    ComposableView<IconView.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent,
    ) {
        val contentDescription = props.contentDescription
        val tint = props.tint
        val imageVectorValue = props.imageVector

        imageVectorValue?.let { imageVector ->
            Icon(
                imageVector = imageVector,
                contentDescription = contentDescription,
                tint = tint ?: LocalContentColor.current,
                modifier = props.commonProps.modifier.paddingIfNotNull(paddingValues)
            )
        }
    }

    @Stable
    internal data class Properties(
        val contentDescription: String = "",
        val tint: Color? = null,
        val imageVector: ImageVector? = null,
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : ComposableProperties

    internal object Factory : ComposableViewFactory<IconView>() {

        private val iconCache = mutableMapOf<String, ImageVector>()

        /**
         * Creates a `IconView` object based on the attributes of the input `Attributes` object.
         * IconView co-relates to the Icon composable
         * @param attributes the `Attributes` object to create the `IconView` object from
         * @return a `IconView` object based on the attributes of the input `Attributes` object
         */
        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>,
            pushEvent: PushEvent?,
            scope: Any?,
        ): IconView = IconView(attributes.fold(Properties()) { props, attribute ->
            when (attribute.name) {
                attrImageVector -> imageVector(props, attribute.value)
                attrTint -> tint(props, attribute.value)
                attrContentDescription -> contentDescription(props, attribute.value)
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
         * Sets the icon content description fro accessibility purpose.
         *
         * ```
         * <Icon contentDescription="Save Icon" />
         * ```
         * @param contentDescription string representing the icon's content description
         */
        private fun contentDescription(props: Properties, contentDescription: String): Properties {
            return props.copy(contentDescription = contentDescription)
        }

        /**
         * Tint color to be applied to icon.
         * ```
         * <Icon tint="#FFFF0000"/>
         * ```
         * @param tintColor the icon tint color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.constants.SystemColorValues] colors.
         */
        private fun tint(props: Properties, tintColor: String): Properties {
            return props.copy(tint = tintColor.toColor())
        }

        /**
         * Material icon to be drawn. This icon is provided by the Material Icons library.
         * There are five distinct icon themes: Filled, Outlined, Rounded, TwoTone, and Sharp. Each
         * theme contains the same icons, but with a distinct visual style.
         * See all available icons [here](https://developer.android.com/reference/kotlin/androidx/compose/material/icons/package-summary).
         * ```
         * <Icon imageVector="filled:ChevronLeft"/>
         * ```
         * @param icon material icon following the pattern: *theme:icon* (e.g.: `"filled:Send"`).
         */
        private fun imageVector(props: Properties, icon: String): Properties {
            return props.copy(imageVector = getIcon(icon))
        }

        private fun getIcon(icon: String): ImageVector? {
            if (!iconCache.containsKey(icon)) {
                icon.toMaterialIcon()?.let { iconCache[icon] = it }
            }
            return iconCache[icon]
        }
    }
}

private fun String.toMaterialIcon(): ImageVector? = try {
    val imageParameters = split(":")
    val themePackage = imageParameters.first()
    val action = imageParameters.last()
    val clazzName = "androidx.compose.material.icons.${themePackage.lowercase()}.${action}Kt"
    val iconClass = Class.forName(clazzName)
    val method = iconClass.declaredMethods.first()

    val theme: Any = when (themePackage) {
        IconPrefixValues.filled -> Icons.Filled
        IconPrefixValues.rounded -> Icons.Rounded
        IconPrefixValues.outlined -> Icons.Outlined
        IconPrefixValues.sharp -> Icons.Sharp
        IconPrefixValues.twoTone -> Icons.TwoTone
        IconPrefixValues.autoMirroredFilled -> Icons.AutoMirrored.Filled
        IconPrefixValues.autoMirroredRounded -> Icons.AutoMirrored.Rounded
        IconPrefixValues.autoMirroredOutlined -> Icons.AutoMirrored.Outlined
        IconPrefixValues.autoMirroredSharp -> Icons.AutoMirrored.Sharp
        IconPrefixValues.autoMirroredTwoTone -> Icons.AutoMirrored.TwoTone
        else -> Icons.Filled
    }
    method.invoke(null, theme) as ImageVector
} catch (e: Throwable) {
    Log.e("IconView", e.message ?: "Icon [$this] not found")
    null
}