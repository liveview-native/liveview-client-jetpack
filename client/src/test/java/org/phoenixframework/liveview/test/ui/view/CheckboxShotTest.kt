package org.phoenixframework.liveview.test.ui.view

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.ui.graphics.Color
import org.phoenixframework.liveview.test.base.LiveViewComposableTest
import org.junit.Test
import org.phoenixframework.liveview.data.constants.Attrs.attrChecked
import org.phoenixframework.liveview.data.constants.Attrs.attrColors
import org.phoenixframework.liveview.data.constants.Attrs.attrEnabled
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrCheckedColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrCheckmarkColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledCheckedColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledUncheckedColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrUncheckedColor
import org.phoenixframework.liveview.data.constants.ComposableTypes.checkbox
import org.phoenixframework.liveview.data.constants.ComposableTypes.row

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
                <$row>
                    <$checkbox $attrChecked="true" />
                    <$checkbox $attrChecked="false" />
                    <$checkbox $attrChecked="true" $attrEnabled="false" />
                    <$checkbox $attrChecked="false" $attrEnabled="false" />
                </$row>
                """
        )
    }

    @Test
    fun checkWithCustomParams() {
        val colors = """
            {
            '$colorAttrCheckedColor': '#FFFF0000', 
            '$colorAttrUncheckedColor': '#FF00FF00', 
            '$colorAttrCheckmarkColor': '#FFFFFF00', 
            '$colorAttrDisabledCheckedColor': '#FF444444', 
            '$colorAttrDisabledUncheckedColor': '#FF888888'
            }
            """.toJsonForTemplate()
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
                <$row>
                  <$checkbox $attrChecked="true" $attrColors="$colors" />
                  <$checkbox $attrChecked="false" $attrColors="$colors" />
                  <$checkbox $attrChecked="true" $attrColors="$colors" $attrEnabled="false" />
                  <$checkbox $attrChecked="false" $attrColors="$colors" $attrEnabled="false" />
                </$row>  
                """
        )
    }
}