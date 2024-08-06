package org.phoenixframework.liveview.test.ui.view

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.ui.graphics.Color
import org.junit.Test
import org.phoenixframework.liveview.data.constants.Attrs.attrColors
import org.phoenixframework.liveview.data.constants.Attrs.attrEnabled
import org.phoenixframework.liveview.data.constants.Attrs.attrSelected
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledSelectedColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledUnselectedColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrSelectedColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrUnselectedColor
import org.phoenixframework.liveview.data.constants.ComposableTypes.radioButton
import org.phoenixframework.liveview.data.constants.ComposableTypes.row
import org.phoenixframework.liveview.test.base.LiveViewComposableTest

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
                <$row>
                  <$radioButton $attrSelected="true" />
                  <$radioButton $attrSelected="false" />
                  <$radioButton $attrSelected="true" $attrEnabled="false" />
                  <$radioButton $attrSelected="false" $attrEnabled="false" />
                </$row>
                """
        )
    }

    @Test
    fun radioButtonWithCustomColorsTest() {
        val colorsForTemplate = """
            {
            '$colorAttrSelectedColor': '#FFFF0000', 
            '$colorAttrUnselectedColor': '#FF0000FF', 
            '$colorAttrDisabledSelectedColor': '#FF444444', 
            '$colorAttrDisabledUnselectedColor': '#FFCCCCCC'
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
                <$row>
                  <$radioButton $attrSelected="true" $attrColors="$colorsForTemplate"/>
                  <$radioButton $attrSelected="false" $attrColors="$colorsForTemplate"/>
                  <$radioButton $attrSelected="true" $attrEnabled="false" $attrColors="$colorsForTemplate"/>
                  <$radioButton $attrSelected="false" $attrEnabled="false" $attrColors="$colorsForTemplate"/>
                </$row>
                """
        )
    }
}