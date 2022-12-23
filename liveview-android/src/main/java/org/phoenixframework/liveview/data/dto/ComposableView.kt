package org.phoenixframework.liveview.data.dto

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.phoenixframework.liveview.extensions.isNotEmptyAndIsDigitsOnly

abstract class ComposableView{


}

abstract class ComposableBuilder(){
   private var modifier: Modifier = Modifier
    fun setSize(size: String) {
        when {
            size.isNotEmptyAndIsDigitsOnly() -> {
                modifier.size(size = size.toInt().dp)
            }
            size == "fill" -> {
                modifier.fillMaxSize()
            }
        }
    }

    fun setHeight(height: String) {
        when {
            height.isNotEmptyAndIsDigitsOnly() -> {
                modifier.height(height.toInt().dp)
            }
            height == "fill" -> {
                modifier.fillMaxHeight()
            }
        }
    }

    fun setWidth(width: String) {
        when {
            width.isNotEmptyAndIsDigitsOnly() -> {
                modifier.width(width.toInt().dp)
            }
            width == "fill" -> {
                modifier.fillMaxWidth()
            }
        }
    }

    fun getModifier() = modifier
}