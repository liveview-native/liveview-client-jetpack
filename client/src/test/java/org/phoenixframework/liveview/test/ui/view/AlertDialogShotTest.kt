package org.phoenixframework.liveview.test.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import org.phoenixframework.liveview.test.base.LiveViewComposableTest
import org.junit.Test
import org.phoenixframework.liveview.data.constants.Attrs.attrContainerColor
import org.phoenixframework.liveview.data.constants.Attrs.attrIconContentColor
import org.phoenixframework.liveview.data.constants.Attrs.attrImageVector
import org.phoenixframework.liveview.data.constants.Attrs.attrShape
import org.phoenixframework.liveview.data.constants.Attrs.attrStyle
import org.phoenixframework.liveview.data.constants.Attrs.attrTemplate
import org.phoenixframework.liveview.data.constants.Attrs.attrTextContentColor
import org.phoenixframework.liveview.data.constants.Attrs.attrTitleContentColor
import org.phoenixframework.liveview.data.constants.ComposableTypes.alertDialog
import org.phoenixframework.liveview.data.constants.ComposableTypes.basicAlertDialog
import org.phoenixframework.liveview.data.constants.ComposableTypes.button
import org.phoenixframework.liveview.data.constants.ComposableTypes.column
import org.phoenixframework.liveview.data.constants.ComposableTypes.icon
import org.phoenixframework.liveview.data.constants.ComposableTypes.row
import org.phoenixframework.liveview.data.constants.ComposableTypes.text
import org.phoenixframework.liveview.data.constants.ComposableTypes.textButton
import org.phoenixframework.liveview.data.constants.IconPrefixValues.filled
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierTestTag
import org.phoenixframework.liveview.data.constants.Templates.templateConfirmButton
import org.phoenixframework.liveview.data.constants.Templates.templateDismissButton
import org.phoenixframework.liveview.data.constants.Templates.templateIcon
import org.phoenixframework.liveview.data.constants.Templates.templateTitle

@OptIn(ExperimentalMaterial3Api::class)
class AlertDialogShotTest : LiveViewComposableTest() {
    @Test
    fun simpleDialogTest() {
        val testTag = "myAlert"
        compareNativeComposableWithTemplate(
            testTag = testTag,
            nativeComposable = {
                AlertDialog(
                    modifier = Modifier.testTag(testTag),
                    onDismissRequest = {},
                    confirmButton = {
                        Button(onClick = {}) {
                            Text(text = "Confirm")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {}) {
                            Text(text = "Dismiss")
                        }
                    },
                    icon = {
                        Icon(imageVector = Icons.Filled.Add, contentDescription = "")
                    },
                    title = {
                        Text(text = "Alert Title")
                    },
                    text = {
                        Text(text = "Alert message")
                    }
                )
            },
            template = """
                <$alertDialog $attrStyle="$modifierTestTag('$testTag')">
                  <$button 
                    $attrTemplate="$templateConfirmButton">
                    <$text>Confirm</$text>
                  </$button>
                  <$textButton 
                    $attrTemplate="$templateDismissButton">
                    <$text>Dismiss</$text>
                  </$textButton>
                  <$icon 
                    $attrImageVector="$filled:Add" 
                    $attrTemplate="$templateIcon" />
                  <$text $attrTemplate="$templateTitle">Alert Title</$text>
                  <$text>Alert message</$text>
                </$alertDialog>    
                """
        )
    }

    @Test
    fun alertDialogWithCustomParamsTest() {
        val testTag = "customAlert"
        compareNativeComposableWithTemplate(
            testTag = testTag,
            nativeComposable = {
                AlertDialog(
                    modifier = Modifier.testTag(testTag),
                    onDismissRequest = {},
                    confirmButton = {
                        Button(onClick = {}) {
                            Text(text = "Confirm")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {}) {
                            Text(text = "Dismiss")
                        }
                    },
                    icon = {
                        Icon(imageVector = Icons.Filled.Add, contentDescription = "")
                    },
                    title = {
                        Text(text = "Alert Title")
                    },
                    text = {
                        Text(text = "Alert message")
                    },
                    shape = RoundedCornerShape(4.dp),
                    containerColor = Color(0xFFF2DDE1),
                    iconContentColor = Color.Red,
                    titleContentColor = Color.Blue,
                    textContentColor = Color.Green,
                )
            },
            template = """
                <$alertDialog 
                  $attrStyle="$modifierTestTag('$testTag')" 
                  $attrShape="4" 
                  $attrContainerColor="#FFF2DDE1"
                  $attrIconContentColor="#FFFF0000" 
                  $attrTitleContentColor="#FF0000FF" 
                  $attrTextContentColor="#FF00FF00" >
                  <$button 
                    $attrTemplate="$templateConfirmButton">
                    <$text>Confirm</$text>
                  </$button>
                  <$textButton $attrTemplate="$templateDismissButton">
                    <$text>Dismiss</$text>
                  </$textButton>
                  <$icon $attrImageVector="$filled:Add" $attrTemplate="$templateIcon" />
                  <$text $attrTemplate="$templateTitle">Alert Title</$text>
                  <$text>Alert message</$text>
                </$alertDialog>    
                """
        )
    }

    @Test
    fun basicAlertDialogTest() {
        val testTag = "basicDialog"
        compareNativeComposableWithTemplate(
            testTag = testTag,
            nativeComposable = {
                BasicAlertDialog(
                    modifier = Modifier.testTag(testTag),
                    onDismissRequest = {},
                ) {
                    Column {
                        Text(text = "Title")
                        Text(text = "Message")
                        Row {
                            Button(onClick = {}) {
                                Text(text = "Cancel")
                            }
                            Button(onClick = {}) {
                                Text(text = "Ok")
                            }
                        }
                    }
                }
            },
            template = """
                <$basicAlertDialog $attrStyle="$modifierTestTag('$testTag')">
                  <$column>
                    <$text>Title</$text>
                    <$text>Message</$text>
                    <$row>
                      <$button><$text>Cancel</$text></$button>
                      <$button><$text>Ok</$text></$button>
                    </$row>
                  </$column>  
                </$basicAlertDialog>
                """
        )
    }
}