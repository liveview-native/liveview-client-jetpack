package org.phoenixframework.liveview.liveform.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap
import org.phoenixframework.liveview.constants.Attrs.attrName
import org.phoenixframework.liveview.constants.Attrs.attrPhxChange
import org.phoenixframework.liveview.constants.Attrs.attrPhxSubmit
import org.phoenixframework.liveview.extensions.paddingIfNotNull
import org.phoenixframework.liveview.foundation.data.core.CoreAttribute
import org.phoenixframework.liveview.foundation.domain.ComposableTreeNode
import org.phoenixframework.liveview.foundation.ui.base.CommonComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableView
import org.phoenixframework.liveview.foundation.ui.base.ComposableView.Companion.EVENT_TYPE_CHANGE
import org.phoenixframework.liveview.foundation.ui.base.ComposableView.Companion.EVENT_TYPE_CLICK
import org.phoenixframework.liveview.foundation.ui.base.ComposableViewFactory
import org.phoenixframework.liveview.foundation.ui.base.LocalParentDataHolder
import org.phoenixframework.liveview.foundation.ui.base.ParentViewDataHolder
import org.phoenixframework.liveview.foundation.ui.base.PushEvent
import org.phoenixframework.liveview.liveform.constants.Attrs.attrAction
import org.phoenixframework.liveview.liveform.constants.Attrs.attrMethod
import org.phoenixframework.liveview.liveform.constants.Attrs.attrPhxTriggerAction
import org.phoenixframework.liveview.liveform.constants.LiveFormTypes
import org.phoenixframework.liveview.ui.phx_components.LocalNavigation
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView
import org.phoenixframework.liveview.ui.view.ButtonParentActionHandler
import org.phoenixframework.liveview.ui.view.LocalButtonParentActionHandler

internal class LiveFormView private constructor(props: Properties) :
    ComposableView<LiveFormView.Properties>(props) {

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent,
    ) {
        val phxChange = props.phxChange ?: ""
        val phxSubmit = props.phxSubmit
        val phxTriggerAction = props.phxTriggerAction
        val action = props.action
        val method = props.method ?: "GET"

        val formDataHolder = remember(composableNode?.id) {
            FormDataHolder(phxChange, pushEvent)
        }
        val submitActionHandler = remember(phxSubmit) {
            SubmitButtonActionHandler(phxSubmit, formDataHolder)
        }
        Column(
            modifier = props.commonProps.modifier.paddingIfNotNull(paddingValues),
            content = {
                CompositionLocalProvider(
                    LocalButtonParentActionHandler provides submitActionHandler,
                    LocalParentDataHolder provides formDataHolder
                ) {
                    composableNode?.children?.forEach {
                        PhxLiveView(it, pushEvent, composableNode, null, this)
                    }
                }
            }
        )
        val navigationController = LocalNavigation.current
        LaunchedEffect(phxTriggerAction) {
            if (phxTriggerAction && action?.isNotEmpty() == true) {
                navigationController.navigate(action, method, formDataHolder.data, true)
            }
        }
    }

    @Stable
    data class Properties(
        val name: String = "",
        val phxChange: String? = null,
        val phxSubmit: String? = null,
        val phxTriggerAction: Boolean = false,
        val action: String? = null,
        val method: String? = null,
        override val commonProps: CommonComposableProperties = CommonComposableProperties(),
    ) : ComposableProperties

    internal object Factory : ComposableViewFactory<LiveFormView>() {

        override fun buildComposableView(
            attributes: ImmutableList<CoreAttribute>,
            pushEvent: PushEvent?,
            scope: Any?,
        ) = LiveFormView(
            attributes.fold(
                Properties()
            ) { props, attribute ->
                when (attribute.name) {
                    attrAction -> action(props, attribute.value)
                    attrMethod -> method(props, attribute.value)
                    attrName -> name(props, attribute.value)
                    attrPhxChange -> phxChange(props, attribute.value)
                    attrPhxSubmit -> phxSubmit(props, attribute.value)
                    attrPhxTriggerAction -> phxTriggerAction(props, attribute.value)
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
         * Sets the form action. Usually a path to a new page where the user is redirected and the
         * form data will be received.
         * ```
         * <.simple_form  action="/users/log_in">
         * ```
         */
        private fun action(props: Properties, action: String): Properties {
            return props.copy(action = action)
        }

        /**
         * Sets the form HTTP method. The new HTTP call will be performed using this HTTP method to
         * the destination specified in the `action` property.
         * ```
         * <.simple_form method="POST">
         * ```
         */
        private fun method(props: Properties, method: String): Properties {
            return props.copy(method = method.uppercase())
        }

        /**
         * Sets the server function name to be called when any field in the form change its value.
         * ```
         * <.simple_form phx-change="validateMyForm">
         * ```
         */
        private fun phxChange(props: Properties, phxChange: String): Properties {
            return props.copy(phxChange = phxChange)
        }

        /**
         * Sets the form name.
         * ```
         * <.simple_form name="myForm">
         * ```
         */
        private fun name(props: Properties, name: String): Properties {
            return props.copy(name = name)
        }

        /**
         * Sets the server function name to be called when the submit button is pressed.
         * ```
         * <.simple_form phx-submit="saveMyForm">
         * ```
         */
        private fun phxSubmit(props: Properties, phxSubmit: String): Properties {
            return props.copy(phxSubmit = phxSubmit)
        }

        /**
         * Flag used to determine if the form should trigger a new request to the new destination
         * using the `action` property.
         * ```
         * <.simple_form phx-trigger-action={@trigger_submit}>
         * ```
         */
        private fun phxTriggerAction(props: Properties, phxTriggerAction: String): Properties {
            return props.copy(phxTriggerAction = phxTriggerAction.toBooleanStrictOrNull() ?: false)
        }
    }
}

internal class SubmitButtonActionHandler(
    private val phxSubmit: String?,
    private val dataHolder: FormDataHolder,
) : ButtonParentActionHandler {
    override fun buttonParentMustHandleAction(buttonNode: ComposableTreeNode?): Boolean {
        return phxSubmit?.isNotEmpty() == true
                && buttonNode?.node?.tag == LiveFormTypes.submitButton
    }

    override fun handleAction(
        composableNode: ComposableTreeNode?,
        pushEvent: PushEvent
    ) {
        if (phxSubmit != null) {
            pushEvent.invoke(EVENT_TYPE_CLICK, phxSubmit, dataHolder.data, null)
        }
    }
}

internal class FormDataHolder(private val event: String, private val pushEvent: PushEvent?) :
    ParentViewDataHolder {
    private val formData = mutableMapOf<String, String>()

    val data: ImmutableMap<String, String>
        get() = formData.toImmutableMap()

    override fun setValue(node: ComposableTreeNode?, value: Any?) {
        if (node != null && event.isNotEmpty()) {
            val nameAttribute = node.node?.attributes?.find { it.name == attrName }
            if (nameAttribute != null) {
                val name = nameAttribute.value
                val pushValue = formData.containsKey(name)
                formData[name] = value.toString()
                if (pushValue) {
                    pushEvent?.invoke(EVENT_TYPE_CHANGE, event, formData, null)
                }
            }
        }
    }
}