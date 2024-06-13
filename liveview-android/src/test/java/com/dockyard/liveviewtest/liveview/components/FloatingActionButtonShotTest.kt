package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Cached
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test
import org.phoenixframework.liveview.data.constants.Attrs.attrContainerColor
import org.phoenixframework.liveview.data.constants.Attrs.attrContentColor
import org.phoenixframework.liveview.data.constants.Attrs.attrExpanded
import org.phoenixframework.liveview.data.constants.Attrs.attrImageVector
import org.phoenixframework.liveview.data.constants.Attrs.attrShape
import org.phoenixframework.liveview.data.constants.Attrs.attrTemplate
import org.phoenixframework.liveview.data.constants.ShapeValues.circle
import org.phoenixframework.liveview.data.constants.Templates.templateIcon
import org.phoenixframework.liveview.data.constants.Templates.templateText
import org.phoenixframework.liveview.data.constants.ComposableTypes.extendedFloatingActionButton
import org.phoenixframework.liveview.data.constants.ComposableTypes.floatingActionButton
import org.phoenixframework.liveview.data.constants.ComposableTypes.icon
import org.phoenixframework.liveview.data.constants.ComposableTypes.largeFloatingActionButton
import org.phoenixframework.liveview.data.constants.ComposableTypes.row
import org.phoenixframework.liveview.data.constants.ComposableTypes.smallFloatingActionButton
import org.phoenixframework.liveview.data.constants.ComposableTypes.text
import org.phoenixframework.liveview.domain.extensions.toColor

class FloatingActionButtonShotTest : LiveViewComposableTest() {

    @Test
    fun simpleFabTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Row {
                    FloatingActionButton(onClick = {}) {
                        Icon(imageVector = Icons.Filled.Add, contentDescription = "")
                    }
                    SmallFloatingActionButton(onClick = {}) {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = "")
                    }
                    LargeFloatingActionButton(onClick = {}) {
                        Icon(imageVector = Icons.Filled.Cached, contentDescription = "")
                    }
                    ExtendedFloatingActionButton(
                        text = {
                            Text(text = "Share")
                        },
                        icon = {
                            Icon(imageVector = Icons.Filled.Share, contentDescription = "")
                        },
                        onClick = {},
                    )
                    ExtendedFloatingActionButton(
                        text = {
                            Text(text = "Share")
                        },
                        icon = {
                            Icon(imageVector = Icons.Filled.Share, contentDescription = "")
                        },
                        onClick = {},
                        expanded = false
                    )
                }
            },
            template = """
                <$row>
                  <$floatingActionButton>
                    <$icon $attrImageVector="filled:Add"/>
                  </$floatingActionButton>
                  <$smallFloatingActionButton>
                    <$icon $attrImageVector="filled:Delete"/>
                  </$smallFloatingActionButton>
                  <$largeFloatingActionButton>
                    <$icon $attrImageVector="filled:Cached"/>
                  </$largeFloatingActionButton>
                  <$extendedFloatingActionButton>
                    <$icon $attrImageVector="filled:Share" $attrTemplate="$templateIcon"/>
                    <$text $attrTemplate="$templateText">Share</$text>
                  </$extendedFloatingActionButton>
                  <$extendedFloatingActionButton $attrExpanded="false">
                    <$icon $attrImageVector="filled:Share" $attrTemplate="$templateIcon"/>
                    <$text $attrTemplate="$templateText">Share</$text>
                  </$extendedFloatingActionButton>                  
                </$row>
                """
        )
    }

    @Test
    fun fabWithCustomColorsTest() {
        val containerColor = "FFFF0000"
        val contentColor = "FFFFFFFF"
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Row {
                    FloatingActionButton(
                        onClick = {},
                        containerColor = containerColor.toColor(),
                        contentColor = contentColor.toColor(),
                    ) {
                        Icon(imageVector = Icons.Filled.Add, contentDescription = "")
                    }
                    SmallFloatingActionButton(
                        onClick = {},
                        containerColor = containerColor.toColor(),
                        contentColor = contentColor.toColor(),
                    ) {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = "")
                    }
                    LargeFloatingActionButton(
                        onClick = {},
                        containerColor = containerColor.toColor(),
                        contentColor = contentColor.toColor(),
                    ) {
                        Icon(imageVector = Icons.Filled.Cached, contentDescription = "")
                    }
                    ExtendedFloatingActionButton(
                        text = {
                            Text(text = "Share")
                        },
                        icon = {
                            Icon(imageVector = Icons.Filled.Share, contentDescription = "")
                        },
                        onClick = {},
                        containerColor = containerColor.toColor(),
                        contentColor = contentColor.toColor(),
                    )
                    ExtendedFloatingActionButton(
                        text = {
                            Text(text = "Share")
                        },
                        icon = {
                            Icon(imageVector = Icons.Filled.Share, contentDescription = "")
                        },
                        onClick = {},
                        expanded = false,
                        containerColor = containerColor.toColor(),
                        contentColor = contentColor.toColor(),
                    )
                }
            },
            template = """
                <$row>
                  <$floatingActionButton 
                    $attrContainerColor="#$containerColor" $attrContentColor="#$contentColor">
                    <$icon $attrImageVector="filled:Add"/>
                  </$floatingActionButton>
                  <$smallFloatingActionButton 
                    $attrContainerColor="#$containerColor" $attrContentColor="#$contentColor">
                    <$icon $attrImageVector="filled:Delete"/>
                  </$smallFloatingActionButton>
                  <$largeFloatingActionButton 
                    $attrContainerColor="#$containerColor" $attrContentColor="#$contentColor">
                    <$icon $attrImageVector="filled:Cached"/>
                  </$largeFloatingActionButton>
                  <$extendedFloatingActionButton 
                    $attrContainerColor="#$containerColor" $attrContentColor="#$contentColor">
                    <$icon $attrImageVector="filled:Share" $attrTemplate="$templateIcon"/>
                    <$text $attrTemplate="$templateText">Share</$text>
                  </$extendedFloatingActionButton>
                  <$extendedFloatingActionButton $attrExpanded="false" 
                    $attrContainerColor="#$containerColor" $attrContentColor="#$contentColor">
                    <$icon $attrImageVector="filled:Share" $attrTemplate="$templateIcon"/>
                    <$text $attrTemplate="$templateText">Share</$text>
                  </$extendedFloatingActionButton>                  
                </$row>
                """
        )
    }

    @Test
    fun fabWithCustomShapesTest() {
        val containerColor = "FF0000FF"
        val contentColor = "FFFFFF00"
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Row {
                    FloatingActionButton(
                        onClick = {},
                        containerColor = containerColor.toColor(),
                        contentColor = contentColor.toColor(),
                        shape = CircleShape,
                    ) {
                        Icon(imageVector = Icons.Filled.Add, contentDescription = "")
                    }
                    SmallFloatingActionButton(
                        onClick = {},
                        containerColor = containerColor.toColor(),
                        contentColor = contentColor.toColor(),
                        shape = CircleShape,
                    ) {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = "")
                    }
                    LargeFloatingActionButton(
                        onClick = {},
                        containerColor = containerColor.toColor(),
                        contentColor = contentColor.toColor(),
                        shape = CircleShape,
                    ) {
                        Icon(imageVector = Icons.Filled.Cached, contentDescription = "")
                    }
                    ExtendedFloatingActionButton(
                        text = {
                            Text(text = "Share")
                        },
                        icon = {
                            Icon(imageVector = Icons.Filled.Share, contentDescription = "")
                        },
                        onClick = {},
                        containerColor = containerColor.toColor(),
                        contentColor = contentColor.toColor(),
                        shape = CircleShape,
                    )
                    ExtendedFloatingActionButton(
                        text = {
                            Text(text = "Share")
                        },
                        icon = {
                            Icon(imageVector = Icons.Filled.Share, contentDescription = "")
                        },
                        onClick = {},
                        expanded = false,
                        containerColor = containerColor.toColor(),
                        contentColor = contentColor.toColor(),
                        shape = CircleShape,
                    )
                }
            },
            template = """
                <$row>
                  <$floatingActionButton 
                    $attrContainerColor="#$containerColor" $attrContentColor="#$contentColor" 
                    $attrShape="$circle">
                    <$icon $attrImageVector="filled:Add"/>
                  </$floatingActionButton>
                  <$smallFloatingActionButton 
                    $attrContainerColor="#$containerColor" $attrContentColor="#$contentColor" 
                    $attrShape="$circle">
                    <$icon $attrImageVector="filled:Delete"/>
                  </$smallFloatingActionButton>
                  <$largeFloatingActionButton 
                    $attrContainerColor="#$containerColor" $attrContentColor="#$contentColor" 
                    $attrShape="$circle">
                    <$icon $attrImageVector="filled:Cached"/>
                  </$largeFloatingActionButton>
                  <$extendedFloatingActionButton 
                    $attrContainerColor="#$containerColor" $attrContentColor="#$contentColor" 
                    $attrShape="$circle">
                    <$icon $attrImageVector="filled:Share" $attrTemplate="$templateIcon"/>
                    <$text $attrTemplate="$templateText">Share</$text>
                  </$extendedFloatingActionButton>
                  <$extendedFloatingActionButton $attrExpanded="false" 
                    $attrContainerColor="#$containerColor" $attrContentColor="#$contentColor" 
                    $attrShape="$circle">
                    <$icon $attrImageVector="filled:Share" $attrTemplate="$templateIcon"/>
                    <$text $attrTemplate="$templateText">Share</$text>
                  </$extendedFloatingActionButton>                  
                </$row>
                """
        )
    }
}