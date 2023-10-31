package org.phoenixframework.liveview.domain

import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object ThemeHolder {
    private val _themeData = MutableStateFlow<ImmutableMap<String, Any>>(persistentMapOf())
    val themeData = _themeData.asStateFlow()

    val isEmpty: Boolean
        get() = _themeData.value.isEmpty()

    fun updateThemeData(themeData: Map<String, Any>?) {
        if (themeData != null) {
            _themeData.value = themeData.toImmutableMap()
        }
    }
}