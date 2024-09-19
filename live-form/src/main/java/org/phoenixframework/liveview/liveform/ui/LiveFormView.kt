package org.phoenixframework.liveview.liveform.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList
import org.phoenixframework.liveview.LiveViewJetpack
import org.phoenixframework.liveview.constants.Attrs.attrName
import org.phoenixframework.liveview.constants.Attrs.attrPhxChange
import org.phoenixframework.liveview.constants.Attrs.attrPhxSubmit
import org.phoenixframework.liveview.extensions.paddingIfNotNull
import org.phoenixframework.liveview.foundation.data.core.CoreAttribute
import org.phoenixframework.liveview.foundation.domain.ComposableTreeNode
import org.phoenixframework.liveview.foundation.ui.base.CommonComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableProperties
import org.phoenixframework.liveview.foundation.ui.base.ComposableView
import org.phoenixframework.liveview.foundation.ui.base.ComposableViewFactory
import org.phoenixframework.liveview.foundation.ui.base.PushEvent
import org.phoenixframework.liveview.ui.phx_components.PhxLiveView
import org.phoenixframework.liveview.ui.view.PhxChangeNotifier

internal class LiveFormView private constructor(props: Properties) :
    ComposableView<LiveFormView.Properties>(props), PhxChangeNotifier.Listener {

    private val formData = mutableMapOf<String, String>()
    private var pushEvent: PushEvent = { _, _, _, _ -> }
    private var onChange: String = ""

    @Composable
    override fun Compose(
        composableNode: ComposableTreeNode?,
        paddingValues: PaddingValues?,
        pushEvent: PushEvent,
    ) {
        val name = props.name
        val phxChange = props.phxChange
        val phxSubmit = props.phxSubmit

        Column(
            modifier = props.commonProps.modifier
                .paddingIfNotNull(paddingValues),
            content = {
                composableNode?.children?.forEach {
                    PhxLiveView(it, pushEvent, composableNode, null, this)
                }
            }
        )

        DisposableEffect(Unit) {
            this@LiveFormView.pushEvent = pushEvent
            phxChange?.let {
                this@LiveFormView.onChange = it
            }
            //LiveViewJetpack.registerNodeInterceptor(screenId, LiveFormNodeInterceptor)
            registerPhxChangeListener(composableNode, pushEvent)

            onDispose {
                //LiveViewJetpack.unregisterNodeInterceptor(screenId, LiveFormNodeInterceptor)
                LiveViewJetpack.getPhxChangeNotifier().unregisterListener(this@LiveFormView)
            }
        }
    }

    private fun registerPhxChangeListener(node: ComposableTreeNode?, pushEvent: PushEvent) {
        inputChildrenIdNameMap.clear()
        walkthroughNode(node)
        inputChildrenIdNameMap.keys.forEach {
            LiveViewJetpack.getPhxChangeNotifier().registerListener(it, this@LiveFormView)
        }
    }

    private val inputChildrenIdNameMap = mutableMapOf<String, String>()


    private fun walkthroughNode(node: ComposableTreeNode?) {
        if (node != null) {
            val nameAttribute = node.node?.attributes?.find { it.name == attrName }
            if (nameAttribute != null) {
                val id = node.id
                Log.d("NGVL", "INPUT FOUND: id=$id | name=${nameAttribute.value}")
                inputChildrenIdNameMap[id] = nameAttribute.value

            }
            if (node.children.isNotEmpty()) {
                node.children.forEach {
                    walkthroughNode(it)
                }
            }
        }
    }

    override fun onChange(id: String, value: Any?) {
        Log.d("NGVL", "LiveForm::onChange | id===>$id | value=$value")
        val name = inputChildrenIdNameMap[id]
        if (name != null) {
            val pushChange = formData.containsKey(name)
            formData[name] = value.toString()

            if (pushChange) {
                Log.d("NGVL", "formData=$formData")
                pushEvent.invoke(EVENT_TYPE_CHANGE, onChange, formData, null)
            }
        }
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