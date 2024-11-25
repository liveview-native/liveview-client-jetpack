package org.phoenixframework.liveview.foundation.ui.base

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toImmutableMap
import org.phoenixframework.liveview.LiveViewJetpack
import org.phoenixframework.liveview.constants.Attrs.attrValue
import org.phoenixframework.liveview.foundation.data.constants.CoreAttrs.attrClass
import org.phoenixframework.liveview.foundation.data.constants.CoreAttrs.attrPhxClick
import org.phoenixframework.liveview.foundation.data.constants.CoreAttrs.attrPhxValue
import org.phoenixframework.liveview.foundation.data.constants.CoreAttrs.attrPhxValueNamed
import org.phoenixframework.liveview.foundation.data.constants.CoreAttrs.attrStyle
import org.phoenixframework.liveview.foundation.data.core.CoreAttribute
import org.phoenixframework.liveview.foundation.domain.ComposableTreeNode
import org.phoenixframework.liveview.foundation.ui.base.ComposableView.Companion.KEY_PHX_VALUE
import org.phoenixframework.liveview.foundation.ui.view.onClickFromString

/**
 *  A `ComposableView` is the parent class of all components. Subclasses must implement the
 *  `Composable` function in order to call the real composable. The `modifier` param and all
 *  necessary params for the actual component implementation should be provided by a subclass of
 *  `ComposableViewFactory` which must be implemented and must be registered on `ComposableRegistry`
 *  object informing the respective tag for the composable.
 */
abstract class ComposableView<CP : ComposableProperties>(protected open val props: CP) {

    @Composable
    abstract fun Compose(
        composableNode: ComposableTreeNode?, paddingValues: PaddingValues?, pushEvent: PushEvent
    )

    // This function is used to merge/join "on changed" values with the component value(s)
    // (phx-value and phx-value-*). For example, when a checkbox changes its checked state, the new
    // checked value (true/false) and the checkbox values (phx-value/phx-value-*) are sent to server
    protected fun mergeValueWithPhxValue(key: String, value: Any?): Any? {
        val currentPhxValue = props.commonProps.phxValue
        return if (currentPhxValue == null) {
            if (key == KEY_PHX_VALUE) {
                value
            } else {
                mapOf(key to value)
            }
        } else if (props.commonProps.value.size == 1 && props.commonProps.value.containsKey(
                KEY_PHX_VALUE
            )
        ) {
            if (key == KEY_PHX_VALUE) {
                value
            } else {
                val newMap = props.commonProps.value.toMutableMap()
                newMap[key] = value
                newMap
            }
        } else {
            val newMap = props.commonProps.value.toMutableMap()
            newMap[key] = value
            newMap
        }
    }

    protected fun pushOnChangeEvent(pushEvent: PushEvent, event: String?, value: Any?) {
        if (!event.isNullOrEmpty()) {
            pushEvent.invoke(EVENT_TYPE_CHANGE, event, value, null)
        }
    }

    companion object {
        const val EVENT_TYPE_CHANGE = "change"
        const val EVENT_TYPE_CLICK = "click"
        const val EVENT_TYPE_DOUBLE_CLICK = "double-click"
        const val EVENT_TYPE_FORM = "form"
        const val EVENT_TYPE_FOCUS_CHANGED = "focus-changed"
        const val EVENT_TYPE_FOCUS_EVENT = "focus-event"
        const val EVENT_TYPE_KEY_UP = "keyup"
        const val EVENT_TYPE_LONG_CLICK = "long-click"
        const val EVENT_TYPE_BLUR = "blur"
        const val EVENT_TYPE_SUBMIT = "submit"
        const val KEY_PHX_VALUE = "value"

        private val viewValues = mutableMapOf<String, Any>()

        fun saveViewValue(viewId: String, value: Any) {
            viewValues[viewId] = value
        }

        fun removeViewValue(viewId: String) {
            viewValues.remove(viewId)
        }

        fun getViewValue(viewId: String): Any? {
            return viewValues[viewId]
        }
    }
}

/**
 * This interface represents the properties received by a composable to create itself. Subclasses
 * can implement this interface to add properties specific for a particular component.
 */
interface ComposableProperties {
    val commonProps: CommonComposableProperties
}

/**
 * This class contain common properties that can be used by all components.
 */
@Stable
data class CommonComposableProperties(
    val modifier: Modifier = Modifier,
    val value: ImmutableMap<String, Any?> = persistentMapOf()
) {
    val phxValue: Any?
        get() = if (value.isEmpty())
            null
        else if (value.size == 1 && value.containsKey(KEY_PHX_VALUE)) {
            value[KEY_PHX_VALUE]
        } else {
            value
        }
}

/**
 * A `ComposableViewFactory` is responsible to create a `ComposableView` using a list of attributes.
 */
abstract class ComposableViewFactory<CV : ComposableView<*>> {

    private val modifierParser by lazy {
        LiveViewJetpack.getModifiersParser()
    }

    /**
     * Create a new instance of a `ComposableView`. Subclasses of this class must override this
     * method and handle specific attributes. Common attributes are handled by the
     * `handleCommonAttributes`.
     * @param attributes a list of `CoreAttribute` to be handled.
     * @param pushEvent function responsible to dispatch the server call.
     * @param scope some attributes are composable specific, the scope determine what parent
     * composable (e.g.: `Column`, `Row`, `Box`).
     */
    abstract fun buildComposableView(
        attributes: ImmutableList<CoreAttribute>,
        pushEvent: PushEvent?,
        scope: Any?
    ): CV

    /**
     * Subclasses of ComposableViewFactory can register subtags specific for a particular component.
     * See ComposableRegistry and ComposableNodeFactory for more details.
     */
    open fun subTags(): Map<String, ComposableViewFactory<*>> = emptyMap()

    /**
     * Handle the properties that are common for most of composables.
     * @param attribute a `CoreAttribute` to be handled.
     * @param pushEvent function responsible to dispatch the server call.
     * @param scope some attributes are composable specific, the scope determine what parent
     * composable (e.g.: `Column`, `Row`, `Box`).
     */
    fun handleCommonAttributes(
        commonProps: CommonComposableProperties,
        attribute: CoreAttribute,
        pushEvent: PushEvent?,
        scope: Any?,
    ): CommonComposableProperties {
        return when (attribute.name) {
            attrClass -> setClassFromAttr(commonProps, attribute.value, scope, pushEvent)
            attrPhxClick -> setPhxClickFromAttr(commonProps, attribute.value, pushEvent)
            attrPhxValue, attrValue ->
                setPhxValueFromAttr(commonProps, attrPhxValue, attribute.value)

            attrStyle -> setStyleFromAttr(commonProps, attribute.value, scope, pushEvent)
            else ->
                if (attribute.name.startsWith(attrPhxValueNamed)) {
                    setPhxValueFromAttr(commonProps, attribute.name, attribute.value)
                } else
                    commonProps
        }
    }

    /**
     * Sets a predefined style/modifier (declared in app.jetpack.styles file on the server) to a
     * component.
     *
     * ```
     * <Composable class="yourPredefinedStyle" />
     * ```
     */
    private fun setClassFromAttr(
        commonProps: CommonComposableProperties,
        string: String,
        scope: Any?,
        pushEvent: PushEvent?
    ): CommonComposableProperties {
        val modifier = commonProps.modifier
        return commonProps.copy(
            modifier = modifier.then(modifierParser.run {
                modifier.fromStyleName(
                    string,
                    scope,
                    pushEvent
                )
            })
        )
    }

    /**
     * Sets the event name to be triggered on the server when the composable is clicked.
     *
     * ```
     * <Composable phx-click="yourServerEventHandler" />
     * ```
     * @param event event name defined on the server to handle the composable's click.
     * @param pushEvent function responsible to dispatch the server call.
     */
    private fun setPhxClickFromAttr(
        commonProps: CommonComposableProperties,
        event: String,
        pushEvent: PushEvent?
    ): CommonComposableProperties {
        val modifier = commonProps.modifier
        return commonProps.copy(
            modifier = modifier.then(
                Modifier.clickable {
                    onClickFromString(pushEvent, event, commonProps.phxValue).invoke()
                }
            )
        )
    }

    /**
     * Sets the phx-value binding.
     *
     * ```
     * <Composable phx-value="someValue" />
     * ```
     * @param value event name defined on the server to handle the composable's click.
     */
    protected fun setPhxValueFromAttr(
        commonProps: CommonComposableProperties,
        attributeName: String,
        value: Any
    ): CommonComposableProperties {
        return if (attributeName == attrPhxValue) {
            val newMap = commonProps.value.toMutableMap()
            newMap[KEY_PHX_VALUE] = value
            commonProps.copy(value = newMap.toImmutableMap())
        } else if (attributeName.startsWith(attrPhxValueNamed)) {
            val phxValueKey = attributeName.substring(attrPhxValueNamed.length)
            val newMap = commonProps.value.toMutableMap()
            newMap[phxValueKey] = value
            commonProps.copy(value = newMap.toImmutableMap())
        } else commonProps
    }

    /**
     * Sets a style/modifier directly on the component. The styles/modifiers must be separated by
     * ";".
     * ```
     * <Composable style="size(100.dp);background(Color.Red)" />
     * ```
     */
    private fun setStyleFromAttr(
        commonProps: CommonComposableProperties,
        style: String,
        scope: Any?,
        pushEvent: PushEvent?
    ): CommonComposableProperties {
        val modifier = commonProps.modifier
        return commonProps.copy(
            modifier = style
                .split(";")
                .map { it.trim() }
                .fold(modifier) { acc, modifierKey ->
                    acc.then(
                        modifierParser.run {
                            modifier.fromStyleName(modifierKey, scope, pushEvent)
                        }
                    )
                }
        )
    }
}

/**
 * This local composition allows children nodes to inform their values to parent nodes. For instance,
 * an input component can notify its value to the form where the input is contained.
 * See `LiveForm` component for an example of usage.
 */
val LocalParentDataHolder = compositionLocalOf<ParentViewDataHolder?> { null }

interface ParentViewDataHolder {
    fun setValue(node: ComposableTreeNode?, value: Any?)
}