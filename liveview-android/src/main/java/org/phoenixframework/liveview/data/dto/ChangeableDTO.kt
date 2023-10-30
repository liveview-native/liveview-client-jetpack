package org.phoenixframework.liveview.data.dto

import org.phoenixframework.liveview.data.core.CoreAttribute
import org.phoenixframework.liveview.domain.base.ComposableBuilder

abstract class ChangeableDTOBuilder<T>(defaultValue: T) : ComposableBuilder() {
    var value = defaultValue
        private set
    var onChange: String? = null
        private set
    var debounce: Long? = null
        private set
    var throttle: Long = 300L
        private set
    var enabled: Boolean = true
        private set

    open fun value(value: T) = apply {
        this.value = value
    }

    fun onChange(event: String) = apply {
        this.onChange = event
    }

    fun debounce(debounce: String) = apply {
        this.debounce = debounce.toLongOrNull()
    }

    fun throttle(throttle: String) = apply {
        this.throttle = throttle.toLongOrNull() ?: 300
    }

    fun enabled(enabled: String) = apply {
        this.enabled = enabled.toBoolean()
    }

    fun processChangeableAttributes(
        attributes: Array<CoreAttribute>,
    ) {

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