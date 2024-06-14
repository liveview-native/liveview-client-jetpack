package com.dockyard.liveviewtest.liveview.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.dockyard.liveviewtest.liveview.test.util.LiveViewComposableTest
import org.junit.Test
import org.phoenixframework.liveview.data.constants.Attrs.attrColors
import org.phoenixframework.liveview.data.constants.Attrs.attrEnabled
import org.phoenixframework.liveview.data.constants.Attrs.attrImageVector
import org.phoenixframework.liveview.data.constants.Attrs.attrIsError
import org.phoenixframework.liveview.data.constants.Attrs.attrMinLines
import org.phoenixframework.liveview.data.constants.Attrs.attrPhxChange
import org.phoenixframework.liveview.data.constants.Attrs.attrPhxValue
import org.phoenixframework.liveview.data.constants.Attrs.attrShape
import org.phoenixframework.liveview.data.constants.Attrs.attrTemplate
import org.phoenixframework.liveview.data.constants.Attrs.attrVisualTransformation
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledLabelColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledLeadingIconColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledPlaceholderColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledPrefixColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledSuffixColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledSupportingTextColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledTextColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledTrailingIconColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrErrorContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrErrorLabelColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrErrorLeadingIconColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrErrorPlaceholderColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrErrorPrefixColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrErrorSuffixColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrErrorSupportingTextColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrErrorTextColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrErrorTrailingIconColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrUnfocusedContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrUnfocusedLabelColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrUnfocusedLeadingIconColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrUnfocusedPlaceholderColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrUnfocusedPrefixColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrUnfocusedSuffixColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrUnfocusedSupportingTextColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrUnfocusedTextColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrUnfocusedTrailingIconColor
import org.phoenixframework.liveview.data.constants.IconPrefixValues.autoMirroredFilled
import org.phoenixframework.liveview.data.constants.IconPrefixValues.filled
import org.phoenixframework.liveview.data.constants.ShapeValues.circle
import org.phoenixframework.liveview.data.constants.Templates.templateLabel
import org.phoenixframework.liveview.data.constants.Templates.templateLeadingIcon
import org.phoenixframework.liveview.data.constants.Templates.templatePlaceholder
import org.phoenixframework.liveview.data.constants.Templates.templatePrefix
import org.phoenixframework.liveview.data.constants.Templates.templateSuffix
import org.phoenixframework.liveview.data.constants.Templates.templateSupportingText
import org.phoenixframework.liveview.data.constants.Templates.templateTrailingIcon
import org.phoenixframework.liveview.data.constants.VisualTransformationValues
import org.phoenixframework.liveview.data.constants.ComposableTypes.column
import org.phoenixframework.liveview.data.constants.ComposableTypes.icon
import org.phoenixframework.liveview.data.constants.ComposableTypes.outlinedTextField
import org.phoenixframework.liveview.data.constants.ComposableTypes.text
import org.phoenixframework.liveview.data.constants.ComposableTypes.textField

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
                <$column>
                    <$textField $attrPhxValue="Text 1" />
                    <$textField $attrPhxValue="Text 2" $attrMinLines="3" />
                    <$textField $attrPhxValue="Text 3" $attrEnabled="false" />
                    <$textField $attrPhxValue="Text 4" $attrShape="$circle" />
                    <$textField $attrPhxValue="Text 5" $attrIsError="true" />
                    <$textField $attrPhxValue="Text 6" $attrVisualTransformation="${VisualTransformationValues.password}" />
                </$column>
                """
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
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = ""
                            )
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
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = ""
                            )
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
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = ""
                            )
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
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = ""
                            )
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
                <$column>
                  <$textField $attrPhxValue="Text 1" $attrPhxChange="">
                    <$text $attrTemplate="$templateLabel">Label</$text>
                  </$textField>
                  <$textField $attrPhxValue="" $attrPhxChange="">
                    <$text $attrTemplate="$templateLabel">Label</$text>
                    <$text $attrTemplate="$templatePlaceholder">Placeholder</$text>
                  </$textField>
                  <$textField $attrPhxValue="Text 3" $attrPhxChange="">
                    <$text $attrTemplate="$templateLabel">Label</$text>
                    <$icon $attrTemplate="$templateLeadingIcon" $attrImageVector="$filled:AccountCircle" />                    
                  </$textField>     
                  <$textField $attrPhxValue="Text 4" $attrPhxChange="">
                    <$text $attrTemplate="$templateLabel">Label</$text>
                    <$icon $attrTemplate="$templateLeadingIcon" $attrImageVector="$filled:AccountCircle" /> 
                    <$icon $attrTemplate="$templateTrailingIcon" $attrImageVector="$autoMirroredFilled:Send" /> 
                  </$textField>                        
                  <$textField $attrPhxValue="Text 5" $attrPhxChange="">
                    <$text $attrTemplate="$templateLabel">Label</$text>
                    <$icon $attrTemplate="$templateLeadingIcon" $attrImageVector="$filled:AccountCircle" /> 
                    <$icon $attrTemplate="$templateTrailingIcon" $attrImageVector="$autoMirroredFilled:Send" />
                    <$text $attrTemplate="$templatePrefix">Pre</$text>
                  </$textField>  
                  <$textField $attrPhxValue="Text 6" $attrPhxChange="">
                    <$text $attrTemplate="$templateLabel">Label</$text>
                    <$icon $attrTemplate="$templateLeadingIcon" $attrImageVector="filled:AccountCircle" /> 
                    <$icon $attrTemplate="$templateTrailingIcon" $attrImageVector="$autoMirroredFilled:Send" />
                    <$text $attrTemplate="$templatePrefix">Pre</$text>
                    <$text $attrTemplate="$templateSuffix">Suf</$text>
                  </$textField>   
                  <$textField $attrPhxValue="Text 7" $attrPhxChange="">
                    <$text $attrTemplate="$templateLabel">Label</$text>
                    <$icon $attrTemplate="$templateLeadingIcon" $attrImageVector="filled:AccountCircle" /> 
                    <$icon $attrTemplate="$templateTrailingIcon" $attrImageVector="$autoMirroredFilled:Send" />
                    <$text $attrTemplate="$templatePrefix">Pre</$text>
                    <$text $attrTemplate="$templateSuffix">Suf</$text>
                    <$text $attrTemplate="$templateSupportingText">Supporting</$text>
                  </$textField>                                                                
                </$column>
                """
        )
    }

    @Test
    fun textFieldCustomColorsTest() {
        val colorsForTemplate = """
            {
            '$colorAttrUnfocusedTextColor': '#FF0000FF', 
            '$colorAttrUnfocusedContainerColor': '#FF00FF00', 
            '$colorAttrUnfocusedLeadingIconColor': '#FFFFFFFF', 
            '$colorAttrUnfocusedTrailingIconColor': '#FFFF00FF', 
            '$colorAttrUnfocusedLabelColor': '#FFFF0000', 
            '$colorAttrUnfocusedPlaceholderColor': '#FFCCCCCC', 
            '$colorAttrUnfocusedSupportingTextColor': '#FF444444', 
            '$colorAttrUnfocusedPrefixColor': '#FF00FFFF', 
            '$colorAttrUnfocusedSuffixColor': '#FF000000', 
            '$colorAttrDisabledTextColor': '#FF888888', 
            '$colorAttrDisabledContainerColor': '#FFCCCCCC', 
            '$colorAttrDisabledLeadingIconColor': '#FF444444', 
            '$colorAttrDisabledTrailingIconColor': '#FF000000', 
            '$colorAttrDisabledLabelColor': '#FFFFFFFF', 
            '$colorAttrDisabledPlaceholderColor': '#FFFFFF00', 
            '$colorAttrDisabledSupportingTextColor': '#FF888888', 
            '$colorAttrDisabledPrefixColor': '#FFFF00FF', 
            '$colorAttrDisabledSuffixColor': '#FF00FFFF', 
            '$colorAttrErrorTextColor': '#FFFFFF00', 
            '$colorAttrErrorContainerColor': '#FFFF0000', 
            '$colorAttrErrorLeadingIconColor': '#FFFF00FF', 
            '$colorAttrErrorTrailingIconColor': '#FFFFFF00',
            '$colorAttrErrorLabelColor': '#FFFFFFFF', 
            '$colorAttrErrorPlaceholderColor': '#FF00FF00', 
            '$colorAttrErrorSupportingTextColor': '#FFFF0000', 
            '$colorAttrErrorPrefixColor': '#FF0000FF',
            '$colorAttrErrorSuffixColor': '#FF000000' 
            }            
            """.toJsonForTemplate()
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
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = ""
                            )
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
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = ""
                            )
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
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = ""
                            )
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
                <$column>
                  <$textField $attrPhxValue="Text 1" $attrColors="$colorsForTemplate">
                    <$text $attrTemplate="$templateLabel">Label</$text>
                    <$icon $attrTemplate="$templateLeadingIcon" $attrImageVector="$filled:AccountCircle" /> 
                    <$icon $attrTemplate="$templateTrailingIcon" $attrImageVector="$autoMirroredFilled:Send" />
                    <$text $attrTemplate="$templatePrefix">Pre</$text>
                    <$text $attrTemplate="$templateSuffix">Suf</$text>
                    <$text $attrTemplate="$templateSupportingText">Supporting</$text>
                  </$textField>    
                  <$textField $attrPhxValue="Text 2" $attrEnabled="false" $attrColors="$colorsForTemplate">
                    <$text $attrTemplate="$templateLabel">Label</$text>
                    <$icon $attrTemplate="$templateLeadingIcon" $attrImageVector="$filled:AccountCircle" /> 
                    <$icon $attrTemplate="$templateTrailingIcon" $attrImageVector="$autoMirroredFilled:Send" />
                    <$text $attrTemplate="$templatePrefix">Pre</$text>
                    <$text $attrTemplate="$templateSuffix">Suf</$text>
                    <$text $attrTemplate="$templateSupportingText">Supporting</$text>
                  </$textField>   
                  <$textField $attrPhxValue="Text 3" $attrIsError="true" $attrColors="$colorsForTemplate">
                    <$text $attrTemplate="$templateLabel">Label</$text>
                    <$icon $attrTemplate="$templateLeadingIcon" $attrImageVector="$filled:AccountCircle" /> 
                    <$icon $attrTemplate="$templateTrailingIcon" $attrImageVector="$autoMirroredFilled:Send" />
                    <$text $attrTemplate="$templatePrefix">Pre</$text>
                    <$text $attrTemplate="$templateSuffix">Suf</$text>
                    <$text $attrTemplate="$templateSupportingText">Supporting</$text>
                  </$textField>                                                                                               
                </$column>
                """
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
                <$column>
                    <$outlinedTextField $attrPhxValue="Text 1" />
                    <$outlinedTextField $attrPhxValue="Text 2" $attrMinLines="3" />
                    <$outlinedTextField $attrPhxValue="Text 3" $attrEnabled="false" />
                    <$outlinedTextField $attrPhxValue="Text 4" $attrShape="$circle" />
                    <$outlinedTextField $attrPhxValue="Text 5" $attrIsError="true" />
                    <$outlinedTextField $attrPhxValue="Text 6" $attrVisualTransformation="${VisualTransformationValues.password}" />
                </$column>
                """
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
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = ""
                            )
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
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = ""
                            )
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
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = ""
                            )
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
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = ""
                            )
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
                <$column>
                  <$outlinedTextField $attrPhxValue="Text 1" $attrPhxChange="">
                    <$text $attrTemplate="$templateLabel">Label</$text>
                  </$outlinedTextField>
                  <$outlinedTextField $attrPhxValue="" $attrPhxChange="">
                    <$text $attrTemplate="$templateLabel">Label</$text>
                    <$text $attrTemplate="$templatePlaceholder">Placeholder</$text>
                  </$outlinedTextField>
                  <$outlinedTextField $attrPhxValue="Text 3" $attrPhxChange="">
                    <$text $attrTemplate="$templateLabel">Label</$text>
                    <$icon $attrTemplate="$templateLeadingIcon" $attrImageVector="$filled:AccountCircle" />                    
                  </$outlinedTextField>     
                  <$outlinedTextField $attrPhxValue="Text 4" $attrPhxChange="">
                    <$text $attrTemplate="$templateLabel">Label</$text>
                    <$icon $attrTemplate="$templateLeadingIcon" $attrImageVector="$filled:AccountCircle" /> 
                    <$icon $attrTemplate="$templateTrailingIcon" $attrImageVector="$autoMirroredFilled:Send" /> 
                  </$outlinedTextField>                        
                  <$outlinedTextField $attrPhxValue="Text 5" $attrPhxChange="">
                    <$text $attrTemplate="$templateLabel">Label</$text>
                    <$icon $attrTemplate="$templateLeadingIcon" $attrImageVector="$filled:AccountCircle" /> 
                    <$icon $attrTemplate="$templateTrailingIcon" $attrImageVector="$autoMirroredFilled:Send" />
                    <$text $attrTemplate="$templatePrefix">Pre</$text>
                  </$outlinedTextField>  
                  <$outlinedTextField $attrPhxValue="Text 6" $attrPhxChange="">
                    <$text $attrTemplate="$templateLabel">Label</$text>
                    <$icon $attrTemplate="$templateLeadingIcon" $attrImageVector="$filled:AccountCircle" /> 
                    <$icon $attrTemplate="$templateTrailingIcon" $attrImageVector="$autoMirroredFilled:Send" />
                    <$text $attrTemplate="$templatePrefix">Pre</$text>
                    <$text $attrTemplate="$templateSuffix">Suf</$text>
                  </$outlinedTextField>   
                  <$outlinedTextField $attrPhxValue="Text 7" $attrPhxChange="">
                    <$text $attrTemplate="$templateLabel">Label</$text>
                    <$icon $attrTemplate="$templateLeadingIcon" $attrImageVector="$filled:AccountCircle" /> 
                    <$icon $attrTemplate="$templateTrailingIcon" $attrImageVector="$autoMirroredFilled:Send" />
                    <$text $attrTemplate="$templatePrefix">Pre</$text>
                    <$text $attrTemplate="$templateSuffix">Suf</$text>
                    <$text $attrTemplate="$templateSupportingText">Supporting</$text>
                  </$outlinedTextField>                                                                
                </$column>
                """
        )
    }

    @Test
    fun outlinedTextFieldCustomColorsTest() {
        val colorsForTemplate = """
            {
            '$colorAttrUnfocusedTextColor': '#FF0000FF', 
            '$colorAttrUnfocusedContainerColor': '#FF00FF00', 
            '$colorAttrUnfocusedLeadingIconColor': '#FFFFFFFF', 
            '$colorAttrUnfocusedTrailingIconColor': '#FFFF00FF', 
            '$colorAttrUnfocusedLabelColor': '#FFFF0000', 
            '$colorAttrUnfocusedPlaceholderColor': '#FFCCCCCC', 
            '$colorAttrUnfocusedSupportingTextColor': '#FF444444', 
            '$colorAttrUnfocusedPrefixColor': '#FF00FFFF', 
            '$colorAttrUnfocusedSuffixColor': '#FF000000', 
            '$colorAttrDisabledTextColor': '#FF888888', 
            '$colorAttrDisabledContainerColor': '#FFCCCCCC', 
            '$colorAttrDisabledLeadingIconColor': '#FF444444', 
            '$colorAttrDisabledTrailingIconColor': '#FF000000', 
            '$colorAttrDisabledLabelColor': '#FFFFFFFF', 
            '$colorAttrDisabledPlaceholderColor': '#FFFFFF00', 
            '$colorAttrDisabledSupportingTextColor': '#FF888888', 
            '$colorAttrDisabledPrefixColor': '#FFFF00FF', 
            '$colorAttrDisabledSuffixColor': '#FF00FFFF', 
            '$colorAttrErrorTextColor': '#FFFFFF00', 
            '$colorAttrErrorContainerColor': '#FFFF0000', 
            '$colorAttrErrorLeadingIconColor': '#FFFF00FF', 
            '$colorAttrErrorTrailingIconColor': '#FFFFFF00',
            '$colorAttrErrorLabelColor': '#FFFFFFFF', 
            '$colorAttrErrorPlaceholderColor': '#FF00FF00', 
            '$colorAttrErrorSupportingTextColor': '#FFFF0000', 
            '$colorAttrErrorPrefixColor': '#FF0000FF',
            '$colorAttrErrorSuffixColor': '#FF000000' 
            }            
            """.toJsonForTemplate()
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
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = ""
                            )
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
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = ""
                            )
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
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = ""
                            )
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
                <$column>
                  <$outlinedTextField $attrPhxValue="Text 1" $attrColors="$colorsForTemplate">
                    <$text $attrTemplate="$templateLabel">Label</$text>
                    <$icon $attrTemplate="$templateLeadingIcon" $attrImageVector="filled:AccountCircle" /> 
                    <$icon $attrTemplate="$templateTrailingIcon" $attrImageVector="$autoMirroredFilled:Send" />
                    <$text $attrTemplate="$templatePrefix">Pre</$text>
                    <$text $attrTemplate="$templateSuffix">Suf</$text>
                    <$text $attrTemplate="$templateSupportingText">Supporting</$text>
                  </$outlinedTextField>    
                  <$outlinedTextField $attrPhxValue="Text 2" $attrEnabled="false" $attrColors="$colorsForTemplate">
                    <$text $attrTemplate="$templateLabel">Label</$text>
                    <$icon $attrTemplate="$templateLeadingIcon" $attrImageVector="filled:AccountCircle" /> 
                    <$icon $attrTemplate="$templateTrailingIcon" $attrImageVector="$autoMirroredFilled:Send" />
                    <$text $attrTemplate="$templatePrefix">Pre</$text>
                    <$text $attrTemplate="$templateSuffix">Suf</$text>
                    <$text $attrTemplate="$templateSupportingText">Supporting</$text>
                  </$outlinedTextField>   
                  <$outlinedTextField $attrPhxValue="Text 3" $attrIsError="true" $attrColors="$colorsForTemplate">
                    <$text $attrTemplate="$templateLabel">Label</$text>
                    <$icon $attrTemplate="$templateLeadingIcon" $attrImageVector="filled:AccountCircle" /> 
                    <$icon $attrTemplate="$templateTrailingIcon" $attrImageVector="$autoMirroredFilled:Send" />
                    <$text $attrTemplate="$templatePrefix">Pre</$text>
                    <$text $attrTemplate="$templateSuffix">Suf</$text>
                    <$text $attrTemplate="$templateSupportingText">Supporting</$text>
                  </$outlinedTextField>                                                                                               
                </$column>
                """
        )
    }
}