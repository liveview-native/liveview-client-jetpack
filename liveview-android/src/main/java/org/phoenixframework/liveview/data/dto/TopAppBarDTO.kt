package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import kotlinx.collections.immutable.toImmutableList
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.data.core.CoreNodeElement
import org.phoenixframework.liveview.data.dto.TopAppBarDtoFactory.actionTag
import org.phoenixframework.liveview.data.dto.TopAppBarDtoFactory.navigationIconTag
import org.phoenixframework.liveview.data.dto.TopAppBarDtoFactory.titleTag
import org.phoenixframework.liveview.data.mappers.JsonParser
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.privateField
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView

class TopAppBarDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {

    private val colors: Map<String, String>? = builder.colors

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val title = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.tag == titleTag }
        }
        val actions = remember(composableNode?.children) {
            composableNode?.children?.filter { it.node?.tag == actionTag }?.toImmutableList()
        }
        val navIcon = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.tag == navigationIconTag }
        }
        TopAppBar(
            colors = getTopAppBarColors(colors = colors) ?: TopAppBarDefaults.topAppBarColors(),
            title = {
                title?.let {
                    PhxLiveView(it, null, pushEvent)
                }
            },
            navigationIcon = {
                navIcon?.let {
                    Box {
                        PhxLiveView(it, null, pushEvent)
                    }
                }
            },
            actions = {
                actions?.forEach {
                    PhxLiveView(it, null, pushEvent)
                }
            },
            modifier = modifier,
        )
    }

    class Builder : ComposableBuilder() {
        var colors: Map<String, String>? = null

        fun colors(colors: String): Builder = apply {
            if (colors.isNotEmpty()) {
                try {
                    this.colors = JsonParser.parse(colors)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        fun build() = TopAppBarDTO(this)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun getTopAppBarColors(colors: Map<String, String>?): TopAppBarColors? {
        val defaultColors = TopAppBarDefaults.topAppBarColors()
        return if (colors == null) {
            defaultColors
        } else {
            ButtonDefaults.buttonColors(
                containerColor = colors["containerColor"]?.toColor()
                    ?: Color(defaultColors.privateField("containerColor")),
                contentColor = colors["contentColor"]?.toColor()
                    ?: Color(defaultColors.privateField("contentColor")),
                disabledContainerColor = colors["contentColor"]?.toColor()
                    ?: Color(defaultColors.privateField("disabledContainerColor")),
                disabledContentColor = colors["disabledContentColor"]?.toColor()
                    ?: Color(defaultColors.privateField("disabledContentColor"))
            )
            TopAppBarDefaults.topAppBarColors(
                containerColor = colors["containerColor"]?.toColor()
                    ?: Color(defaultColors.privateField("containerColor")),
                scrolledContainerColor = colors["scrolledContainerColor"]?.toColor()
                    ?: Color(defaultColors.privateField("scrolledContainerColor")),
                navigationIconContentColor = colors["navigationIconContentColor"]?.toColor()
                    ?: Color(defaultColors.privateField("navigationIconContentColor")),
                titleContentColor = colors["titleContentColor"]?.toColor()
                    ?: Color(defaultColors.privateField("titleContentColor")),
                actionIconContentColor = colors["actionIconContentColor"]?.toColor()
                    ?: Color(defaultColors.privateField("actionIconContentColor")),
            )
        }
    }
}

object TopAppBarDtoFactory : ComposableViewFactory<TopAppBarDTO, TopAppBarDTO.Builder>() {
    override fun buildComposableView(
        attributes: List<CoreAttribute>,
        children: List<CoreNodeElement>?,
        pushEvent: PushEvent?
    ): TopAppBarDTO {
        val builder = TopAppBarDTO.Builder()

        attributes.forEach { attribute ->
            when (attribute.name) {
                "colors" -> builder.colors(attribute.value)
                "size" -> builder.size(attribute.value)
                "height" -> builder.height(attribute.value)
                "width" -> builder.width(attribute.value)
                "padding" -> builder.padding(attribute.value)
                "horizontalPadding" -> builder.horizontalPadding(attribute.value)
                "verticalPadding" -> builder.verticalPadding(attribute.value)
            }
        }
        return builder.build()
    }

    const val titleTag = "Title"
    const val actionTag = "Action"
    const val navigationIconTag = "NavIcon"
}