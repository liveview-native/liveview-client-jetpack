package org.phoenixframework.liveview.domain.extensions

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.core.text.isDigitsOnly
import java.lang.Long


fun String.isNotEmptyAndIsDigitsOnly(): Boolean {
    return this.isNotEmpty() && this.isDigitsOnly()
}

fun String.getMaterialIconByName(): ImageVector? {

    val imageParameters = this.split(":")
    val materialIcon = try {
        val iconClass =
            Class.forName("androidx.compose.material.icons.${imageParameters.first()}.${imageParameters.last()}Kt")

        val method = iconClass.declaredMethods.first()

        method.invoke(
            null,
            when (imageParameters.first()) {
                "filled" -> Icons.Filled
                "rounded" -> Icons.Rounded
                "outlined" -> Icons.Outlined
                "sharp" -> Icons.Sharp
                "twotone" -> Icons.TwoTone
                else -> Icons.Default
            }
        ) as ImageVector
    } catch (e: Throwable) {
        Log.e("NavIcon", e.message ?: "Icon not found")
        null
    }
    return materialIcon
}

fun String.decodeColor(): Color {
    return Color(Long.decode(this))
}