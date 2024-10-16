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
            object : ButtonParentActionHandler {
                override fun buttonParentMustHandleAction(buttonNode: ComposableTreeNode?): Boolean {
                    return phxSubmit?.isNotEmpty() == true
                            && buttonNode?.node?.tag == LiveFormTypes.submitButton
                }

                override fun handleAction(
                    composableNode: ComposableTreeNode?,
                    pushEvent: PushEvent
                ) {
                    if (phxSubmit != null) {
                        pushEvent.invoke(EVENT_TYPE_CLICK, phxSubmit, formDataHolder.data, null)
                    }
                }
            }
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

        private fun action(props: Properties, action: String): Properties {
            return props.copy(action = action)
        }

        private fun method(props: Properties, method: String): Properties {
            return props.copy(method = method.uppercase())
        }

        private fun name(props: Properties, name: String): Properties {
            return props.copy(name = name)
        }

        private fun phxChange(props: Properties, phxChange: String): Properties {
            return props.copy(phxChange = phxChange)
        }

        private fun phxSubmit(props: Properties, phxSubmit: String): Properties {
            return props.copy(phxSubmit = phxSubmit)
        }

        private fun phxTriggerAction(props: Properties, phxTriggerAction: String): Properties {
            return props.copy(phxTriggerAction = phxTriggerAction.toBooleanStrictOrNull() ?: false)
        }
    }
}

class FormDataHolder(private val event: String, private val pushEvent: PushEvent?) :
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