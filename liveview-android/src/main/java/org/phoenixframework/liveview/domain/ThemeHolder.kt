package org.phoenixframework.liveview.domain

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object ThemeHolder {
    private val _themeData = MutableStateFlow<Map<String, Any>>(emptyMap())
    val themeData = _themeData.asStateFlow()

    val isEmpty: Boolean
        get() = _themeData.value.isEmpty()

    fun updateThemeData(themeData: Map<String, Any>?) {
        if (themeData != null) {
            _themeData.value = themeData
        }
    }
}