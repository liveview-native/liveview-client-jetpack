package org.phoenixframework.liveview.ui.view

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import kotlinx.collections.immutable.ImmutableList
import org.phoenixframework.liveview.data.constants.Attrs.attrNavigate
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.ui.base.CommonComposableProperties
import org.phoenixframework.liveview.ui.base.ComposableBuilder
import org.phoenixframework.liveview.ui.base.ComposableProperties
import org.phoenixframework.liveview.ui.base.ComposableView
import org.phoenixframework.liveview.ui.base.ComposableViewFactory
import org.phoenixframework.liveview.ui.base.PushEvent
import org.phoenixframework.liveview.domain.data.ComposableTreeNode
import org.phoenixframework.liveview.ui.phx_components.LocalNavController
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView
import org.phoenixframework.liveview.ui.phx_components.generateRelativePath
import org.phoenixframework.liveview.ui.phx_components.getCurrentRoute
import org.phoenixframework.liveview.ui.phx_components.createRoute

/**
 * Component that allows a local navigation to a different path declared in the application route.
 * ```
 * <Link navigate="otherPage"><Text>Link to another page</Text></Link>
 * ```
 */
internal class LinkView private constructor(props: Properties) :
    ComposableView<LinkView.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val context = LocalContext.current
        val navController = LocalNavController.current
        val modifier = props.commonProps.modifier.then(
            Modifier.clickable {
                if (props.navigationPath.startsWith("http://") || props.navigationPath.startsWith("https://")) {
                    context.startActivity(Intent(ACTION_VIEW, Uri.parse(props.navigationPath)))
                } else {
                    navController.navigate(
                        createRoute(
                            generateRelativePath(
                                getCurrentRoute(navController),
                                props.navigationPath
                            )
                        )
                    )
                }
            }
        )
        Box(modifier) {
            composableNode?.children?.forEach {
                PhxLiveView(it, pushEvent, composableNode, null, this)
            }
        }
    }

    internal class Builder : ComposableBuilder() {

        private var navigatePath: String = ""

        fun navigate(path: String) = apply {
            this.navigatePath = path
        }

        fun build() = LinkView(Properties(navigatePath, commonProps))
    }

    @Stable
    data class Properties(
        val navigationPath: String,
        override val commonProps: CommonComposableProperties,
    ) : ComposableProperties
}

internal object LinkViewFactory : ComposableViewFactory<LinkView>() {
    override fun buildComposableView(
        attributes: ImmutableList<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?,
    ): LinkView = attributes.fold(LinkView.Builder()) { builder, attribute ->
        when (attribute.name) {
            attrNavigate -> builder.navigate(attribute.value)
            else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
        } as LinkView.Builder
    }.build()
}
