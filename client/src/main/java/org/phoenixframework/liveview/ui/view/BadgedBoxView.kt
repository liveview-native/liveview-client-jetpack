package org.phoenixframework.liveview.ui.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgeDefaults
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import kotlinx.collections.immutable.ImmutableList
import org.phoenixframework.liveview.constants.Attrs.attrContainerColor
import org.phoenixframework.liveview.constants.Attrs.attrContentColor
import org.phoenixframework.liveview.constants.Templates.templateBadge
import org.phoenixframework.liveview.extensions.toColor
import org.phoenixframework.liveview.foundation.data.core.CoreAttribute
import org.phoenixframework.liveview.foundation.domain.ComposableTreeNode
import org.phoenixframework.liveview.foundation.ui.base.CommonComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableView
import org.phoenixframework.liveview.foundation.ui.base.ComposableViewFactory
import org.phoenixframework.liveview.foundation.ui.base.PushEvent
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView

/**
 * Material Design badge box.
 * It must be at most two children: the one which will appear on the top-right must use the `badge`
 * template; and the other child can be any component, but usually it is an `Icon` like below.
 * ```
 * <BadgedBox>
 *   <Text template="badge">+99</Text>
 *   <Icon imageVector="filled:Add" />
 * </BadgedBox>
 * ```
 */
internal class BadgedBoxView private constructor(props: Properties) :
    ComposableView<BadgedBoxView.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val contentColor = props.contentColor
        val containerColor = props.containerColor

        val badge = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template == templateBadge }
        }
        val contentChild = remember(composableNode?.children) {
            composableNode?.children?.find { it.node?.template != templateBadge }
        }
        BadgedBox(
            badge = {
                badge?.let {
                    Badge(
                        containerColor = containerColor ?: BadgeDefaults.containerColor,
                        contentColor = contentColor
                            ?: contentColorFor(BadgeDefaults.containerColor),
                    ) {
                        PhxLiveView(it, pushEvent, composableNode, null, this)
                    }
                }
            },
            modifier = props.commonProps.modifier,
        ) {
            contentChild?.let {
                PhxLiveView(it, pushEvent, composableNode, null, this)
            }
        }
    }

    @Stable
    internal data class Properties(
        val containerColor: Color? = null,
        val contentColor: Color? = null,
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : ComposableProperties

    internal object Factory : ComposableViewFactory<BadgedBoxView>() {
        /**
         * Creates a `BadgedBoxView` object based on the attributes of the input `Attributes` object.
         * BadgedBoxView co-relates to the BadgedBox composable
         * @param attributes the `Attributes` object to create the `BadgedBoxView` object from
         * @return a `BadgedBoxView` object based on the attributes of the input `Attributes` object
         */
        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>,
            pushEvent: PushEvent?,
            scope: Any?
        ): BadgedBoxView = BadgedBoxView(attributes.fold(Properties()) { props, attribute ->
            when (attribute.name) {
                attrContainerColor -> containerColor(props, attribute.value)
                attrContentColor -> contentColor(props, attribute.value)
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
         * The color used for the background of the BadgedBox.
         *
         * ```
         * <BadgedBox containerColor="#FF0000FF">
         * ```
         * @param containerColor the background color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.constants.SystemColorValues] colors.
         */
        private fun containerColor(props: Properties, containerColor: String): Properties {
            return props.copy(containerColor = containerColor.toColor())
        }

        /**
         * The preferred color for content inside the BadgedBox.
         *
         * ```
         * <BadgedBox contentColor="#FF0000FF" />
         * ```
         * @param contentColor the content color in AARRGGBB format or one of the
         * [org.phoenixframework.liveview.constants.SystemColorValues] colors.
         */
        private fun contentColor(props: Properties, contentColor: String): Properties {
            return props.copy(contentColor = contentColor.toColor())
        }
    }
}