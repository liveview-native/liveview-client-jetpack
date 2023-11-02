package org.phoenixframework.liveview.data.dto

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.paddingIfNotNull

internal class IconDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {
    private val contentDescription: String = builder.contentDescription
    private val tint: Color? = builder.tint
    private val imageVector: ImageVector? = builder.imageVector

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent,
    ) {
        imageVector?.let { imageVector ->
            Icon(
                imageVector = imageVector,
                contentDescription = contentDescription,
                tint = tint ?: LocalContentColor.current,
                modifier = modifier.paddingIfNotNull(paddingValues)
            )
        }
    }

    internal class Builder : ComposableBuilder() {
        var contentDescription: String = ""
            private set
        var tint: Color? = null
            private set
        var imageVector: ImageVector? = null
            private set

        /**
         * Sets the icon content description fro accessibility purpose.
         *
         * ```
         * <Icon contentDescription="Save Icon" />
         * ```
         * @param contentDescription string representing the icon's content description
         */
        fun contentDescription(contentDescription: String) = apply {
            this.contentDescription = contentDescription
        }

        /**
         * Tint color to be applied to icon.
         * ```
         * <Icon tint="#FFFF0000"/>
         * ```
         * @param tintColor the icon tint color in AARRGGBB format.
         */
        fun tint(tintColor: String) = apply {
            this.tint = tintColor.toColor()
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
        fun imageVector(icon: String) = apply {
            imageVector = getIcon(icon)
        }

        fun build() = IconDTO(this)

        companion object {
            private val iconCache = mutableMapOf<String, ImageVector>()

            fun getIcon(icon: String): ImageVector {
                if (!iconCache.containsKey(icon)) {
                    icon.toMaterialIcon()?.let { iconCache[icon] = it }
                }
                return iconCache[icon]!!
            }
        }
    }
}

internal object IconDtoFactory : ComposableViewFactory<IconDTO, IconDTO.Builder>() {
    /**
     * Creates a `IconDTO` object based on the attributes of the input `Attributes` object.
     * Row co-relates to the Icon composable
     * @param attributes the `Attributes` object to create the `IconDTO` object from
     * @return a `IconDTO` object based on the attributes of the input `Attributes` object
     */
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?,
    ): IconDTO = attributes.fold(
        IconDTO.Builder()
    ) { builder, attribute ->
        when (attribute.name) {
            "imageVector" -> builder.imageVector(attribute.value)
            "tint" -> builder.tint(attribute.value)
            "contentDescription" -> builder.contentDescription(attribute.value)
            else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
        } as IconDTO.Builder
    }.build()
}

private fun String.toMaterialIcon(): ImageVector? = try {
    val imageParameters = split(":")
    val themePackage = imageParameters.first()
    val action = imageParameters.last()

    val iconClass = Class.forName("androidx.compose.material.icons.${themePackage}.${action}Kt")
    val method = iconClass.declaredMethods.first()

    val theme: Any = when (themePackage) {
        "filled" -> Icons.Filled
        "rounded" -> Icons.Rounded
        "outlined" -> Icons.Outlined
        "sharp" -> Icons.Sharp
        "twoTone" -> Icons.TwoTone
        else -> Icons.Default
    }
    method.invoke(null, theme) as ImageVector
} catch (e: Throwable) {
    Log.e("IconDTO", e.message ?: "Icon not found")
    null
}