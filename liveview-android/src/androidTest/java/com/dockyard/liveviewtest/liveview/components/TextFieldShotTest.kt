package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test

class TextFieldShotTest : LiveViewComposableTest() {
    @Test
    fun textFieldTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Column {
                    TextField(value = "Text 1", onValueChange = {})
                    TextField(value = "Text 2", onValueChange = {}, minLines = 3)
                    TextField(value = "Text 3", onValueChange = {}, enabled = false)
                    TextField(value = "Text 4", onValueChange = {}, shape = CircleShape)
                    TextField(value = "Text 5", onValueChange = {}, isError = true)
                    TextField(
                        value = "Text 6",
                        onValueChange = {},
                        visualTransformation = PasswordVisualTransformation()
                    )
                }
            }, template = """
                <Column>
                    <TextField phx-value="Text 1" phx-change="" />
                    <TextField phx-value="Text 2" phx-change="" min-lines="3" />
                    <TextField phx-value="Text 3" phx-change="" enabled="false" />
                    <TextField phx-value="Text 4" phx-change="" shape="circle" />
                    <TextField phx-value="Text 5" phx-change="" is-error="true" />
                    <TextField phx-value="Text 6" phx-change="" visual-transformation="password" />
                </Column>
                """.templateToTest()
        )
    }

    @Test
    fun textFieldWithChildrenTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Column {
                    TextField(
                        value = "Text 1", onValueChange = {},
                        label = {
                            Text(text = "Label")
                        },
                    )
                    TextField(
                        value = "", onValueChange = {},
                        label = {
                            Text(text = "Label")
                        },
                        placeholder = {
                            Text(text = "Placeholder")
                        },
                    )
                    TextField(
                        value = "Text 3", onValueChange = {},
                        label = {
                            Text(text = "Label")
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                        },
                    )
                    TextField(
                        value = "Text 4", onValueChange = {},
                        label = {
                            Text(text = "Label")
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.Send, contentDescription = "")
                        },
                    )
                    TextField(
                        value = "Text 5", onValueChange = {},
                        label = {
                            Text(text = "Label")
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.Send, contentDescription = "")
                        },
                        prefix = {
                            Text(text = "Pre")
                        },
                    )
                    TextField(
                        value = "Text 6", onValueChange = {},
                        label = {
                            Text(text = "Label")
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.Send, contentDescription = "")
                        },
                        prefix = {
                            Text(text = "Pre")
                        },
                        suffix = {
                            Text(text = "Suf")
                        },
                    )
                    TextField(
                        value = "Text 7", onValueChange = {},
                        label = {
                            Text(text = "Label")
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.Send, contentDescription = "")
                        },
                        prefix = {
                            Text(text = "Pre")
                        },
                        suffix = {
                            Text(text = "Suf")
                        },
                        supportingText = {
                            Text(text = "Supporting")
                        },
                    )
                }
            }, template = """
                <Column>
                  <TextField phx-value="Text 1" phx-change="">
                    <Text template="label">Label</Text>
                  </Text>
                  <TextField phx-value="" phx-change="">
                    <Text template="label">Label</Text>
                    <Text template="placeholder">Placeholder</Text>
                  </Text>
                  <TextField phx-value="Text 3" phx-change="">
                    <Text template="label">Label</Text>
                    <Icon template="leadingIcon" image-vector="filled:AccountCircle" />                    
                  </Text>     
                  <TextField phx-value="Text 4" phx-change="">
                    <Text template="label">Label</Text>
                    <Icon template="leadingIcon" image-vector="filled:AccountCircle" /> 
                    <Icon template="trailingIcon" image-vector="filled:Send" /> 
                  </Text>                        
                  <TextField phx-value="Text 5" phx-change="">
                    <Text template="label">Label</Text>
                    <Icon template="leadingIcon" image-vector="filled:AccountCircle" /> 
                    <Icon template="trailingIcon" image-vector="filled:Send" />
                    <Text template="prefix">Pre</Text>
                  </Text>  
                  <TextField phx-value="Text 6" phx-change="">
                    <Text template="label">Label</Text>
                    <Icon template="leadingIcon" image-vector="filled:AccountCircle" /> 
                    <Icon template="trailingIcon" image-vector="filled:Send" />
                    <Text template="prefix">Pre</Text>
                    <Text template="suffix">Suf</Text>
                  </Text>   
                  <TextField phx-value="Text 7" phx-change="">
                    <Text template="label">Label</Text>
                    <Icon template="leadingIcon" image-vector="filled:AccountCircle" /> 
                    <Icon template="trailingIcon" image-vector="filled:Send" />
                    <Text template="prefix">Pre</Text>
                    <Text template="suffix">Suf</Text>
                    <Text template="supportingText">Supporting</Text>
                  </Text>                                                                
                </Column>
                """.templateToTest()
        )
    }

    @Test
    fun textFieldCustomColorsTest() {
        val colorsForTemplate = """
            {
                'unfocusedTextColor': '#FF0000FF', 
                'unfocusedContainerColor': '#FF00FF00', 
                'unfocusedLeadingIconColor': '#FFFFFFFF', 
                'unfocusedTrailingIconColor': '#FFFF00FF', 
                'unfocusedLabelColor': '#FFFF0000', 
                'unfocusedPlaceholderColor': '#FFCCCCCC', 
                'unfocusedSupportingTextColor': '#FF444444', 
                'unfocusedPrefixColor': '#FF00FFFF', 
                'unfocusedSuffixColor': '#FF000000', 
                'disabledTextColor': '#FF888888', 
                'disabledContainerColor': '#FFCCCCCC', 
                'disabledLeadingIconColor': '#FF444444', 
                'disabledTrailingIconColor': '#FF000000', 
                'disabledLabelColor': '#FFFFFFFF', 
                'disabledPlaceholderColor': '#FFFFFF00', 
                'disabledSupportingTextColor': '#FF888888', 
                'disabledPrefixColor': '#FFFF00FF', 
                'disabledSuffixColor': '#FF00FFFF', 
                'errorTextColor': '#FFFFFF00', 
                'errorContainerColor': '#FFFF0000', 
                'errorLeadingIconColor': '#FFFF00FF', 
                'errorTrailingIconColor': '#FFFFFF00',
                'errorLabelColor': '#FFFFFFFF', 
                'errorPlaceholderColor': '#FF00FF00', 
                'errorSupportingTextColor': '#FFFF0000', 
                'errorPrefixColor': '#FF0000FF',
                'errorSuffixColor': '#FF000000' 
            }            
            """.trimIndent().trim().replace("\n", "")
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val colors = TextFieldDefaults.colors(
                    unfocusedTextColor = Color.Blue,
                    unfocusedContainerColor = Color.Green,
                    unfocusedLeadingIconColor = Color.White,
                    unfocusedTrailingIconColor = Color.Magenta,
                    unfocusedLabelColor = Color.Red,
                    unfocusedPlaceholderColor = Color.LightGray,
                    unfocusedSupportingTextColor = Color.DarkGray,
                    unfocusedPrefixColor = Color.Cyan,
                    unfocusedSuffixColor = Color.Black,
                    disabledTextColor = Color.Gray,
                    disabledContainerColor = Color.LightGray,
                    disabledLeadingIconColor = Color.DarkGray,
                    disabledTrailingIconColor = Color.Black,
                    disabledLabelColor = Color.White,
                    disabledPlaceholderColor = Color.Yellow,
                    disabledSupportingTextColor = Color.Gray,
                    disabledPrefixColor = Color.Magenta,
                    disabledSuffixColor = Color.Cyan,
                    errorTextColor = Color.Yellow,
                    errorContainerColor = Color.Red,
                    errorLeadingIconColor = Color.Magenta,
                    errorTrailingIconColor = Color.Yellow,
                    errorLabelColor = Color.White,
                    errorPlaceholderColor = Color.Green,
                    errorSupportingTextColor = Color.Red,
                    errorPrefixColor = Color.Blue,
                    errorSuffixColor = Color.Black
                )
                Column {
                    TextField(
                        value = "Text 1", onValueChange = {}, colors = colors,
                        label = {
                            Text(text = "Label")
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.Send, contentDescription = "")
                        },
                        prefix = {
                            Text(text = "Pre")
                        },
                        suffix = {
                            Text(text = "Suf")
                        },
                        supportingText = {
                            Text(text = "Supporting")
                        },
                    )
                    TextField(
                        value = "Text 2",
                        onValueChange = {},
                        enabled = false,
                        colors = colors,
                        label = {
                            Text(text = "Label")
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.Send, contentDescription = "")
                        },
                        prefix = {
                            Text(text = "Pre")
                        },
                        suffix = {
                            Text(text = "Suf")
                        },
                        supportingText = {
                            Text(text = "Supporting")
                        },
                    )
                    TextField(
                        value = "Text 3",
                        onValueChange = {},
                        isError = true,
                        colors = colors,
                        label = {
                            Text(text = "Label")
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.Send, contentDescription = "")
                        },
                        prefix = {
                            Text(text = "Pre")
                        },
                        suffix = {
                            Text(text = "Suf")
                        },
                        supportingText = {
                            Text(text = "Supporting")
                        },
                    )
                }
            }, template = """
                <Column>
                  <TextField phx-value="Text 1" phx-change="" colors="$colorsForTemplate">
                    <Text template="label">Label</Text>
                    <Icon template="leadingIcon" image-vector="filled:AccountCircle" /> 
                    <Icon template="trailingIcon" image-vector="filled:Send" />
                    <Text template="prefix">Pre</Text>
                    <Text template="suffix">Suf</Text>
                    <Text template="supportingText">Supporting</Text>
                  </Text>    
                  <TextField phx-value="Text 2" phx-change="" enabled="false" colors="$colorsForTemplate">
                    <Text template="label">Label</Text>
                    <Icon template="leadingIcon" image-vector="filled:AccountCircle" /> 
                    <Icon template="trailingIcon" image-vector="filled:Send" />
                    <Text template="prefix">Pre</Text>
                    <Text template="suffix">Suf</Text>
                    <Text template="supportingText">Supporting</Text>
                  </Text>   
                  <TextField phx-value="Text 3" phx-change="" is-error="true" colors="$colorsForTemplate">
                    <Text template="label">Label</Text>
                    <Icon template="leadingIcon" image-vector="filled:AccountCircle" /> 
                    <Icon template="trailingIcon" image-vector="filled:Send" />
                    <Text template="prefix">Pre</Text>
                    <Text template="suffix">Suf</Text>
                    <Text template="supportingText">Supporting</Text>
                  </Text>                                                                                               
                </Column>
                """.templateToTest()
        )
    }

    @Test
    fun outlinedTextFieldTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Column {
                    OutlinedTextField(value = "Text 1", onValueChange = {})
                    OutlinedTextField(value = "Text 2", onValueChange = {}, minLines = 3)
                    OutlinedTextField(value = "Text 3", onValueChange = {}, enabled = false)
                    OutlinedTextField(value = "Text 4", onValueChange = {}, shape = CircleShape)
                    OutlinedTextField(value = "Text 5", onValueChange = {}, isError = true)
                    OutlinedTextField(
                        value = "Text 6",
                        onValueChange = {},
                        visualTransformation = PasswordVisualTransformation()
                    )
                }
            }, template = """
                <Column>
                    <OutlinedTextField phx-value="Text 1" phx-change="" />
                    <OutlinedTextField phx-value="Text 2" phx-change="" min-lines="3" />
                    <OutlinedTextField phx-value="Text 3" phx-change="" enabled="false" />
                    <OutlinedTextField phx-value="Text 4" phx-change="" shape="circle" />
                    <OutlinedTextField phx-value="Text 5" phx-change="" is-error="true" />
                    <OutlinedTextField phx-value="Text 6" phx-change="" visual-transformation="password" />
                </Column>
                """.templateToTest()
        )
    }

    @Test
    fun outlinedTextFieldWithChildrenTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Column {
                    OutlinedTextField(
                        value = "Text 1", onValueChange = {},
                        label = {
                            Text(text = "Label")
                        },
                    )
                    OutlinedTextField(
                        value = "", onValueChange = {},
                        label = {
                            Text(text = "Label")
                        },
                        placeholder = {
                            Text(text = "Placeholder")
                        },
                    )
                    OutlinedTextField(
                        value = "Text 3", onValueChange = {},
                        label = {
                            Text(text = "Label")
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                        },
                    )
                    OutlinedTextField(
                        value = "Text 4", onValueChange = {},
                        label = {
                            Text(text = "Label")
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.Send, contentDescription = "")
                        },
                    )
                    OutlinedTextField(
                        value = "Text 5", onValueChange = {},
                        label = {
                            Text(text = "Label")
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.Send, contentDescription = "")
                        },
                        prefix = {
                            Text(text = "Pre")
                        },
                    )
                    OutlinedTextField(
                        value = "Text 6", onValueChange = {},
                        label = {
                            Text(text = "Label")
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.Send, contentDescription = "")
                        },
                        prefix = {
                            Text(text = "Pre")
                        },
                        suffix = {
                            Text(text = "Suf")
                        },
                    )
                    OutlinedTextField(
                        value = "Text 7", onValueChange = {},
                        label = {
                            Text(text = "Label")
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.Send, contentDescription = "")
                        },
                        prefix = {
                            Text(text = "Pre")
                        },
                        suffix = {
                            Text(text = "Suf")
                        },
                        supportingText = {
                            Text(text = "Supporting")
                        },
                    )
                }
            }, template = """
                <Column>
                  <OutlinedTextField phx-value="Text 1" phx-change="">
                    <Text template="label">Label</Text>
                  </OutlinedTextField>
                  <OutlinedTextField phx-value="" phx-change="">
                    <Text template="label">Label</Text>
                    <Text template="placeholder">Placeholder</Text>
                  </OutlinedTextField>
                  <OutlinedTextField phx-value="Text 3" phx-change="">
                    <Text template="label">Label</Text>
                    <Icon template="leadingIcon" image-vector="filled:AccountCircle" />                    
                  </OutlinedTextField>     
                  <OutlinedTextField phx-value="Text 4" phx-change="">
                    <Text template="label">Label</Text>
                    <Icon template="leadingIcon" image-vector="filled:AccountCircle" /> 
                    <Icon template="trailingIcon" image-vector="filled:Send" /> 
                  </OutlinedTextField>                        
                  <OutlinedTextField phx-value="Text 5" phx-change="">
                    <Text template="label">Label</Text>
                    <Icon template="leadingIcon" image-vector="filled:AccountCircle" /> 
                    <Icon template="trailingIcon" image-vector="filled:Send" />
                    <Text template="prefix">Pre</Text>
                  </OutlinedTextField>  
                  <OutlinedTextField phx-value="Text 6" phx-change="">
                    <Text template="label">Label</Text>
                    <Icon template="leadingIcon" image-vector="filled:AccountCircle" /> 
                    <Icon template="trailingIcon" image-vector="filled:Send" />
                    <Text template="prefix">Pre</Text>
                    <Text template="suffix">Suf</Text>
                  </OutlinedTextField>   
                  <OutlinedTextField phx-value="Text 7" phx-change="">
                    <Text template="label">Label</Text>
                    <Icon template="leadingIcon" image-vector="filled:AccountCircle" /> 
                    <Icon template="trailingIcon" image-vector="filled:Send" />
                    <Text template="prefix">Pre</Text>
                    <Text template="suffix">Suf</Text>
                    <Text template="supportingText">Supporting</Text>
                  </OutlinedTextField>                                                                
                </Column>
                """.templateToTest()
        )
    }

    @Test
    fun outlinedTextFieldCustomColorsTest() {
        val colorsForTemplate = """
            {
                'unfocusedTextColor': '#FF0000FF', 
                'unfocusedContainerColor': '#FF00FF00', 
                'unfocusedLeadingIconColor': '#FFFFFFFF', 
                'unfocusedTrailingIconColor': '#FFFF00FF', 
                'unfocusedLabelColor': '#FFFF0000', 
                'unfocusedPlaceholderColor': '#FFCCCCCC', 
                'unfocusedSupportingTextColor': '#FF444444', 
                'unfocusedPrefixColor': '#FF00FFFF', 
                'unfocusedSuffixColor': '#FF000000', 
                'disabledTextColor': '#FF888888', 
                'disabledContainerColor': '#FFCCCCCC', 
                'disabledLeadingIconColor': '#FF444444', 
                'disabledTrailingIconColor': '#FF000000', 
                'disabledLabelColor': '#FFFFFFFF', 
                'disabledPlaceholderColor': '#FFFFFF00', 
                'disabledSupportingTextColor': '#FF888888', 
                'disabledPrefixColor': '#FFFF00FF', 
                'disabledSuffixColor': '#FF00FFFF', 
                'errorTextColor': '#FFFFFF00', 
                'errorContainerColor': '#FFFF0000', 
                'errorLeadingIconColor': '#FFFF00FF', 
                'errorTrailingIconColor': '#FFFFFF00',
                'errorLabelColor': '#FFFFFFFF', 
                'errorPlaceholderColor': '#FF00FF00', 
                'errorSupportingTextColor': '#FFFF0000', 
                'errorPrefixColor': '#FF0000FF',
                'errorSuffixColor': '#FF000000' 
            }            
            """.trimIndent().trim().replace("\n", "")
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val colors = OutlinedTextFieldDefaults.colors(
                    unfocusedTextColor = Color.Blue,
                    unfocusedContainerColor = Color.Green,
                    unfocusedLeadingIconColor = Color.White,
                    unfocusedTrailingIconColor = Color.Magenta,
                    unfocusedLabelColor = Color.Red,
                    unfocusedPlaceholderColor = Color.LightGray,
                    unfocusedSupportingTextColor = Color.DarkGray,
                    unfocusedPrefixColor = Color.Cyan,
                    unfocusedSuffixColor = Color.Black,
                    disabledTextColor = Color.Gray,
                    disabledContainerColor = Color.LightGray,
                    disabledLeadingIconColor = Color.DarkGray,
                    disabledTrailingIconColor = Color.Black,
                    disabledLabelColor = Color.White,
                    disabledPlaceholderColor = Color.Yellow,
                    disabledSupportingTextColor = Color.Gray,
                    disabledPrefixColor = Color.Magenta,
                    disabledSuffixColor = Color.Cyan,
                    errorTextColor = Color.Yellow,
                    errorContainerColor = Color.Red,
                    errorLeadingIconColor = Color.Magenta,
                    errorTrailingIconColor = Color.Yellow,
                    errorLabelColor = Color.White,
                    errorPlaceholderColor = Color.Green,
                    errorSupportingTextColor = Color.Red,
                    errorPrefixColor = Color.Blue,
                    errorSuffixColor = Color.Black
                )
                Column {
                    OutlinedTextField(
                        value = "Text 1", onValueChange = {}, colors = colors,
                        label = {
                            Text(text = "Label")
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.Send, contentDescription = "")
                        },
                        prefix = {
                            Text(text = "Pre")
                        },
                        suffix = {
                            Text(text = "Suf")
                        },
                        supportingText = {
                            Text(text = "Supporting")
                        },
                    )
                    OutlinedTextField(value = "Text 2",
                        onValueChange = {},
                        enabled = false,
                        colors = colors,
                        label = {
                            Text(text = "Label")
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.Send, contentDescription = "")
                        },
                        prefix = {
                            Text(text = "Pre")
                        },
                        suffix = {
                            Text(text = "Suf")
                        },
                        supportingText = {
                            Text(text = "Supporting")
                        })
                    OutlinedTextField(value = "Text 3",
                        onValueChange = {},
                        isError = true,
                        colors = colors,
                        label = {
                            Text(text = "Label")
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.Send, contentDescription = "")
                        },
                        prefix = {
                            Text(text = "Pre")
                        },
                        suffix = {
                            Text(text = "Suf")
                        },
                        supportingText = {
                            Text(text = "Supporting")
                        })
                }
            }, template = """
                <Column>
                  <OutlinedTextField phx-value="Text 1" phx-change="" colors="$colorsForTemplate">
                    <Text template="label">Label</Text>
                    <Icon template="leadingIcon" image-vector="filled:AccountCircle" /> 
                    <Icon template="trailingIcon" image-vector="filled:Send" />
                    <Text template="prefix">Pre</Text>
                    <Text template="suffix">Suf</Text>
                    <Text template="supportingText">Supporting</Text>
                  </OutlinedTextField>    
                  <OutlinedTextField phx-value="Text 2" phx-change="" enabled="false" colors="$colorsForTemplate">
                    <Text template="label">Label</Text>
                    <Icon template="leadingIcon" image-vector="filled:AccountCircle" /> 
                    <Icon template="trailingIcon" image-vector="filled:Send" />
                    <Text template="prefix">Pre</Text>
                    <Text template="suffix">Suf</Text>
                    <Text template="supportingText">Supporting</Text>
                  </OutlinedTextField>   
                  <OutlinedTextField phx-value="Text 3" phx-change="" is-error="true" colors="$colorsForTemplate">
                    <Text template="label">Label</Text>
                    <Icon template="leadingIcon" image-vector="filled:AccountCircle" /> 
                    <Icon template="trailingIcon" image-vector="filled:Send" />
                    <Text template="prefix">Pre</Text>
                    <Text template="suffix">Suf</Text>
                    <Text template="supportingText">Supporting</Text>
                  </OutlinedTextField>                                                                                               
                </Column>
                """.templateToTest()
        )
    }
}