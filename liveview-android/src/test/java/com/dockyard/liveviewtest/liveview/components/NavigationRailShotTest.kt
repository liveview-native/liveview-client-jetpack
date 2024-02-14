package com.dockyard.liveviewtest.liveview.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test
import org.phoenixframework.liveview.data.constants.Attrs.attrContainerColor
import org.phoenixframework.liveview.data.constants.Attrs.attrContentColor
import org.phoenixframework.liveview.data.constants.Attrs.attrFontSize
import org.phoenixframework.liveview.data.constants.Attrs.attrImageVector
import org.phoenixframework.liveview.data.constants.Attrs.attrSelected
import org.phoenixframework.liveview.data.constants.Attrs.attrTemplate
import org.phoenixframework.liveview.data.constants.SystemColorValues.Blue
import org.phoenixframework.liveview.data.constants.SystemColorValues.Yellow
import org.phoenixframework.liveview.data.constants.Templates.templateHeader
import org.phoenixframework.liveview.data.constants.Templates.templateIcon
import org.phoenixframework.liveview.domain.base.ComposableTypes.icon
import org.phoenixframework.liveview.domain.base.ComposableTypes.navigationRail
import org.phoenixframework.liveview.domain.base.ComposableTypes.navigationRailItem
import org.phoenixframework.liveview.domain.base.ComposableTypes.text
import org.robolectric.annotation.Config

@Config(qualifiers = "xlarge-land")
class NavigationRailShotTest : LiveViewComposableTest() {

    @Test
    fun simpleNavigationRailTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                NavigationRail {
                    NavigationRailItem(
                        selected = true,
                        onClick = {},
                        icon = {
                            Icon(imageVector = Icons.Filled.Favorite, contentDescription = "")
                        },
                    )
                    NavigationRailItem(
                        selected = false,
                        onClick = {},
                        icon = {
                            Icon(imageVector = Icons.Filled.Home, contentDescription = "")
                        },
                    )
                    NavigationRailItem(
                        selected = false,
                        onClick = {},
                        icon = {
                            Icon(imageVector = Icons.Filled.Person, contentDescription = "")
                        },
                    )
                }
            },
            template = """
                <$navigationRail>
                  <$navigationRailItem $attrSelected="true">
                    <$icon $attrImageVector="filled:Favorite" $attrTemplate="$templateIcon"/>
                  </$navigationRailItem>
                  <$navigationRailItem $attrSelected="false">
                    <$icon $attrImageVector="filled:Home" $attrTemplate="$templateIcon"/>
                  </$navigationRailItem>
                  <$navigationRailItem $attrSelected="false">
                    <$icon $attrImageVector="filled:Person" $attrTemplate="$templateIcon"/>
                  </$navigationRailItem>                                   
                </$navigationRail>
                """
        )
    }

    @Test
    fun navigationRailWithHeaderTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                NavigationRail(
                    header = {
                        Text(text = "NavRail Header", fontSize = 24.sp)
                        Text(text = "Subheader", fontSize = 18.sp)
                    }
                ) {
                    NavigationRailItem(
                        selected = true,
                        onClick = {},
                        icon = {
                            Icon(imageVector = Icons.Filled.Favorite, contentDescription = "")
                        },
                    )
                    NavigationRailItem(
                        selected = false,
                        onClick = {},
                        icon = {
                            Icon(imageVector = Icons.Filled.Home, contentDescription = "")
                        },
                    )
                    NavigationRailItem(
                        selected = false,
                        onClick = {},
                        icon = {
                            Icon(imageVector = Icons.Filled.Person, contentDescription = "")
                        },
                    )
                }
            },
            template = """
                <$navigationRail>
                  <$text $attrFontSize="24" $attrTemplate="$templateHeader">NavRail Header</$text>
                  <$text $attrFontSize="18" $attrTemplate="$templateHeader">Subheader</$text>
                  <$navigationRailItem $attrSelected="true">
                    <$icon $attrImageVector="filled:Favorite" $attrTemplate="$templateIcon"/>
                  </$navigationRailItem>
                  <$navigationRailItem $attrSelected="false">
                    <$icon $attrImageVector="filled:Home" $attrTemplate="$templateIcon"/>
                  </$navigationRailItem>
                  <$navigationRailItem $attrSelected="false">
                    <$icon $attrImageVector="filled:Person" $attrTemplate="$templateIcon"/>
                  </$navigationRailItem>                                   
                </$navigationRail>
                """
        )
    }

    @Test
    fun navigationRailWithCustomColorsTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                NavigationRail(
                    header = {
                        Text(text = "NavRail Header", fontSize = 24.sp)
                        Text(text = "Subheader", fontSize = 18.sp)
                    },
                    containerColor = Color.Blue,
                    contentColor = Color.Yellow,
                ) {
                    NavigationRailItem(
                        selected = true,
                        onClick = {},
                        icon = {
                            Icon(imageVector = Icons.Filled.Favorite, contentDescription = "")
                        },
                    )
                    NavigationRailItem(
                        selected = false,
                        onClick = {},
                        icon = {
                            Icon(imageVector = Icons.Filled.Home, contentDescription = "")
                        },
                    )
                    NavigationRailItem(
                        selected = false,
                        onClick = {},
                        icon = {
                            Icon(imageVector = Icons.Filled.Person, contentDescription = "")
                        },
                    )
                }
            },
            template = """
                <$navigationRail $attrContainerColor="$Blue" $attrContentColor="$Yellow">
                  <$text $attrFontSize="24" $attrTemplate="$templateHeader">NavRail Header</$text>
                  <$text $attrFontSize="18" $attrTemplate="$templateHeader">Subheader</$text>
                  <$navigationRailItem $attrSelected="true">
                    <$icon $attrImageVector="filled:Favorite" $attrTemplate="$templateIcon"/>
                  </$navigationRailItem>
                  <$navigationRailItem $attrSelected="false">
                    <$icon $attrImageVector="filled:Home" $attrTemplate="$templateIcon"/>
                  </$navigationRailItem>
                  <$navigationRailItem $attrSelected="false">
                    <$icon $attrImageVector="filled:Person" $attrTemplate="$templateIcon"/>
                  </$navigationRailItem>                                   
                </$navigationRail>
                """
        )
    }
}