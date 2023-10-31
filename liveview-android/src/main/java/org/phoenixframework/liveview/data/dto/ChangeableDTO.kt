package org.phoenixframework.liveview.data.dto

import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.ComposableBuilder
import org.phoenixframework.liveview.domain.base.ComposableView

/**
 * Common builder class for components that use `phx-change` attribute. These components can use
 * the properties `phx-debounce` and `phx-throttle` in order to reduce the number of socket calls
 * from the client.
 */
abstract class ChangeableDTOBuilder<T : ComposableView, V : Any>(defaultValue: V) :
    ComposableBuilder<T>() {
    var value = defaultValue
        private set
    var onChange: String? = null
        private set
    var debounce: Long = 300
        private set
    var throttle: Long = 300L
        private set
    var enabled: Boolean = true
        private set

    /**
     * Sets the component value. For a `TextField`, it's a string representing the text. For a
     * `Slider`, the value is a float.
     *
     * @param value component's value.
     */
    open fun value(value: V) = apply {
        this.value = value
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
     * @param attributes list of attributes declared for the component.
     */
    fun processChangeableAttributes(attributes: Array<CoreAttribute>) {
        attributes.forEach { attribute ->
            when (attribute.name) {
                "phx-change" -> onChange(attribute.value)
                "phx-debounce" -> debounce(attribute.value)
                "phx-throttle" -> throttle(attribute.value)
                "enabled" -> enabled(attribute.value)
            }
        }
    }
}