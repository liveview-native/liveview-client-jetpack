package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.ui.graphics.Color
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test

class CheckboxShotTest : LiveViewComposableTest() {
    @Test
    fun simpleCheckboxTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Row {
                    Checkbox(checked = true, onCheckedChange = {})
                    Checkbox(checked = false, onCheckedChange = {})
                    Checkbox(checked = true, onCheckedChange = {}, enabled = false)
                    Checkbox(checked = false, onCheckedChange = {}, enabled = false)
                }
            },
            template = """
                <Row>
                    <CheckBox checked="true" phx-change="" />
                    <CheckBox checked="false" phx-change="" />
                    <CheckBox checked="true" phx-change="" enabled="false" />
                    <CheckBox checked="false" phx-change="" enabled="false" />
                </Row>
                """
        )
    }

    @Test
    fun checkWithCustomParams() {
        val colors =
            "{'checkedColor': '#FFFF0000', 'uncheckedColor': '#FF00FF00', 'checkmarkColor': '#FFFFFF00', " +
                    "'disabledCheckedColor': '#FF444444', 'disabledUncheckedColor': '#FF888888'}"
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val colorsNative = CheckboxDefaults.colors(
                    checkedColor = Color.Red,
                    uncheckedColor = Color.Green,
                    checkmarkColor = Color.Yellow,
                    disabledCheckedColor = Color.DarkGray,
                    disabledUncheckedColor = Color.Gray
                )
                Row {
                    Checkbox(checked = true, onCheckedChange = {}, colors = colorsNative)
                    Checkbox(checked = false, onCheckedChange = {}, colors = colorsNative)
                    Checkbox(
                        checked = true,
                        onCheckedChange = {},
                        colors = colorsNative,
                        enabled = false
                    )
                    Checkbox(
                        checked = false,
                        onCheckedChange = {},
                        colors = colorsNative,
                        enabled = false
                    )
                }
            },
            template = """
                <Row>
                  <CheckBox checked="true" phx-change="" colors="$colors" />
                  <CheckBox checked="false" phx-change="" colors="$colors" />
                  <CheckBox checked="true" phx-change="" colors="$colors" enabled="false" />
                  <CheckBox checked="false" phx-change="" colors="$colors" enabled="false" />
                </Row>  
                """
        )
    }
}