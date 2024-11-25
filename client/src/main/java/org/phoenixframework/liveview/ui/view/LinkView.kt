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
import org.phoenixframework.liveview.constants.Attrs.attrNavigate
import org.phoenixframework.liveview.foundation.data.constants.HttpMethod.GET
import org.phoenixframework.liveview.foundation.data.core.CoreAttribute
import org.phoenixframework.liveview.foundation.domain.ComposableTreeNode
import org.phoenixframework.liveview.foundation.ui.base.CommonComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableView
import org.phoenixframework.liveview.foundation.ui.base.ComposableViewFactory
import org.phoenixframework.liveview.foundation.ui.base.PushEvent
import org.phoenixframework.liveview.ui.phx_components.LocalNavigation
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView

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
        val navController = LocalNavigation.current
        val modifier = props.commonProps.modifier.then(
            Modifier.clickable {
                if (props.navigationPath.startsWith("http://") || props.navigationPath.startsWith("https://")) {
                    context.startActivity(Intent(ACTION_VIEW, Uri.parse(props.navigationPath)))
                } else {
                    navController.navigate(props.navigationPath, GET, emptyMap(), false)
                }
            }
        )
        Box(modifier) {
            composableNode?.children?.forEach {
                PhxLiveView(it, pushEvent, composableNode, null, this)
            }
        }
    }

    @Stable
    data class Properties(
        val navigationPath: String = "",
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : ComposableProperties


    internal object Factory : ComposableViewFactory<LinkView>() {
        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>,
            pushEvent: PushEvent?,
            scope: Any?,
        ): LinkView = LinkView(attributes.fold(Properties()) { props, attribute ->
            when (attribute.name) {
                attrNavigate -> navigate(props, attribute.value)
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

        private fun navigate(props: Properties, path: String): Properties {
            return props.copy(navigationPath = path)
        }
    }
}
