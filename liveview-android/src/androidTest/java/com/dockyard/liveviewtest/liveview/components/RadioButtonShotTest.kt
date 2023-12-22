package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.ui.graphics.Color
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test

class RadioButtonShotTest : LiveViewComposableTest() {
    @Test
    fun simpleRadioButtonTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Row {
                    RadioButton(selected = true, onClick = {})
                    RadioButton(selected = false, onClick = {})
                    RadioButton(selected = true, enabled = false, onClick = {})
                    RadioButton(selected = false, enabled = false, onClick = {})

                }
            }, template = """
                <Row>
                  <RadioButton selected="true" />
                  <RadioButton selected="false" />
                  <RadioButton selected="true" enabled="false" />
                  <RadioButton selected="false" enabled="false" />
                </Row>
                """
        )
    }

    @Test
    fun radioButtonWithCustomColorsTest() {
        val colorsForTemplate = """
            {
            'selectedColor': '#FFFF0000', 
            'unselectedColor': '#FF0000FF', 
            'disabledSelectedColor': '#FF444444', 
            'disabledUnselectedColor': '#FFCCCCCC'
            }
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val colors = RadioButtonDefaults.colors(
                    selectedColor = Color.Red,
                    unselectedColor = Color.Blue,
                    disabledSelectedColor = Color.DarkGray,
                    disabledUnselectedColor = Color.LightGray
                )
                Row {
                    RadioButton(selected = true, onClick = {}, colors = colors)
                    RadioButton(selected = false, onClick = {}, colors = colors)
                    RadioButton(selected = true, enabled = false, onClick = {}, colors = colors)
                    RadioButton(selected = false, enabled = false, onClick = {}, colors = colors)
                }
            }, template = """
                <Row>
                  <RadioButton selected="true" colors="$colorsForTemplate"/>
                  <RadioButton selected="false" colors="$colorsForTemplate"/>
                  <RadioButton selected="true" enabled="false" colors="$colorsForTemplate"/>
                  <RadioButton selected="false" enabled="false" colors="$colorsForTemplate"/>
                </Row>
                """
        )
    }
}