package org.phoenixframework.liveview.data.dto

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.data.core.CoreNodeElement
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.paddingIfNotNull

class IconDTO private constructor(builder: Builder) : ComposableView(modifier = builder.modifier) {
    private val contentDescription: String = builder.contentDescription
    private val tint: Color = builder.tint
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
                tint = tint,
                modifier = modifier.paddingIfNotNull(paddingValues)
            )
        }
    }

    open class Builder : ComposableBuilder() {
        var contentDescription: String = ""
        var tint: Color = Color.Black
        var imageVector: ImageVector? = null

        fun contentDescription(contentDescription: String) = apply {
            this.contentDescription = contentDescription
        }

        fun tint(tintColor: String) = apply {
            this.tint = tintColor.toColor()
        }

        fun imageVector(image: String) = apply {
            imageVector = image.toMaterialIcon()
        }

        fun build() = IconDTO(this)
    }
}

object IconDtoFactory : ComposableViewFactory<IconDTO, IconDTO.Builder>() {
    override fun buildComposableView(
        attributes: List<CoreAttribute>,
        children: List<CoreNodeElement>?,
        pushEvent: PushEvent?,
    ): IconDTO = attributes.fold(
        IconDTO.Builder().imageVector(attributes.find { it.name == "imageVector" }?.value ?: "")
    ) { builder, attribute ->
        when (attribute.name) {
            "tint" -> builder.tint(attribute.value)
            "size" -> builder.size(attribute.value)
            "height" -> builder.height(attribute.value)
            "width" -> builder.width(attribute.value)
            "contentDescription" -> builder.contentDescription(attribute.value)
            "padding" -> builder.padding(attribute.value)
            "horizontalPadding" -> builder.horizontalPadding(attribute.value)
            "verticalPadding" -> builder.verticalPadding(attribute.value)
            else -> builder
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
    Log.e("NavIcon", e.message ?: "Icon not found")

    null
}