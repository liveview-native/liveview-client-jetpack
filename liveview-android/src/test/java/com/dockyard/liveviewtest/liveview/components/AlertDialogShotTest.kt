package com.dockyard.liveviewtest.liveview.components

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
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test

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
                <AlertDialog phx-click="" test-tag="$testTag">
                  <Button phx-click="" template="confirm">
                    <Text>Confirm</Text>
                  </Button>
                  <TextButton phx-click="dismissEvent" template="dismiss">
                    <Text>Dismiss</Text>
                  </TextButton>
                  <Icon image-vector="filled:Add" template="icon" />
                  <Text template="title">Alert Title</Title>
                  <Text>Alert message</Text>
                </AlertDialog>    
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
                <AlertDialog 
                  phx-click="" test-tag="$testTag" shape="4" container-color="#FFF2DDE1"
                  icon-content-color="#FFFF0000" title-content-color="#FF0000FF" 
                  text-content-color="#FF00FF00" >
                  <Button phx-click="" template="confirm">
                    <Text>Confirm</Text>
                  </Button>
                  <TextButton phx-click="dismissEvent" template="dismiss">
                    <Text>Dismiss</Text>
                  </TextButton>
                  <Icon image-vector="filled:Add" template="icon" />
                  <Text template="title">Alert Title</Title>
                  <Text>Alert message</Text>
                </AlertDialog>    
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
                <BasicAlertDialog test-tag="$testTag">
                  <Column>               
                    <Text>Title</Text>
                    <Text>Message</Text>
                    <Row>
                      <Button><Text>Cancel</Text></Button>
                      <Button><Text>Ok</Text></Button>
                    </Row>
                  </Column>  
                </BasicAlertDialog>
                """
        )
    }
}