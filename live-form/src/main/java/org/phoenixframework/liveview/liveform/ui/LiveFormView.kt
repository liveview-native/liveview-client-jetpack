package org.phoenixframework.liveview.liveform.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView
import org.phoenixframework.liveview.ui.view.LocalParentButtonAction

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

        val formDataHolder = remember(composableNode?.id) {
            FormDataHolder(phxChange, pushEvent)
        }
        val submitAction = { _: Any? ->
            if (phxSubmit != null) {
                pushEvent.invoke(EVENT_TYPE_CLICK, phxSubmit, formDataHolder.data, null)
            }
        }
        Column(
            modifier = props.commonProps.modifier
                .paddingIfNotNull(paddingValues),
            content = {
                CompositionLocalProvider(
                    LocalParentButtonAction provides submitAction,
                    LocalParentDataHolder provides formDataHolder
                ) {
                    composableNode?.children?.forEach {
                        PhxLiveView(it, pushEvent, composableNode, null, this)
                    }
                }
            }
        )
    }

    @Stable
    data class Properties(
        val name: String = "",
        val phxChange: String? = null,
        val phxSubmit: String? = null,
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
                    attrName -> name(props, attribute.value)
                    attrPhxChange -> phxChange(props, attribute.value)
                    attrPhxSubmit -> phxSubmit(props, attribute.value)
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

        private fun name(props: Properties, name: String): Properties {
            return props.copy(name = name)
        }

        private fun phxChange(props: Properties, phxChange: String): Properties {
            return props.copy(phxChange = phxChange)
        }

        private fun phxSubmit(props: Properties, phxSubmit: String): Properties {
            return props.copy(phxSubmit = phxSubmit)
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
                formData[name] = value.toString() // nameAttribute.value
                if (pushValue) {
                    pushEvent?.invoke(EVENT_TYPE_CHANGE, event, formData, null)
                }
            }
        }
    }
}