package org.phoenixframework.liveview.data.dto

import androidx.compose.runtime.Stable
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import org.phoenixframework.liveview.data.constants.Attrs.attrEnabled
import org.phoenixframework.liveview.data.constants.Attrs.attrPhxChange
import org.phoenixframework.liveview.data.constants.Attrs.attrPhxDebounce
import org.phoenixframework.liveview.data.constants.Attrs.attrPhxThrottle
import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableProperties
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.throttleLatest

/**
 * Common class for ComposableViews which the user can change the component value to be displayed.
 * This class holds values like debounce and throttle times to send change value event to the
 * server. It also contains the component value and if it is enabled or not.
 * Examples of subclasses of this class are: `CheckBoxDTO`, `SliderDTO` and `TextFieldDTO`.
 */
internal abstract class ChangeableDTO<T : Any, CP : IChangeableProperties>(properties: CP) :
    ComposableView<IChangeableProperties>(properties) {
    override val props: CP = properties

    @OptIn(FlowPreview::class)
    protected fun <TC> Flow<TC>.onTypedChangeable(): Flow<TC> = this.distinctUntilChanged()
        .drop(1) // Ignoring the first emission when the component is displayed
        .debounce(props.changeableProps.debounce)
        .throttleLatest(props.changeableProps.throttle)

    protected fun Flow<T>.onChangeable(): Flow<T> = onTypedChangeable()

    protected fun pushOnChangeEvent(pushEvent: PushEvent, event: String, value: Any?) {
        pushEvent.invoke(ComposableBuilder.EVENT_TYPE_CHANGE, event, value, null)
    }
}

/**
 * Extension of Composable properties adding properties specific for components that can change its
 * internal value.
 */
internal interface IChangeableProperties : ComposableProperties {
    val changeableProps: ChangeableProperties
}

@Stable
internal data class ChangeableProperties(
    val onChange: String? = null,
    val debounce: Long = DEFAULT_DEBOUNCE,
    val throttle: Long = DEFAULT_THROTTLE,
    val enabled: Boolean = true,
)

/**
 * Common builder class for components that use `phx-change` attribute. These components can use
 * the properties `phx-debounce` and `phx-throttle` in order to reduce the number of socket calls
 * from the client.
 */
internal abstract class ChangeableDTOBuilder : ComposableBuilder() {
    protected var changeableProps: ChangeableProperties = ChangeableProperties()

    /**
     * Sets the event name to triggered on the server when the component's value changes.
     *
     * ```
     * <TextField phx-change="setUserName">...</TextField>
     * ```
     * @param event event name defined on the server to handle the component's value changes.
     */
    private fun onChange(event: String) = apply {
        this.changeableProps = this.changeableProps.copy(onChange = event)
    }

    /**
     * Set the delay (in milliseconds) for emitting the change value's event.
     * Default value is 300ms.
     * ```
     * <TextField phx-debounce="300">...</TextField>
     * ```
     * @param debounce delay (in milliseconds) for emitting the change value's event.
     */
    private fun debounce(debounce: String) = apply {
        this.changeableProps =
            this.changeableProps.copy(debounce = debounce.toLongOrNull() ?: DEFAULT_DEBOUNCE)
    }

    /**
     * Accepts an integer timeout value to throttle the event in milliseconds. Unlike debounce,
     * throttle will immediately emit the event, then rate limit it at once per provided timeout.
     * Default value is 300ms.
     *
     * ```
     * <TextField phx-throttle="300">...</TextField>
     * ```
     * @param throttle rate limit (in milliseconds) for change value's event calls.
     */
    private fun throttle(throttle: String) = apply {
        this.changeableProps =
            this.changeableProps.copy(throttle = throttle.toLongOrNull() ?: DEFAULT_THROTTLE)
    }

    /**
     * A boolean value indicating if the component is enabled or not.
     *
     * ```
     * <TextField enabled="true">...</TextField>
     * ```
     * @param enabled true if the component is enabled, false otherwise.
     */
    private fun enabled(enabled: String) = apply {
        this.changeableProps = this.changeableProps.copy(enabled = enabled.toBoolean())
    }

    /**
     * Handle common attributes for value changeable components like `TextField`, `Slider`, etc.
     * The attributes handled are: `phx-change`, `phx-debounce`, `phx-throttle` and `enabled`.
     *
     * @param attribute attribute to be handled.
     */
    fun handleChangeableAttribute(attribute: CoreAttribute): Boolean {
        var result = true
        when (attribute.name) {
            attrEnabled -> enabled(attribute.value)
            attrPhxChange -> onChange(attribute.value)
            attrPhxDebounce -> debounce(attribute.value)
            attrPhxThrottle -> throttle(attribute.value)
            else -> result = false
        }
        return result
    }
}

private const val DEFAULT_THROTTLE: Long = 300L
private const val DEFAULT_DEBOUNCE: Long = 300L