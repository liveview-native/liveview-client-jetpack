package org.phoenixframework.liveview.data.dto

import androidx.compose.material.Icon
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.extensions.toColor

class TopAppBarDTO private constructor(builder: Builder) :
    ComposableView(modifier = builder.modifier) {
    private val actionIcons: List<IconDTO> = builder.actionIcons
    private val navIcons: List<IconDTO> = builder.navIcons
    private val textDTO: TextDTO? = builder.textDTO

    @Composable
    fun Compose() {
        val navigationIcon: @Composable (() -> Unit)? =
            navIcons
                .takeUnless(List<IconDTO>::isEmpty)
                ?.let {
                    {
                        it.forEach { icon ->
                            icon.imageVector?.let { imageVector ->
                                Icon(
                                    imageVector = imageVector,
                                    contentDescription = icon.contentDescription,
                                    tint = icon.tint
                                )
                            }
                        }
                    }
                }

        TopAppBar(
            backgroundColor = Color.White,
            title = {
                textDTO?.Compose(paddingValues = null)
            },
            navigationIcon = navigationIcon,
            actions = {
                actionIcons.forEach { actionIcon ->
                    actionIcon.Compose(paddingValues = null)
                }
            },
            modifier = modifier
        )
    }

    class Builder : ComposableBuilder() {
        val navIcons = mutableListOf<IconDTO>()
        val actionIcons = mutableListOf<IconDTO>()
        var backgroundColor = Color.White
        var textDTO: TextDTO? = null

        fun backgroundColor(color: String) = apply {
            if (color.isNotEmpty()) {
                backgroundColor = color.toColor()
            }
        }

        fun addNavIcon(navIcon: IconDTO) = apply {
            navIcons.add(navIcon)
        }

        fun addActionIcon(actionIcon: IconDTO) = apply {
            actionIcons.add(actionIcon)
        }

        fun build() = TopAppBarDTO(this)
    }
}

