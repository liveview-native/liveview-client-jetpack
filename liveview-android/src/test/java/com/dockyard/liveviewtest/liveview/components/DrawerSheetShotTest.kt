package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DismissibleDrawerSheet
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test

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
                <ModalDrawerSheet>
                    <NavigationDrawerItem selected="true">
                        <Text template="label">Option 1</Text>
                    </NavigationDrawerItem>
                    <NavigationDrawerItem selected="false">
                        <Text template="label">Option 2</Text>
                    </NavigationDrawerItem>    
                    <Text>Option 3</Text>                
                </ModalDrawerSheet>
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
                <ModalDrawerSheet drawer-content-color="system-red" drawer-container-color="system-yellow" drawer-shape="32">
                    <NavigationDrawerItem selected="true">
                        <Text template="label">Option 1</Text>
                    </NavigationDrawerItem>
                    <NavigationDrawerItem selected="false">
                        <Text template="label">Option 2</Text>
                    </NavigationDrawerItem>  
                    <Text>Option 3</Text>                  
                </ModalDrawerSheet>                
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
                <DismissibleDrawerSheet>
                    <NavigationDrawerItem selected="true">
                        <Text template="label">Option 1</Text>
                    </NavigationDrawerItem>
                    <NavigationDrawerItem selected="false">
                        <Text template="label">Option 2</Text>
                    </NavigationDrawerItem>    
                    <Text>Option 3</Text>                
                </DismissibleDrawerSheet>
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
                <DismissibleDrawerSheet drawer-content-color="system-red" drawer-container-color="system-yellow" drawer-shape="32">
                    <NavigationDrawerItem selected="true">
                        <Text template="label">Option 1</Text>
                    </NavigationDrawerItem>
                    <NavigationDrawerItem selected="false">
                        <Text template="label">Option 2</Text>
                    </NavigationDrawerItem>  
                    <Text>Option 3</Text>                  
                </DismissibleDrawerSheet>                
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
                <PermanentDrawerSheet>
                    <NavigationDrawerItem selected="true">
                        <Text template="label">Option 1</Text>
                    </NavigationDrawerItem>
                    <NavigationDrawerItem selected="false">
                        <Text template="label">Option 2</Text>
                    </NavigationDrawerItem>    
                    <Text>Option 3</Text>                
                </PermanentDrawerSheet>
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
                <PermanentDrawerSheet drawer-content-color="system-red" drawer-container-color="system-yellow" drawer-shape="32">
                    <NavigationDrawerItem selected="true">
                        <Text template="label">Option 1</Text>
                    </NavigationDrawerItem>
                    <NavigationDrawerItem selected="false">
                        <Text template="label">Option 2</Text>
                    </NavigationDrawerItem>  
                    <Text>Option 3</Text>                  
                </PermanentDrawerSheet>                
                """
        )
    }
}