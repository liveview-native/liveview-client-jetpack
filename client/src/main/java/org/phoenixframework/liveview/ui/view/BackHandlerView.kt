package org.phoenixframework.liveview.ui.view

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList
import org.phoenixframework.liveview.constants.Attrs.attrEnabled
import org.phoenixframework.liveview.constants.Attrs.attrPhxKeyUp
import org.phoenixframework.liveview.foundation.data.core.CoreAttribute
import org.phoenixframework.liveview.foundation.domain.ComposableTreeNode
import org.phoenixframework.liveview.foundation.ui.base.CommonComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableView
import org.phoenixframework.liveview.foundation.ui.base.ComposableViewFactory
import org.phoenixframework.liveview.foundation.ui.base.PushEvent

/**
 * An effect for handling presses of the system back button. If this is called by nested components,
 * if enabled, the inner most composable will consume the call to system back and invoke its event.
 * The call will continue to propagate up until it finds an enabled BackHandler.
 * ```
 * <BackHandler enabled="true" phx-keyup="onBackPressed" />
 * ```
 */
internal class BackHandlerView private constructor(props: Properties) :
    ComposableView<BackHandlerView.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent
    ) {
        val enabled = props.enabled
        val onBack = props.onBack

        BackHandler(
            enabled = enabled,
            onBack = {
                pushEvent(EVENT_TYPE_KEY_UP, onBack, mergeValueWithPhxValue(KEY_KEY, "Back"), null)
            }
        )
    }

    companion object {
        private const val KEY_KEY = "key"
    }

    @Stable
    internal data class Properties(
        val enabled: Boolean = false,
        val onBack: String = "",
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : ComposableProperties

    internal object Factory : ComposableViewFactory<BackHandlerView>() {
        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>,
            pushEvent: PushEvent?,
            scope: Any?
        ): BackHandlerView = BackHandlerView(
            attributes.fold(Properties()) { props, attribute ->
                when (attribute.name) {
                    attrEnabled -> enabled(props, attribute.value)
                    attrPhxKeyUp -> onBack(props, attribute.value)
                    else -> props.copy(
                        commonProps = handleCommonAttributes(
                            props.commonProps,
                            attribute,
                            pushEvent,
                            scope
                        )
                    )
                }
            }
        )

        /**
         * Sets the event name to be triggered on the server when the back button is pressed.
         *
         * ```
         * <BackHandler phx-keyup="onBackPressed">
         * ```
         * @param event event name defined on the server to handle the back press.
         */
        private fun onBack(props: Properties, event: String): Properties {
            return props.copy(onBack = event)
        }

        /**
         * Defines if the back handler is enabled.
         *
         * ```
         * <BackHandler enabled="true" />
         * ```
         * @param enabled true if the button is enabled, false otherwise.
         */
        private fun enabled(props: Properties, enabled: String): Properties {
            return props.copy(enabled = enabled.toBoolean())
        }
    }
}