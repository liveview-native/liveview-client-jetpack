package org.phoenixframework.liveview.foundation.data.core

import androidx.compose.runtime.Immutable
import org.phoenixframework.liveviewnative.core.Attribute

/**
 * This class is a copy of the `Attribute` class existing in Core-Jetpack.
 * It stores data from `Attribute` class in order to avoid to deal native objects.
 */
@Immutable
data class CoreAttribute(
    val name: String,
    val namespace: String,
    val value: String,
) {
    companion object {
        /**
         * Create a new instance of `CoreAttribute` from an `Attribute` object.
         */
        internal fun fromAttribute(attribute: Attribute): CoreAttribute {
            return CoreAttribute(
                attribute.name.name,
                attribute.name.namespace.toString(),
                attribute.value.toString()
            )
        }
    }
}
