package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.data.mappers.JsonParser
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableBuilder.Companion.ATTR_CLICK
import org.phoenixframework.liveview.domain.base.ComposableBuilder.Companion.EVENT_CLICK_TYPE
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.privateField
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView

internal class IconButtonDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {
    private val onClick: () -> Unit = builder.onClick
    private val enabled: Boolean = builder.enabled
    private val colors: ImmutableMap<String, String>? = builder.colors?.toImmutableMap()

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent,
    ) {
        IconButton(
            onClick = onClick,
            enabled = enabled,
            colors = getIconButtonColors(colors),
        ) {
            composableNode?.children?.forEach {
                PhxLiveView(it, pushEvent, composableNode, null)
            }
        }
    }

    @Composable
    private fun getIconButtonColors(colors: ImmutableMap<String, String>?): IconButtonColors {
        val defaultValue = IconButtonDefaults.iconButtonColors()
        return if (colors == null) {
            defaultValue
        } else {
            IconButtonDefaults.iconButtonColors(
                containerColor = colors["containerColor"]?.toColor()
                    ?: Color(defaultValue.privateField("containerColor")),
                contentColor = colors["contentColor"]?.toColor()
                    ?: Color(defaultValue.privateField("contentColor")),
                disabledContainerColor = colors["disabledContainerColor"]?.toColor()
                    ?: Color(defaultValue.privateField("disabledContainerColor")),
                disabledContentColor = colors["disabledContentColor"]?.toColor()
                    ?: Color(defaultValue.privateField("disabledContentColor")),
            )
        }
    }

    internal class Builder : ComposableBuilder<IconButtonDTO>() {
        var onClick: () -> Unit = {}
            private set
        var enabled: Boolean = true
            private set
        var colors: Map<String, String>? = null
            private set

        fun onClick(clickEvent: () -> Unit): Builder = apply {
            this.onClick = clickEvent
        }

        fun enabled(enabled: String): Builder = apply {
            this.enabled = enabled.toBoolean()
        }

        fun colors(colors: String): Builder = apply {
            if (colors.isNotEmpty()) {
                try {
                    this.colors = JsonParser.parse(colors)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        override fun build() = IconButtonDTO(this)
    }
}

internal object IconButtonDtoFactory :
    ComposableViewFactory<IconButtonDTO, IconButtonDTO.Builder>() {
    override fun buildComposableView(
        attributes: Array<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?,
    ): IconButtonDTO = attributes.fold(
        IconButtonDTO.Builder()
    ) { builder, attribute ->
        when (attribute.name) {
            "enabled" -> builder.enabled(attribute.value)
            "colors" -> builder.colors(attribute.value)
            ATTR_CLICK -> builder.onClick {
                pushEvent?.invoke(EVENT_CLICK_TYPE, attribute.value, "", null)
            }

            else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
        } as IconButtonDTO.Builder
    }.build()
}