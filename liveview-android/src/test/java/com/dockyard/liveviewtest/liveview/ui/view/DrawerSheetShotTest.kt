package com.dockyard.liveviewtest.liveview.ui.view

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DismissibleDrawerSheet
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dockyard.liveviewtest.liveview.test.util.LiveViewComposableTest
import org.junit.Test
import org.phoenixframework.liveview.data.constants.Attrs.attrDrawerContainerColor
import org.phoenixframework.liveview.data.constants.Attrs.attrDrawerContentColor
import org.phoenixframework.liveview.data.constants.Attrs.attrDrawerShape
import org.phoenixframework.liveview.data.constants.Attrs.attrSelected
import org.phoenixframework.liveview.data.constants.Attrs.attrTemplate
import org.phoenixframework.liveview.data.constants.SystemColorValues.Red
import org.phoenixframework.liveview.data.constants.SystemColorValues.Yellow
import org.phoenixframework.liveview.data.constants.Templates.templateLabel
import org.phoenixframework.liveview.data.constants.ComposableTypes.dismissibleDrawerSheet
import org.phoenixframework.liveview.data.constants.ComposableTypes.modalDrawerSheet
import org.phoenixframework.liveview.data.constants.ComposableTypes.navigationDrawerItem
import org.phoenixframework.liveview.data.constants.ComposableTypes.permanentDrawerSheet
import org.phoenixframework.liveview.data.constants.ComposableTypes.text

class DrawerSheetShotTest : LiveViewComposableTest() {

    @Test
    fun simpleModalDrawerSheetTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                ModalDrawerSheet {
                    NavigationDrawerItem(
                        label = {
                            Text(text = "Option 1")
                        },
                        selected = true, onClick = { },
                    )
                    NavigationDrawerItem(
                        label = {
                            Text(text = "Option 2")
                        },
                        selected = false, onClick = { },
                    )
                    Text(text = "Option 3")
                }
            }, template = """
                <$modalDrawerSheet>
                  <$navigationDrawerItem $attrSelected="true">
                    <$text $attrTemplate="$templateLabel">Option 1</$text>
                  </$navigationDrawerItem>
                  <$navigationDrawerItem $attrSelected="false">
                    <$text $attrTemplate="$templateLabel">Option 2</$text>
                  </$navigationDrawerItem>    
                  <$text>Option 3</$text>                
                </$modalDrawerSheet>
                """
        )
    }

    @Test
    fun modalDrawerSheetWithCustomColorsTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                ModalDrawerSheet(
                    drawerContentColor = Color.Red,
                    drawerContainerColor = Color.Yellow,
                    drawerShape = RoundedCornerShape(32.dp)
                ) {
                    NavigationDrawerItem(
                        label = {
                            Text(text = "Option 1")
                        },
                        selected = true, onClick = { },
                    )
                    NavigationDrawerItem(
                        label = {
                            Text(text = "Option 2")
                        },
                        selected = false, onClick = { },
                    )
                    Text(text = "Option 3")
                }
            },
            template = """
                <$modalDrawerSheet 
                  $attrDrawerContentColor="$Red" 
                  $attrDrawerContainerColor="$Yellow" 
                  $attrDrawerShape="32">
                    <$navigationDrawerItem $attrSelected="true">
                      <$text $attrTemplate="$templateLabel">Option 1</$text>
                    </$navigationDrawerItem>
                    <$navigationDrawerItem $attrSelected="false">
                      <$text $attrTemplate="$templateLabel">Option 2</$text>
                    </$navigationDrawerItem>  
                    <$text>Option 3</$text>                  
                </$modalDrawerSheet>                
                """
        )
    }

    @Test
    fun simpleDismissibleDrawerSheetTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                DismissibleDrawerSheet {
                    NavigationDrawerItem(
                        label = {
                            Text(text = "Option 1")
                        },
                        selected = true, onClick = { },
                    )
                    NavigationDrawerItem(
                        label = {
                            Text(text = "Option 2")
                        },
                        selected = false, onClick = { },
                    )
                    Text(text = "Option 3")
                }
            }, template = """
                <$dismissibleDrawerSheet>
                  <$navigationDrawerItem $attrSelected="true">
                    <$text $attrTemplate="$templateLabel">Option 1</$text>
                  </$navigationDrawerItem>
                  <$navigationDrawerItem $attrSelected="false">
                    <$text $attrTemplate="$templateLabel">Option 2</$text>
                  </$navigationDrawerItem>    
                  <$text>Option 3</$text>                
                </$dismissibleDrawerSheet>
                """
        )
    }

    @Test
    fun dismissibleDrawerSheetWithCustomColorsTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                DismissibleDrawerSheet(
                    drawerContentColor = Color.Red,
                    drawerContainerColor = Color.Yellow,
                    drawerShape = RoundedCornerShape(32.dp)
                ) {
                    NavigationDrawerItem(
                        label = {
                            Text(text = "Option 1")
                        },
                        selected = true, onClick = { },
                    )
                    NavigationDrawerItem(
                        label = {
                            Text(text = "Option 2")
                        },
                        selected = false, onClick = { },
                    )
                    Text(text = "Option 3")
                }
            },
            template = """
                <$dismissibleDrawerSheet 
                  $attrDrawerContentColor="$Red" 
                  $attrDrawerContainerColor="$Yellow" 
                  $attrDrawerShape="32">
                    <$navigationDrawerItem $attrSelected="true">
                        <$text $attrTemplate="$templateLabel">Option 1</$text>
                    </$navigationDrawerItem>
                    <$navigationDrawerItem $attrSelected="false">
                        <$text $attrTemplate="$templateLabel">Option 2</$text>
                    </$navigationDrawerItem>  
                    <$text>Option 3</$text>                  
                </$dismissibleDrawerSheet>                
                """
        )
    }

    @Test
    fun simplePermanentDrawerSheetTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                PermanentDrawerSheet {
                    NavigationDrawerItem(
                        label = {
                            Text(text = "Option 1")
                        },
                        selected = true, onClick = { },
                    )
                    NavigationDrawerItem(
                        label = {
                            Text(text = "Option 2")
                        },
                        selected = false, onClick = { },
                    )
                    Text(text = "Option 3")
                }
            }, template = """
                <$permanentDrawerSheet>
                  <$navigationDrawerItem $attrSelected="true">
                    <$text $attrTemplate="$templateLabel">Option 1</$text>
                  </$navigationDrawerItem>
                  <$navigationDrawerItem $attrSelected="false">
                    <$text $attrTemplate="$templateLabel">Option 2</$text>
                  </$navigationDrawerItem>    
                  <$text>Option 3</$text>                
                </$permanentDrawerSheet>
                """
        )
    }

    @Test
    fun permanentDrawerSheetWithCustomColorsTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                PermanentDrawerSheet(
                    drawerContentColor = Color.Red,
                    drawerContainerColor = Color.Yellow,
                    drawerShape = RoundedCornerShape(32.dp)
                ) {
                    NavigationDrawerItem(
                        label = {
                            Text(text = "Option 1")
                        },
                        selected = true, onClick = { },
                    )
                    NavigationDrawerItem(
                        label = {
                            Text(text = "Option 2")
                        },
                        selected = false, onClick = { },
                    )
                    Text(text = "Option 3")
                }
            },
            template = """
                <$permanentDrawerSheet 
                  $attrDrawerContentColor="$Red" 
                  $attrDrawerContainerColor="$Yellow" 
                  $attrDrawerShape="32">
                    <$navigationDrawerItem $attrSelected="true">
                        <$text $attrTemplate="$templateLabel">Option 1</$text>
                    </$navigationDrawerItem>
                    <$navigationDrawerItem $attrSelected="false">
                        <$text $attrTemplate="$templateLabel">Option 2</$text>
                    </$navigationDrawerItem>  
                    <$text>Option 3</$text>                  
                </$permanentDrawerSheet>                
                """
        )
    }
}