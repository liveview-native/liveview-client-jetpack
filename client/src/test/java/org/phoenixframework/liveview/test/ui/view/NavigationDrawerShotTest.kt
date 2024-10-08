package org.phoenixframework.liveview.test.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DismissibleNavigationDrawer
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.junit.Test
import org.phoenixframework.liveview.constants.AlignmentValues.center
import org.phoenixframework.liveview.constants.Attrs.attrContentAlignment
import org.phoenixframework.liveview.constants.Attrs.attrIsOpen
import org.phoenixframework.liveview.constants.Attrs.attrPhxChange
import org.phoenixframework.liveview.constants.Attrs.attrScrimColor
import org.phoenixframework.liveview.constants.Attrs.attrSelected
import org.phoenixframework.liveview.constants.Attrs.attrStyle
import org.phoenixframework.liveview.constants.Attrs.attrTemplate
import org.phoenixframework.liveview.constants.ComposableTypes.box
import org.phoenixframework.liveview.constants.ComposableTypes.dismissibleNavigationDrawer
import org.phoenixframework.liveview.constants.ComposableTypes.modalDrawerSheet
import org.phoenixframework.liveview.constants.ComposableTypes.modalNavigationDrawer
import org.phoenixframework.liveview.constants.ComposableTypes.navigationDrawerItem
import org.phoenixframework.liveview.constants.ComposableTypes.permanentNavigationDrawer
import org.phoenixframework.liveview.constants.ComposableTypes.text
import org.phoenixframework.liveview.constants.ModifierNames.modifierFillMaxSize
import org.phoenixframework.liveview.constants.SystemColorValues.Red
import org.phoenixframework.liveview.constants.Templates.templateDrawerContent
import org.phoenixframework.liveview.constants.Templates.templateLabel
import org.phoenixframework.liveview.test.base.LiveViewComposableTest

class NavigationDrawerShotTest : LiveViewComposableTest() {
    @Test
    fun simpleModalNavigationDrawerTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                ModalNavigationDrawer(
                    drawerState = rememberDrawerState(initialValue = DrawerValue.Open),
                    drawerContent = {
                        ModalDrawerSheet {
                            NavigationDrawerItem(
                                label = {
                                    Text(text = "Option 1")
                                },
                                selected = true,
                                onClick = { },
                            )
                            NavigationDrawerItem(
                                label = {
                                    Text(text = "Option 2")
                                },
                                selected = false,
                                onClick = { },
                            )
                        }
                    },
                    content = {
                        Box(contentAlignment = Alignment.Center) {
                            Text(text = "Screen Content")
                        }
                    },
                )
            },
            template = """
                <$modalNavigationDrawer $attrIsOpen="true" $attrPhxChange="">
                  <$modalDrawerSheet $attrTemplate="$templateDrawerContent">
                    <$navigationDrawerItem $attrSelected="true">
                      <$text $attrTemplate="$templateLabel">Option 1</$text>
                    </$navigationDrawerItem>
                    <$navigationDrawerItem $attrSelected="false">
                      <$text $attrTemplate="$templateLabel">Option 2</$text>
                    </$navigationDrawerItem>
                  </$modalDrawerSheet>                      
                  <$box $attrContentAlignment="$center">
                    <$text>Screen Content</$text>
                  </$box>
                </$modalNavigationDrawer>
                """
        )
    }

    @Test
    fun modalNavigationDrawerWithScrimColorTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                ModalNavigationDrawer(
                    scrimColor = Color.Red,
                    drawerState = rememberDrawerState(initialValue = DrawerValue.Open),
                    drawerContent = {
                        ModalDrawerSheet {
                            NavigationDrawerItem(
                                label = {
                                    Text(text = "Option 1")
                                },
                                selected = true,
                                onClick = { },
                            )
                            NavigationDrawerItem(
                                label = {
                                    Text(text = "Option 2")
                                },
                                selected = false,
                                onClick = { },
                            )
                        }
                    },
                    content = {
                        Box(contentAlignment = Alignment.Center) {
                            Text(text = "Screen Content")
                        }
                    },
                )
            },
            template = """
                <$modalNavigationDrawer 
                  $attrScrimColor="$Red" 
                  $attrIsOpen="true" 
                  $attrPhxChange="">
                  <$modalDrawerSheet $attrTemplate="$templateDrawerContent">
                    <$navigationDrawerItem $attrSelected="true">
                      <$text $attrTemplate="$templateLabel">Option 1</$text>
                    </$navigationDrawerItem>
                    <$navigationDrawerItem $attrSelected="false">
                      <$text $attrTemplate="$templateLabel">Option 2</$text>
                    </$navigationDrawerItem>
                  </$modalDrawerSheet>                      
                  <$box $attrContentAlignment="$center">
                    <$text>Screen Content</$text>
                  </$box>
                </$modalNavigationDrawer>
                """
        )
    }

    @Test
    fun simpleDismissibleNavigationDrawerTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                DismissibleNavigationDrawer(
                    modifier = Modifier.fillMaxSize(),
                    drawerState = rememberDrawerState(initialValue = DrawerValue.Open),
                    drawerContent = {
                        ModalDrawerSheet {
                            NavigationDrawerItem(
                                label = {
                                    Text(text = "Option 1")
                                },
                                selected = true,
                                onClick = { },
                            )
                            NavigationDrawerItem(
                                label = {
                                    Text(text = "Option 2")
                                },
                                selected = false,
                                onClick = { },
                            )
                        }
                    },
                    content = {
                        Box(contentAlignment = Alignment.Center) {
                            Text(text = "Screen Content")
                        }
                    },
                )
            },
            template = """
                <$dismissibleNavigationDrawer $attrIsOpen="true" $attrPhxChange="" $attrStyle="$modifierFillMaxSize()">
                  <$modalDrawerSheet $attrTemplate="$templateDrawerContent">
                    <$navigationDrawerItem $attrSelected="true">
                      <$text $attrTemplate="$templateLabel">Option 1</$text>
                    </$navigationDrawerItem>
                    <$navigationDrawerItem $attrSelected="false">
                      <$text $attrTemplate="$templateLabel">Option 2</$text>
                    </$navigationDrawerItem>
                  </$modalDrawerSheet>                      
                  <$box $attrContentAlignment="$center">
                    <$text>Screen Content</$text>
                  </$box>
                </$dismissibleNavigationDrawer>
                """
        )
    }

    @Test
    fun permanentNavigationDrawerWithScrimColorTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                PermanentNavigationDrawer(
                    drawerContent = {
                        ModalDrawerSheet {
                            NavigationDrawerItem(
                                label = {
                                    Text(text = "Option 1")
                                },
                                selected = true,
                                onClick = { },
                            )
                            NavigationDrawerItem(
                                label = {
                                    Text(text = "Option 2")
                                },
                                selected = false,
                                onClick = { },
                            )
                        }
                    },
                    content = {
                        Box(contentAlignment = Alignment.Center) {
                            Text(text = "Screen Content")
                        }
                    },
                )
            },
            template = """
                <$permanentNavigationDrawer $attrIsOpen="true" $attrPhxChange="">
                  <$modalDrawerSheet $attrTemplate="$templateDrawerContent">
                    <$navigationDrawerItem $attrSelected="true">
                      <$text $attrTemplate="$templateLabel">Option 1</$text>
                    </$navigationDrawerItem>
                    <$navigationDrawerItem $attrSelected="false">
                      <$text $attrTemplate="$templateLabel">Option 2</$text>
                    </$navigationDrawerItem>
                  </$modalDrawerSheet>                      
                  <$box $attrContentAlignment="$center">
                    <$text>Screen Content</$text>
                  </$box>
                </$permanentNavigationDrawer>
                """
        )
    }
}