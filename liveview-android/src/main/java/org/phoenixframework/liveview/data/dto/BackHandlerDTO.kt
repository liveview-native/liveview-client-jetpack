package org.phoenixframework.liveview.data.dto

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList
import org.phoenixframework.liveview.data.constants.Attrs.attrEnabled
import org.phoenixframework.liveview.data.constants.Attrs.attrPhxKeyUp
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.CommonComposableProperties
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableBuilder.Companion.EVENT_TYPE_KEY_UP
import org.phoenixframework.liveview.domain.base.ComposableProperties
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.ComposableViewFactory
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.factory.ComposableTreeNode

/**
 * An effect for handling presses of the system back button. If this is called by nested components,
 * if enabled, the inner most composable will consume the call to system back and invoke its event.
 * The call will continue to propagate up until it finds an enabled BackHandler.
 * ```
 * <BackHandler enabled="true" phx-keyup="onBackPressed" />
 * ```
 */
internal class BackHandlerDTO private constructor(props: Properties) :
    ComposableView<BackHandlerDTO.Properties>(props) {

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

    internal class Builder : ComposableBuilder() {
        private var enabled: Boolean = false
        private var onBack: String = ""

        /**
         * Sets the event name to be triggered on the server when the back button is pressed.
         *
         * ```
         * <BackHandler phx-keyup="onBackPressed">
         * ```
         * @param event event name defined on the server to handle the back press.
         */
        fun onBack(event: String) = apply {
            this.onBack = event
        }

        /**
         * Defines if the back handler is enabled.
         *
         * ```
         * <BackHandler enabled="true" />
         * ```
         * @param enabled true if the button is enabled, false otherwise.
         */
        fun enabled(enabled: String) = apply {
            this.enabled = enabled.toBoolean()
        }

        fun build() = BackHandlerDTO(
            Properties(enabled, onBack, commonProps)
        )
    }
}

internal object BackHandlerDtoFactory : ComposableViewFactory<BackHandlerDTO>() {
    override fun buildComposableView(
        attributes: ImmutableList<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?
    ): BackHandlerDTO = BackHandlerDTO.Builder().also {
        attributes.fold(
            it
        ) { builder, attribute ->
            when (attribute.name) {
                attrEnabled -> builder.enabled(attribute.value)
                attrPhxKeyUp -> builder.onBack(attribute.value)
                else -> builder.handleCommonAttributes(attribute, pushEvent, scope)
            } as BackHandlerDTO.Builder
        }
    }.build()
}