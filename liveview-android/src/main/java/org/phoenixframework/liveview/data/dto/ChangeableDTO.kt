package org.phoenixframework.liveview.data.dto

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
import org.phoenixframework.liveview.domain.base.ComposableView
import org.phoenixframework.liveview.domain.base.PushEvent
import org.phoenixframework.liveview.domain.extensions.throttleLatest

/**
 * Common class for ComposableViews which the user can change the component value to be displayed.
 * This class holds values like debounce and throttle times to send change value event to the
 * server. It also contains the component value and if it is enabled or not.
 * Examples of subclasses of this class are: `CheckBoxDTO`, `SliderDTO` and `TextFieldDTO`.
 */
internal abstract class ChangeableDTO<T : Any>(builder: ChangeableDTOBuilder<T>) :
    ComposableView(builder.modifier) {
    protected val debounce = builder.debounce
    protected val throttle = builder.throttle
    protected val changeValueEventName = builder.onChange
    protected val enabled = builder.enabled
    protected val value = builder.value as T

    protected fun <TC> Flow<TC>.onTypedChangeable(): Flow<TC> =
        this.distinctUntilChanged()
            .drop(1) // Ignoring the first emission when the component is displayed
            .debounce(debounce)
            .throttleLatest(throttle)

    protected fun Flow<T>.onChangeable(): Flow<T> = onTypedChangeable()

    protected fun pushOnChangeEvent(pushEvent: PushEvent, event: String, value: T) {
        pushEvent.invoke(ComposableBuilder.EVENT_TYPE_CHANGE, event, value, null)
    }
}

/**
 * Common builder class for components that use `phx-change` attribute. These components can use
 * the properties `phx-debounce` and `phx-throttle` in order to reduce the number of socket calls
 * from the client.
 */
internal abstract class ChangeableDTOBuilder<T : Any>(defaultValue: T) : ComposableBuilder() {
    var onChange: String? = null
        private set
    var debounce: Long = 300
        private set
    var throttle: Long = 300L
        private set
    var enabled: Boolean = true
        private set

    init {
        value(defaultValue)
    }

    /**
     * Sets the event name to triggered on the server when the component's value changes.
     *
     * ```
     * <TextField phx-change="setUserName">...</TextField>
     * ```
     * @param event event name defined on the server to handle the component's value changes.
     */
    private fun onChange(event: String) = apply {
        this.onChange = event
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
        this.debounce = debounce.toLongOrNull() ?: 300
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
        this.throttle = throttle.toLongOrNull() ?: 300
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
        this.enabled = enabled.toBoolean()
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