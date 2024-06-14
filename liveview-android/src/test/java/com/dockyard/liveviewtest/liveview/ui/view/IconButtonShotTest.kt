package com.dockyard.liveviewtest.liveview.ui.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dockyard.liveviewtest.liveview.test.util.LiveViewComposableTest
import org.junit.Test
import org.phoenixframework.liveview.data.constants.Attrs.attrBorder
import org.phoenixframework.liveview.data.constants.Attrs.attrColor
import org.phoenixframework.liveview.data.constants.Attrs.attrColors
import org.phoenixframework.liveview.data.constants.Attrs.attrEnabled
import org.phoenixframework.liveview.data.constants.Attrs.attrImageVector
import org.phoenixframework.liveview.data.constants.Attrs.attrShape
import org.phoenixframework.liveview.data.constants.Attrs.attrWidth
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrContentColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledContentColor
import org.phoenixframework.liveview.data.constants.SystemColorValues.Magenta
import org.phoenixframework.liveview.data.constants.ComposableTypes.filledIconButton
import org.phoenixframework.liveview.data.constants.ComposableTypes.filledTonalIconButton
import org.phoenixframework.liveview.data.constants.ComposableTypes.icon
import org.phoenixframework.liveview.data.constants.ComposableTypes.iconButton
import org.phoenixframework.liveview.data.constants.ComposableTypes.outlinedIconButton
import org.phoenixframework.liveview.data.constants.ComposableTypes.row

class IconButtonShotTest : LiveViewComposableTest() {

    @Test
    fun simpleIconButtonTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Row {
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                    }
                    IconButton(onClick = {}, enabled = false) {
                        Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                    }
                }
            },
            template = """
                <$row>
                  <$iconButton>
                    <$icon $attrImageVector="filled:AccountCircle" />
                  </$iconButton>
                  <$iconButton $attrEnabled="false">
                    <$icon $attrImageVector="filled:AccountCircle" />
                  </$iconButton>                    
                </$row>
                """
        )
    }

    @Test
    fun iconButtonWithCustomColorsTest() {
        val colorsForTemplate = """
            {
            '$colorAttrContainerColor': '#FFFF0000', 
            '$colorAttrContentColor': '#FFFFFF00',
            '$colorAttrDisabledContainerColor': '#FF888888', 
            '$colorAttrDisabledContentColor': '#FFCCCCCC'
            }
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Row {
                    IconButton(
                        onClick = {},
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color.Red,
                            contentColor = Color.Yellow,
                        ),
                    ) {
                        Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                    }
                    IconButton(
                        onClick = {},
                        enabled = false,
                        colors = IconButtonDefaults.iconButtonColors(
                            disabledContainerColor = Color.Gray,
                            disabledContentColor = Color.LightGray,
                        ),
                    ) {
                        Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                    }
                }
            },
            template = """
                <$row>
                  <$iconButton $attrColors="$colorsForTemplate">
                    <$icon $attrImageVector="filled:AccountCircle" />
                  </$iconButton>
                  <$iconButton $attrEnabled="false" $attrColors="$colorsForTemplate">
                    <$icon $attrImageVector="filled:AccountCircle" />
                  </$iconButton>                    
                </$row>
                """
        )
    }

    @Test
    fun simpleFilledIconButtonTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Row {
                    FilledIconButton(onClick = {}) {
                        Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                    }
                    FilledIconButton(onClick = {}, enabled = false) {
                        Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                    }
                }
            },
            template = """
                <$row>
                    <$filledIconButton>
                        <$icon $attrImageVector="filled:AccountCircle" />
                    </$filledIconButton>
                    <$filledIconButton $attrEnabled="false">
                        <$icon $attrImageVector="filled:AccountCircle" />
                    </$filledIconButton>                    
                </$row>
                """
        )
    }

    @Test
    fun filledIconButtonWithCustomColorsTest() {
        val colorsForTemplate = """
            {
            '$colorAttrContainerColor': '#FFFF0000', 
            '$colorAttrContentColor': '#FFFFFF00',
            '$colorAttrDisabledContainerColor': '#FF888888', 
            '$colorAttrDisabledContentColor': '#FFCCCCCC'
            }
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Row {
                    FilledIconButton(
                        onClick = {},
                        shape = RoundedCornerShape(8.dp),
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color.Red,
                            contentColor = Color.Yellow,
                        ),
                    ) {
                        Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                    }
                    FilledIconButton(
                        onClick = {},
                        shape = RoundedCornerShape(8.dp),
                        enabled = false,
                        colors = IconButtonDefaults.iconButtonColors(
                            disabledContainerColor = Color.Gray,
                            disabledContentColor = Color.LightGray,
                        ),
                    ) {
                        Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                    }
                }
            },
            template = """
                <$row>
                  <$filledIconButton
                    $attrShape="8"
                    $attrColors="$colorsForTemplate">
                      <$icon $attrImageVector="filled:AccountCircle" />
                  </$filledIconButton>
                  <$filledIconButton
                    $attrEnabled="false" 
                    $attrShape="8"
                    $attrColors="$colorsForTemplate">
                      <$icon $attrImageVector="filled:AccountCircle" />
                  </$filledIconButton>                    
                </$row>
                """
        )
    }

    @Test
    fun simpleFilledTonalIconButtonTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Row {
                    FilledTonalIconButton(onClick = {}) {
                        Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                    }
                    FilledTonalIconButton(onClick = {}, enabled = false) {
                        Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                    }
                }
            },
            template = """
                <$row>
                  <$filledTonalIconButton>
                    <$icon $attrImageVector="filled:AccountCircle" />
                  </$filledTonalIconButton>
                  <$filledTonalIconButton $attrEnabled="false">
                    <$icon $attrImageVector="filled:AccountCircle" />
                  </$filledTonalIconButton>                    
                </$row>
                """
        )
    }

    @Test
    fun filledTonalIconButtonWithCustomColorsTest() {
        val colorsForTemplate = """
            {
            '$colorAttrContainerColor': '#FFFF0000', 
            '$colorAttrContentColor': '#FFFFFF00',
            '$colorAttrDisabledContainerColor': '#FF888888', 
            '$colorAttrDisabledContentColor': '#FFCCCCCC'
            }
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Row {
                    FilledTonalIconButton(
                        onClick = {},
                        shape = RoundedCornerShape(8.dp),
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color.Red,
                            contentColor = Color.Yellow,
                        ),
                    ) {
                        Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                    }
                    FilledTonalIconButton(
                        onClick = {},
                        shape = RoundedCornerShape(8.dp),
                        enabled = false,
                        colors = IconButtonDefaults.iconButtonColors(
                            disabledContainerColor = Color.Gray,
                            disabledContentColor = Color.LightGray,
                        ),
                    ) {
                        Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                    }
                }
            },
            template = """
                <$row>
                  <$filledTonalIconButton 
                    $attrShape="8"
                    $attrColors="$colorsForTemplate">
                      <$icon $attrImageVector="filled:AccountCircle" />
                  </$filledTonalIconButton>
                  <$filledTonalIconButton 
                    $attrEnabled="false" 
                    $attrShape="8"
                    $attrColors="$colorsForTemplate">
                      <$icon $attrImageVector="filled:AccountCircle" />
                  </$filledTonalIconButton>                    
                </$row>
                """
        )
    }

    @Test
    fun simpleOutlinedIconButtonTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Row {
                    OutlinedIconButton(onClick = {}) {
                        Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                    }
                    OutlinedIconButton(onClick = {}, enabled = false) {
                        Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                    }
                }
            },
            template = """
                <$row>
                  <$outlinedIconButton>
                    <$icon $attrImageVector="filled:AccountCircle" />
                  </$outlinedIconButton>
                  <$outlinedIconButton $attrEnabled="false">
                    <$icon $attrImageVector="filled:AccountCircle" />
                  </$outlinedIconButton>                    
                </$row>
                """
        )
    }

    @Test
    fun outlinedIconButtonWithCustomBorderTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Row {
                    OutlinedIconButton(onClick = {}, border = BorderStroke(2.dp, Color.Magenta)) {
                        Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                    }
                    OutlinedIconButton(
                        onClick = {},
                        enabled = false,
                        border = BorderStroke(2.dp, Color.Magenta)
                    ) {
                        Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                    }
                }
            },
            template = """
                <$row>
                  <$outlinedIconButton 
                    $attrBorder="{'$attrWidth': '2', '$attrColor': '$Magenta'}">
                    <$icon $attrImageVector="filled:AccountCircle" />
                  </$outlinedIconButton>
                  <$outlinedIconButton $attrEnabled="false" 
                    $attrBorder="{'$attrWidth': '2', '$attrColor': '$Magenta'}">
                    <$icon $attrImageVector="filled:AccountCircle" />
                  </$outlinedIconButton>                    
                </$row>
                """
        )
    }

    @Test
    fun outlinedIconButtonWithCustomColorsTest() {
        val colorsForTemplate = """
            {
            '$colorAttrContainerColor': '#FFFF0000', 
            '$colorAttrContentColor': '#FFFFFF00',
            '$colorAttrDisabledContainerColor': '#FF888888', 
            '$colorAttrDisabledContentColor': '#FFCCCCCC'
            }
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Row {
                    OutlinedIconButton(
                        onClick = {},
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(2.dp, Color.Magenta),
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color.Red,
                            contentColor = Color.Yellow,
                        ),
                    ) {
                        Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                    }
                    OutlinedIconButton(
                        onClick = {},
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(2.dp, Color.Magenta),
                        enabled = false,
                        colors = IconButtonDefaults.iconButtonColors(
                            disabledContainerColor = Color.Gray,
                            disabledContentColor = Color.LightGray,
                        ),
                    ) {
                        Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                    }
                }
            },
            template = """
                <$row>
                  <$outlinedIconButton $attrShape="8"
                    $attrBorder="{'$attrWidth': '2', '$attrColor': '$Magenta'}"
                    $attrColors="$colorsForTemplate">
                      <$icon $attrImageVector="filled:AccountCircle" />
                  </$outlinedIconButton>
                  <$outlinedIconButton $attrEnabled="false" $attrShape="8"
                    $attrBorder="{'$attrWidth': '2', '$attrColor': '$Magenta'}"
                    $attrColors="$colorsForTemplate">
                      <$icon $attrImageVector="filled:AccountCircle" />
                  </$outlinedIconButton>                    
                </$row>
                """
        )
    }
}