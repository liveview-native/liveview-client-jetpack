package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.sharp.Person
import androidx.compose.material.icons.sharp.PersonAdd
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ElevatedSuggestionChip
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test
import org.phoenixframework.liveview.data.constants.Attrs.attrBorder
import org.phoenixframework.liveview.data.constants.Attrs.attrColor
import org.phoenixframework.liveview.data.constants.Attrs.attrColors
import org.phoenixframework.liveview.data.constants.Attrs.attrEnabled
import org.phoenixframework.liveview.data.constants.Attrs.attrImageVector
import org.phoenixframework.liveview.data.constants.Attrs.attrSelected
import org.phoenixframework.liveview.data.constants.Attrs.attrShape
import org.phoenixframework.liveview.data.constants.Attrs.attrTemplate
import org.phoenixframework.liveview.data.constants.Attrs.attrWidth
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledIconContentColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledLabelColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledLeadingIconColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledLeadingIconContentColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledSelectedContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledTrailingIconColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDisabledTrailingIconContentColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrIconColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrIconContentColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrLabelColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrLeadingIconColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrLeadingIconContentColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrSelectedContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrSelectedLabelColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrSelectedLeadingIconColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrSelectedTrailingIconColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrTrailingIconColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrTrailingIconContentColor
import org.phoenixframework.liveview.data.constants.IconPrefixValues.filled
import org.phoenixframework.liveview.data.constants.IconPrefixValues.sharp
import org.phoenixframework.liveview.data.constants.SystemColorValues.Black
import org.phoenixframework.liveview.data.constants.SystemColorValues.Blue
import org.phoenixframework.liveview.data.constants.SystemColorValues.Cyan
import org.phoenixframework.liveview.data.constants.SystemColorValues.DarkGray
import org.phoenixframework.liveview.data.constants.SystemColorValues.Gray
import org.phoenixframework.liveview.data.constants.SystemColorValues.Green
import org.phoenixframework.liveview.data.constants.SystemColorValues.LightGray
import org.phoenixframework.liveview.data.constants.SystemColorValues.Magenta
import org.phoenixframework.liveview.data.constants.SystemColorValues.Red
import org.phoenixframework.liveview.data.constants.SystemColorValues.White
import org.phoenixframework.liveview.data.constants.SystemColorValues.Yellow
import org.phoenixframework.liveview.data.constants.Templates.templateAvatar
import org.phoenixframework.liveview.data.constants.Templates.templateIcon
import org.phoenixframework.liveview.data.constants.Templates.templateLabel
import org.phoenixframework.liveview.data.constants.Templates.templateLeadingIcon
import org.phoenixframework.liveview.data.constants.Templates.templateTrailingIcon
import org.phoenixframework.liveview.domain.base.ComposableTypes.assistChip
import org.phoenixframework.liveview.domain.base.ComposableTypes.elevatedAssistChip
import org.phoenixframework.liveview.domain.base.ComposableTypes.elevatedFilterChip
import org.phoenixframework.liveview.domain.base.ComposableTypes.elevatedSuggestionChip
import org.phoenixframework.liveview.domain.base.ComposableTypes.filterChip
import org.phoenixframework.liveview.domain.base.ComposableTypes.flowRow
import org.phoenixframework.liveview.domain.base.ComposableTypes.icon
import org.phoenixframework.liveview.domain.base.ComposableTypes.inputChip
import org.phoenixframework.liveview.domain.base.ComposableTypes.suggestionChip
import org.phoenixframework.liveview.domain.base.ComposableTypes.text

@OptIn(ExperimentalLayoutApi::class)
class ChipShotTest : LiveViewComposableTest() {

    @Test
    fun assistChipTest() {
        val colorsForTemplate = """
            {
                '$colorAttrContainerColor': '$Blue',
                '$colorAttrLabelColor': '$Yellow',
                '$colorAttrLeadingIconContentColor': '$White',
                '$colorAttrTrailingIconContentColor': '$Cyan',
                '$colorAttrDisabledContainerColor': '$LightGray',
                '$colorAttrDisabledLabelColor': '$Gray',
                '$colorAttrDisabledLeadingIconContentColor': '$DarkGray',
                '$colorAttrDisabledTrailingIconContentColor': '$Black'
            }
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val colors = AssistChipDefaults.assistChipColors(
                    containerColor = Color.Blue,
                    labelColor = Color.Yellow,
                    leadingIconContentColor = Color.White,
                    trailingIconContentColor = Color.Cyan,
                    disabledContainerColor = Color.LightGray,
                    disabledLabelColor = Color.Gray,
                    disabledLeadingIconContentColor = Color.DarkGray,
                    disabledTrailingIconContentColor = Color.Black,
                )
                FlowRow {
                    AssistChip(onClick = {}, label = { Text("Chip 1") })
                    AssistChip(onClick = {}, label = { Text("Chip 2") }, enabled = false)
                    AssistChip(
                        onClick = {}, label = { Text("Chip 3") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    AssistChip(
                        onClick = {}, label = { Text("Chip 4") }, enabled = false,
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    AssistChip(
                        onClick = {}, label = { Text("Chip 5") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    AssistChip(
                        onClick = {}, label = { Text("Chip 6") }, enabled = false,
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    AssistChip(
                        onClick = {}, label = { Text("Chip 7") }, shape = RectangleShape,
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    AssistChip(
                        onClick = {},
                        label = { Text("Chip 8") },
                        border = BorderStroke(2.dp, Color.Red),
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    AssistChip(
                        onClick = {},
                        label = { Text("Chip 9") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        },
                        colors = colors
                    )
                    AssistChip(
                        onClick = {},
                        label = { Text("Chip 10") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        },
                        enabled = false,
                        colors = colors
                    )
                }
            },
            template = """
                <$flowRow>
                  <$assistChip>
                    <$text $attrTemplate="$templateLabel">Chip 1</$text>
                  </$assistChip>     
                  <$assistChip $attrEnabled="false">
                    <$text $attrTemplate="$templateLabel">Chip 2</$text>
                  </$assistChip>      
                  <$assistChip>
                    <$icon $attrImageVector="$filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 3</$text>
                  </$assistChip>    
                  <$assistChip $attrEnabled="false">
                    <$icon $attrImageVector="$filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 4</$text>
                  </$assistChip>                                                         
                  <$assistChip>
                    <$icon $attrImageVector="$filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$icon $attrImageVector="$filled:ChevronRight" $attrTemplate="$templateTrailingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 5</$text>
                  </$assistChip>
                  <$assistChip $attrEnabled="false">
                    <$icon $attrImageVector="$filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$icon $attrImageVector="$filled:ChevronRight" $attrTemplate="$templateTrailingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 6</$text>
                  </$assistChip>   
                  <$assistChip $attrShape="rect">
                    <$icon $attrImageVector="$filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$icon $attrImageVector="$filled:ChevronRight" $attrTemplate="$templateTrailingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 7</$text>
                  </$assistChip>    
                  <$assistChip $attrBorder="{'$attrWidth': '2', '$attrColor': '$Red'}">
                    <$icon $attrImageVector="$filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$icon $attrImageVector="$filled:ChevronRight" $attrTemplate="$templateTrailingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 8</$text>
                  </$assistChip> 
                  <$assistChip $attrColors="$colorsForTemplate">
                    <$icon $attrImageVector="$filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$icon $attrImageVector="$filled:ChevronRight" $attrTemplate="$templateTrailingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 9</$text>
                  </$assistChip>   
                  <$assistChip $attrColors="$colorsForTemplate" $attrEnabled="false">
                    <$icon $attrImageVector="$filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$icon $attrImageVector="$filled:ChevronRight" $attrTemplate="$templateTrailingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 10</$text>
                  </$assistChip>                                                                                
                </$flowRow>            
                """
        )
    }

    @Test
    fun elevatedAssistChipTest() {
        val colorsForTemplate = """
            {
            '$colorAttrContainerColor': '$Blue',
            '$colorAttrLabelColor': '$Yellow',
            '$colorAttrLeadingIconContentColor': '$White',
            '$colorAttrTrailingIconContentColor': '$Cyan',
            '$colorAttrDisabledContainerColor': '$LightGray',
            '$colorAttrDisabledLabelColor': '$Gray',
            '$colorAttrDisabledLeadingIconContentColor': '$DarkGray',
            '$colorAttrDisabledTrailingIconContentColor': '$Black'
            }
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val colors = AssistChipDefaults.assistChipColors(
                    containerColor = Color.Blue,
                    labelColor = Color.Yellow,
                    leadingIconContentColor = Color.White,
                    trailingIconContentColor = Color.Cyan,
                    disabledContainerColor = Color.LightGray,
                    disabledLabelColor = Color.Gray,
                    disabledLeadingIconContentColor = Color.DarkGray,
                    disabledTrailingIconContentColor = Color.Black,
                )
                FlowRow {
                    ElevatedAssistChip(onClick = {}, label = { Text("Chip 1") })
                    ElevatedAssistChip(onClick = {}, label = { Text("Chip 2") }, enabled = false)
                    ElevatedAssistChip(
                        onClick = {}, label = { Text("Chip 3") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    ElevatedAssistChip(
                        onClick = {}, label = { Text("Chip 4") }, enabled = false,
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    ElevatedAssistChip(
                        onClick = {}, label = { Text("Chip 5") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    ElevatedAssistChip(
                        onClick = {}, label = { Text("Chip 6") }, enabled = false,
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    ElevatedAssistChip(
                        onClick = {}, label = { Text("Chip 7") }, shape = RectangleShape,
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    ElevatedAssistChip(
                        onClick = {},
                        label = { Text("Chip 8") },
                        border = BorderStroke(2.dp, Color.Red),
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    ElevatedAssistChip(
                        onClick = {},
                        label = { Text("Chip 9") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        },
                        colors = colors
                    )
                    ElevatedAssistChip(
                        onClick = {},
                        label = { Text("Chip 10") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        },
                        enabled = false,
                        colors = colors
                    )
                }
            },
            template = """
                <$flowRow>
                  <$elevatedAssistChip>
                    <$text $attrTemplate="$templateLabel">Chip 1</$text>
                  </$elevatedAssistChip>     
                  <$elevatedAssistChip $attrEnabled="false">
                    <$text $attrTemplate="$templateLabel">Chip 2</$text>
                  </$elevatedAssistChip>      
                  <$elevatedAssistChip>
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 3</$text>
                  </$elevatedAssistChip>    
                  <$elevatedAssistChip $attrEnabled="false">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 4</$text>
                  </$elevatedAssistChip>                                                         
                  <$elevatedAssistChip>
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$icon $attrImageVector="filled:ChevronRight" $attrTemplate="$templateTrailingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 5</$text>
                  </$elevatedAssistChip>
                  <$elevatedAssistChip $attrEnabled="false">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$icon $attrImageVector="filled:ChevronRight" $attrTemplate="$templateTrailingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 6</$text>
                  </$elevatedAssistChip>   
                  <$elevatedAssistChip $attrShape="rect">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$icon $attrImageVector="filled:ChevronRight" $attrTemplate="$templateTrailingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 7</$text>
                  </$elevatedAssistChip>    
                  <$elevatedAssistChip $attrBorder="{'$attrWidth': '2', '$attrColor': '$Red'}">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$icon $attrImageVector="filled:ChevronRight" $attrTemplate="$templateTrailingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 8</$text>
                  </$elevatedAssistChip> 
                  <$elevatedAssistChip $attrColors="$colorsForTemplate">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$icon $attrImageVector="filled:ChevronRight" $attrTemplate="$templateTrailingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 9</$text>
                  </$elevatedAssistChip>   
                  <$elevatedAssistChip $attrColors="$colorsForTemplate" $attrEnabled="false">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$icon $attrImageVector="filled:ChevronRight" $attrTemplate="$templateTrailingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 10</$text>
                  </$elevatedAssistChip>                                                                                
                </$flowRow>            
                """
        )
    }

    @Test
    fun suggestionChipTest() {
        val colorsForTemplate = """
            {
            '$colorAttrContainerColor': '$Blue',
            '$colorAttrLabelColor': '$Yellow',
            '$colorAttrIconContentColor': '$White',
            '$colorAttrDisabledContainerColor': '$LightGray',
            '$colorAttrDisabledLabelColor': '$Gray',
            '$colorAttrDisabledIconContentColor': '$DarkGray'
            }
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val colors = SuggestionChipDefaults.suggestionChipColors(
                    containerColor = Color.Blue,
                    labelColor = Color.Yellow,
                    iconContentColor = Color.White,
                    disabledContainerColor = Color.LightGray,
                    disabledLabelColor = Color.Gray,
                    disabledIconContentColor = Color.DarkGray,
                )
                FlowRow {
                    SuggestionChip(onClick = {}, label = { Text("Chip 1") })
                    SuggestionChip(onClick = {}, label = { Text("Chip 2") }, enabled = false)
                    SuggestionChip(
                        onClick = {}, label = { Text("Chip 3") },
                        icon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    SuggestionChip(
                        onClick = {}, label = { Text("Chip 4") }, enabled = false,
                        icon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    SuggestionChip(
                        onClick = {}, label = { Text("Chip 5") }, shape = RectangleShape,
                        icon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    SuggestionChip(
                        onClick = {},
                        label = { Text("Chip 6") },
                        border = BorderStroke(2.dp, Color.Red),
                        icon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    SuggestionChip(
                        onClick = {},
                        label = { Text("Chip 7") },
                        icon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        colors = colors
                    )
                    SuggestionChip(
                        onClick = {},
                        label = { Text("Chip 8") },
                        icon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        enabled = false,
                        colors = colors
                    )
                }
            },
            template = """
                <$flowRow>
                  <$suggestionChip>
                    <$text $attrTemplate="$templateLabel">Chip 1</$text>
                  </$suggestionChip>     
                  <$suggestionChip $attrEnabled="false">
                    <$text $attrTemplate="$templateLabel">Chip 2</$text>
                  </$suggestionChip>      
                  <$suggestionChip>
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 3</$text>
                  </$suggestionChip>    
                  <$suggestionChip $attrEnabled="false">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 4</$text>
                  </$suggestionChip>                                                          
                  <$suggestionChip $attrShape="rect">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 5</$text>
                  </$suggestionChip>    
                  <$suggestionChip $attrBorder="{'$attrWidth': '2', '$attrColor': '$Red'}">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 6</$text>
                  </$suggestionChip> 
                  <$suggestionChip $attrColors="$colorsForTemplate">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 7</$text>
                  </$suggestionChip>   
                  <$suggestionChip $attrColors="$colorsForTemplate" $attrEnabled="false">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 8</$text>
                  </$suggestionChip>                                                                                
                </$flowRow>            
                """
        )
    }

    @Test
    fun elevatedSuggestionChipTest() {
        val colorsForTemplate = """
            {
            '$colorAttrContainerColor': '$Blue',
            '$colorAttrLabelColor': '$Yellow',
            '$colorAttrIconContentColor': '$White',
            '$colorAttrDisabledContainerColor': '$LightGray',
            '$colorAttrDisabledLabelColor': '$Gray',
            '$colorAttrDisabledIconContentColor': '$DarkGray'
            }
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val colors = SuggestionChipDefaults.elevatedSuggestionChipColors(
                    containerColor = Color.Blue,
                    labelColor = Color.Yellow,
                    iconContentColor = Color.White,
                    disabledContainerColor = Color.LightGray,
                    disabledLabelColor = Color.Gray,
                    disabledIconContentColor = Color.DarkGray,
                )
                FlowRow {
                    ElevatedSuggestionChip(onClick = {}, label = { Text("Chip 1") })
                    ElevatedSuggestionChip(
                        onClick = {},
                        label = { Text("Chip 2") },
                        enabled = false
                    )
                    ElevatedSuggestionChip(
                        onClick = {}, label = { Text("Chip 3") },
                        icon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    ElevatedSuggestionChip(
                        onClick = {}, label = { Text("Chip 4") }, enabled = false,
                        icon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    ElevatedSuggestionChip(
                        onClick = {}, label = { Text("Chip 5") }, shape = RectangleShape,
                        icon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    ElevatedSuggestionChip(
                        onClick = {},
                        label = { Text("Chip 6") },
                        border = BorderStroke(2.dp, Color.Red),
                        icon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    ElevatedSuggestionChip(
                        onClick = {},
                        label = { Text("Chip 7") },
                        icon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        colors = colors
                    )
                    ElevatedSuggestionChip(
                        onClick = {},
                        label = { Text("Chip 8") },
                        icon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        enabled = false,
                        colors = colors
                    )
                }
            },
            template = """
                <$flowRow>
                  <$elevatedSuggestionChip>
                    <$text $attrTemplate="$templateLabel">Chip 1</$text>
                  </$elevatedSuggestionChip>     
                  <$elevatedSuggestionChip $attrEnabled="false">
                    <$text $attrTemplate="$templateLabel">Chip 2</$text>
                  </$elevatedSuggestionChip>      
                  <$elevatedSuggestionChip>
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 3</$text>
                  </$elevatedSuggestionChip>    
                  <$elevatedSuggestionChip $attrEnabled="false">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 4</$text>
                  </$elevatedSuggestionChip>                                                          
                  <$elevatedSuggestionChip $attrShape="rect">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 5</$text>
                  </$elevatedSuggestionChip>    
                  <$elevatedSuggestionChip $attrBorder="{'$attrWidth': '2', '$attrColor': '$Red'}">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 6</$text>
                  </$elevatedSuggestionChip> 
                  <$elevatedSuggestionChip $attrColors="$colorsForTemplate">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 7</$text>
                  </$elevatedSuggestionChip>   
                  <$elevatedSuggestionChip $attrColors="$colorsForTemplate" $attrEnabled="false">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 8</$text>
                  </$elevatedSuggestionChip>                                                                                
                </$flowRow>            
                """
        )
    }

    @Test
    fun filterChipTest() {
        val colorsForTemplate = """
            {                
            '$colorAttrContainerColor': '$Blue',
            '$colorAttrLabelColor': '$Yellow',
            '$colorAttrIconColor': '$White',
            '$colorAttrDisabledContainerColor': '$LightGray',
            '$colorAttrDisabledLabelColor': '$Gray',
            '$colorAttrDisabledLeadingIconColor': '$DarkGray',
            '$colorAttrDisabledTrailingIconColor': '$Black',
            '$colorAttrSelectedContainerColor': '$Red',
            '$colorAttrDisabledSelectedContainerColor': '$Magenta',
            '$colorAttrSelectedLabelColor': '$Green',
            '$colorAttrSelectedLeadingIconColor': '$Yellow',
            '$colorAttrSelectedTrailingIconColor': '$White'                
            }
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val colors = FilterChipDefaults.filterChipColors(
                    containerColor = Color.Blue,
                    labelColor = Color.Yellow,
                    iconColor = Color.White,
                    disabledContainerColor = Color.LightGray,
                    disabledLabelColor = Color.Gray,
                    disabledLeadingIconColor = Color.DarkGray,
                    disabledTrailingIconColor = Color.Black,
                    selectedContainerColor = Color.Red,
                    disabledSelectedContainerColor = Color.Magenta,
                    selectedLabelColor = Color.Green,
                    selectedLeadingIconColor = Color.Yellow,
                    selectedTrailingIconColor = Color.White
                )

                FlowRow {
                    FilterChip(onClick = {}, selected = false, label = { Text("Chip 1") })
                    FilterChip(onClick = {}, selected = true, label = { Text("Chip 2") })
                    FilterChip(
                        onClick = {},
                        selected = false,
                        enabled = false,
                        label = { Text("Chip 3") },
                    )
                    FilterChip(
                        onClick = {},
                        selected = true,
                        enabled = false,
                        label = { Text("Chip 4") },
                    )
                    FilterChip(
                        onClick = {},
                        selected = false,
                        label = { Text("Chip 5") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    FilterChip(
                        onClick = {},
                        selected = true,
                        label = { Text("Chip 6") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    FilterChip(
                        onClick = {},
                        selected = false,
                        enabled = false,
                        label = { Text("Chip 7") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    FilterChip(
                        onClick = {},
                        selected = true,
                        enabled = false,
                        label = { Text("Chip 8") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    FilterChip(
                        onClick = {},
                        selected = false,
                        label = { Text("Chip 9") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    FilterChip(
                        onClick = {},
                        selected = true,
                        label = { Text("Chip 10") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    FilterChip(
                        onClick = {},
                        selected = false,
                        shape = RectangleShape,
                        label = { Text("Chip 11") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    FilterChip(
                        onClick = {},
                        selected = true,
                        shape = RectangleShape,
                        label = { Text("Chip 12") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    FilterChip(
                        onClick = {},
                        selected = false,
                        label = { Text("Chip 13") },
                        border = BorderStroke(2.dp, Color.Red),
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    FilterChip(
                        onClick = {},
                        selected = true,
                        label = { Text("Chip 14") },
                        border = BorderStroke(2.dp, Color.Red),
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    FilterChip(
                        onClick = {},
                        selected = false,
                        label = { Text("Chip 15") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        },
                        colors = colors
                    )
                    FilterChip(
                        onClick = {},
                        selected = false,
                        enabled = false,
                        label = { Text("Chip 16") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        },
                        colors = colors
                    )
                    FilterChip(
                        onClick = {},
                        selected = true,
                        label = { Text("Chip 17") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        },
                        colors = colors
                    )
                    FilterChip(
                        onClick = {},
                        selected = true,
                        enabled = false,
                        label = { Text("Chip 18") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        },
                        colors = colors
                    )
                }
            },
            template = """
                <$flowRow>
                  <$filterChip $attrSelected="false">
                    <$text $attrTemplate="$templateLabel">Chip 1</$text>
                  </$filterChip>   
                  <$filterChip $attrSelected="true">
                    <$text $attrTemplate="$templateLabel">Chip 2</$text>
                  </$filterChip>                    
                  <$filterChip $attrSelected="false" $attrEnabled="false">
                    <$text $attrTemplate="$templateLabel">Chip 3</$text>
                  </$filterChip>     
                  <$filterChip $attrSelected="true" $attrEnabled="false">
                    <$text $attrTemplate="$templateLabel">Chip 4</$text>
                  </$filterChip>                     
                  <$filterChip $attrSelected="false">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 5</$text>
                  </$filterChip> 
                  <$filterChip $attrSelected="true">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 6</$text>
                  </$filterChip>                         
                  <$filterChip $attrSelected="false" $attrEnabled="false">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 7</$text>
                  </$filterChip>    
                  <$filterChip $attrSelected="true" $attrEnabled="false">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 8</$text>
                  </$filterChip>                                                                        
                  <$filterChip $attrSelected="false">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$icon $attrImageVector="filled:ChevronRight" $attrTemplate="$templateTrailingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 9</$text>
                  </$filterChip>
                  <$filterChip $attrSelected="true">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$icon $attrImageVector="filled:ChevronRight" $attrTemplate="$templateTrailingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 10</$text>
                  </$filterChip>   
                  <$filterChip $attrShape="rect" $attrSelected="false">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$icon $attrImageVector="filled:ChevronRight" $attrTemplate="$templateTrailingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 11</$text>
                  </$filterChip>  
                  <$filterChip $attrShape="rect" $attrSelected="true">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$icon $attrImageVector="filled:ChevronRight" $attrTemplate="$templateTrailingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 12</$text>
                  </$filterChip>                     
                  <$filterChip $attrBorder="{'$attrWidth': '2', '$attrColor': '$Red'}" $attrSelected="false">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$icon $attrImageVector="filled:ChevronRight" $attrTemplate="$templateTrailingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 13</$text>
                  </$filterChip> 
                  <$filterChip $attrBorder="{'$attrWidth': '2', '$attrColor': '$Red'}" $attrSelected="true">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$icon $attrImageVector="filled:ChevronRight" $attrTemplate="$templateTrailingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 14</$text>
                  </$filterChip>   
                  <$filterChip $attrColors="$colorsForTemplate" $attrSelected="false">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$icon $attrImageVector="filled:ChevronRight" $attrTemplate="$templateTrailingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 15</$text>
                  </$filterChip>                                   
                  <$filterChip $attrColors="$colorsForTemplate" $attrSelected="false" $attrEnabled="false">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$icon $attrImageVector="filled:ChevronRight" $attrTemplate="$templateTrailingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 16</$text>
                  </$filterChip>   
                  <$filterChip $attrColors="$colorsForTemplate" $attrSelected="true">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$icon $attrImageVector="filled:ChevronRight" $attrTemplate="$templateTrailingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 17</$text>
                  </$filterChip>    
                  <$filterChip $attrColors="$colorsForTemplate" $attrSelected="true" $attrEnabled="false">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$icon $attrImageVector="filled:ChevronRight" $attrTemplate="$templateTrailingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 18</$text>
                  </$filterChip>                                                                                                
                </$flowRow>            
                """
        )
    }

    @Test
    fun elevatedFilterChipTest() {
        val colorsForTemplate = """
            {                
            '$colorAttrContainerColor': '$Blue',
            '$colorAttrLabelColor': '$Yellow',
            '$colorAttrIconColor': '$White',
            '$colorAttrDisabledContainerColor': '$LightGray',
            '$colorAttrDisabledLabelColor': '$Gray',
            '$colorAttrDisabledLeadingIconColor': '$DarkGray',
            '$colorAttrDisabledTrailingIconColor': '$Black',
            '$colorAttrSelectedContainerColor': '$Red',
            '$colorAttrDisabledSelectedContainerColor': '$Magenta',
            '$colorAttrSelectedLabelColor': '$Green',
            '$colorAttrSelectedLeadingIconColor': '$Yellow',
            '$colorAttrSelectedTrailingIconColor': '$White'                
            }
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val colors = FilterChipDefaults.elevatedFilterChipColors(
                    containerColor = Color.Blue,
                    labelColor = Color.Yellow,
                    iconColor = Color.White,
                    disabledContainerColor = Color.LightGray,
                    disabledLabelColor = Color.Gray,
                    disabledLeadingIconColor = Color.DarkGray,
                    disabledTrailingIconColor = Color.Black,
                    selectedContainerColor = Color.Red,
                    disabledSelectedContainerColor = Color.Magenta,
                    selectedLabelColor = Color.Green,
                    selectedLeadingIconColor = Color.Yellow,
                    selectedTrailingIconColor = Color.White
                )

                FlowRow {
                    ElevatedFilterChip(onClick = {}, selected = false, label = { Text("Chip 1") })
                    ElevatedFilterChip(onClick = {}, selected = true, label = { Text("Chip 2") })
                    ElevatedFilterChip(
                        onClick = {},
                        selected = false,
                        enabled = false,
                        label = { Text("Chip 3") },
                    )
                    ElevatedFilterChip(
                        onClick = {},
                        selected = true,
                        enabled = false,
                        label = { Text("Chip 4") },
                    )
                    ElevatedFilterChip(
                        onClick = {},
                        selected = false,
                        label = { Text("Chip 5") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    ElevatedFilterChip(
                        onClick = {},
                        selected = true,
                        label = { Text("Chip 6") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    ElevatedFilterChip(
                        onClick = {},
                        selected = false,
                        enabled = false,
                        label = { Text("Chip 7") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    ElevatedFilterChip(
                        onClick = {},
                        selected = true,
                        enabled = false,
                        label = { Text("Chip 8") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    ElevatedFilterChip(
                        onClick = {},
                        selected = false,
                        label = { Text("Chip 9") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    ElevatedFilterChip(
                        onClick = {},
                        selected = true,
                        label = { Text("Chip 10") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    ElevatedFilterChip(
                        onClick = {},
                        selected = false,
                        shape = RectangleShape,
                        label = { Text("Chip 11") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    ElevatedFilterChip(
                        onClick = {},
                        selected = true,
                        shape = RectangleShape,
                        label = { Text("Chip 12") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    ElevatedFilterChip(
                        onClick = {},
                        selected = false,
                        label = { Text("Chip 13") },
                        border = BorderStroke(2.dp, Color.Red),
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    ElevatedFilterChip(
                        onClick = {},
                        selected = true,
                        label = { Text("Chip 14") },
                        border = BorderStroke(2.dp, Color.Red),
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    ElevatedFilterChip(
                        onClick = {},
                        selected = false,
                        label = { Text("Chip 15") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        },
                        colors = colors
                    )
                    ElevatedFilterChip(
                        onClick = {},
                        selected = false,
                        enabled = false,
                        label = { Text("Chip 16") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        },
                        colors = colors
                    )
                    ElevatedFilterChip(
                        onClick = {},
                        selected = true,
                        label = { Text("Chip 17") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        },
                        colors = colors
                    )
                    ElevatedFilterChip(
                        onClick = {},
                        selected = true,
                        enabled = false,
                        label = { Text("Chip 18") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        },
                        colors = colors
                    )
                }
            },
            template = """
                <$flowRow>
                  <$elevatedFilterChip $attrSelected="false">
                    <$text $attrTemplate="$templateLabel">Chip 1</$text>
                  </$elevatedFilterChip>   
                  <$elevatedFilterChip $attrSelected="true">
                    <$text $attrTemplate="$templateLabel">Chip 2</$text>
                  </$elevatedFilterChip>                    
                  <$elevatedFilterChip $attrSelected="false" $attrEnabled="false">
                    <$text $attrTemplate="$templateLabel">Chip 3</$text>
                  </$elevatedFilterChip>     
                  <$elevatedFilterChip $attrSelected="true" $attrEnabled="false">
                    <$text $attrTemplate="$templateLabel">Chip 4</$text>
                  </$elevatedFilterChip>                     
                  <$elevatedFilterChip $attrSelected="false">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 5</$text>
                  </$elevatedFilterChip> 
                  <$elevatedFilterChip $attrSelected="true">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 6</$text>
                  </$elevatedFilterChip>                         
                  <$elevatedFilterChip $attrSelected="false" $attrEnabled="false">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 7</$text>
                  </$elevatedFilterChip>    
                  <$elevatedFilterChip $attrSelected="true" $attrEnabled="false">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 8</$text>
                  </$elevatedFilterChip>                                                                        
                  <$elevatedFilterChip $attrSelected="false">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$icon $attrImageVector="filled:ChevronRight" $attrTemplate="$templateTrailingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 9</$text>
                  </$elevatedFilterChip>
                  <$elevatedFilterChip $attrSelected="true">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$icon $attrImageVector="filled:ChevronRight" $attrTemplate="$templateTrailingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 10</$text>
                  </$elevatedFilterChip>   
                  <$elevatedFilterChip $attrShape="rect" $attrSelected="false">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$icon $attrImageVector="filled:ChevronRight" $attrTemplate="$templateTrailingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 11</$text>
                  </$elevatedFilterChip>  
                  <$elevatedFilterChip $attrShape="rect" $attrSelected="true">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$icon $attrImageVector="filled:ChevronRight" $attrTemplate="$templateTrailingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 12</$text>
                  </$elevatedFilterChip>                     
                  <$elevatedFilterChip $attrBorder="{'$attrWidth': '2', '$attrColor': '$Red'}" $attrSelected="false">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$icon $attrImageVector="filled:ChevronRight" $attrTemplate="$templateTrailingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 13</$text>
                  </$elevatedFilterChip> 
                  <$elevatedFilterChip $attrBorder="{'$attrWidth': '2', '$attrColor': '$Red'}" $attrSelected="true">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$icon $attrImageVector="filled:ChevronRight" $attrTemplate="$templateTrailingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 14</$text>
                  </$elevatedFilterChip>   
                  <$elevatedFilterChip $attrColors="$colorsForTemplate" $attrSelected="false">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$icon $attrImageVector="filled:ChevronRight" $attrTemplate="$templateTrailingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 15</$text>
                  </$elevatedFilterChip>                                   
                  <$elevatedFilterChip $attrColors="$colorsForTemplate" $attrSelected="false" $attrEnabled="false">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$icon $attrImageVector="filled:ChevronRight" $attrTemplate="$templateTrailingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 16</$text>
                  </$elevatedFilterChip>   
                  <$elevatedFilterChip $attrColors="$colorsForTemplate" $attrSelected="true">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$icon $attrImageVector="filled:ChevronRight" $attrTemplate="$templateTrailingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 17</$text>
                  </$elevatedFilterChip>    
                  <$elevatedFilterChip $attrColors="$colorsForTemplate" $attrSelected="true" $attrEnabled="false">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$icon $attrImageVector="filled:ChevronRight" $attrTemplate="$templateTrailingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 18</$text>
                  </$elevatedFilterChip>                                                                                                
                </$flowRow>            
                """
        )
    }


    @Test
    fun inputChipTest() {
        val colorsForTemplate = """
            {                
            '$colorAttrContainerColor': '$Blue',
            '$colorAttrLabelColor': '$Yellow',
            '$colorAttrLeadingIconColor': '$White',
            '$colorAttrTrailingIconColor': '$Red',
            '$colorAttrDisabledContainerColor': '$LightGray',
            '$colorAttrDisabledLabelColor': '$Gray',
            '$colorAttrDisabledLeadingIconColor': '$DarkGray',
            '$colorAttrDisabledTrailingIconColor': '$Black',
            '$colorAttrSelectedContainerColor': '$Red',
            '$colorAttrDisabledSelectedContainerColor': '$Magenta',
            '$colorAttrSelectedLabelColor': '$Green',
            '$colorAttrSelectedLeadingIconColor': '$Yellow',
            '$colorAttrSelectedTrailingIconColor': '$White'                
            }
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val colors = InputChipDefaults.inputChipColors(
                    containerColor = Color.Blue,
                    labelColor = Color.Yellow,
                    leadingIconColor = Color.White,
                    trailingIconColor = Color.Red,
                    disabledContainerColor = Color.LightGray,
                    disabledLabelColor = Color.Gray,
                    disabledLeadingIconColor = Color.DarkGray,
                    disabledTrailingIconColor = Color.Black,
                    selectedContainerColor = Color.Red,
                    disabledSelectedContainerColor = Color.Magenta,
                    selectedLabelColor = Color.Green,
                    selectedLeadingIconColor = Color.Yellow,
                    selectedTrailingIconColor = Color.White
                )

                FlowRow {
                    InputChip(onClick = {}, selected = false, label = { Text("Chip 1") })
                    InputChip(onClick = {}, selected = true, label = { Text("Chip 2") })
                    InputChip(
                        onClick = {},
                        selected = false,
                        enabled = false,
                        label = { Text("Chip 3") },
                    )
                    InputChip(
                        onClick = {},
                        selected = true,
                        enabled = false,
                        label = { Text("Chip 4") },
                    )
                    InputChip(
                        onClick = {},
                        selected = false,
                        label = { Text("Chip 5") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    InputChip(
                        onClick = {},
                        selected = true,
                        label = { Text("Chip 6") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    InputChip(
                        onClick = {},
                        selected = false,
                        enabled = false,
                        label = { Text("Chip 7") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    InputChip(
                        onClick = {},
                        selected = true,
                        enabled = false,
                        label = { Text("Chip 8") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                    )
                    InputChip(
                        onClick = {},
                        selected = false,
                        label = { Text("Chip 9") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    InputChip(
                        onClick = {},
                        selected = true,
                        label = { Text("Chip 10") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    InputChip(
                        onClick = {},
                        selected = false,
                        shape = RectangleShape,
                        label = { Text("Chip 11") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    InputChip(
                        onClick = {},
                        selected = true,
                        shape = RectangleShape,
                        label = { Text("Chip 12") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    InputChip(
                        onClick = {},
                        selected = false,
                        label = { Text("Chip 13") },
                        border = BorderStroke(2.dp, Color.Red),
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    InputChip(
                        onClick = {},
                        selected = true,
                        label = { Text("Chip 14") },
                        border = BorderStroke(2.dp, Color.Red),
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        }
                    )
                    InputChip(
                        onClick = {},
                        selected = false,
                        label = { Text("Chip 15") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        },
                        colors = colors
                    )
                    InputChip(
                        onClick = {},
                        selected = false,
                        enabled = false,
                        label = { Text("Chip 16") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        },
                        colors = colors
                    )
                    InputChip(
                        onClick = {},
                        selected = true,
                        label = { Text("Chip 17") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        },
                        colors = colors
                    )
                    InputChip(
                        onClick = {},
                        selected = true,
                        enabled = false,
                        label = { Text("Chip 18") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = "")
                        },
                        colors = colors
                    )
                    InputChip(
                        selected = true,
                        onClick = { },
                        label = { Text("Chip 19") },
                        avatar = {
                            Icon(
                                imageVector = Icons.Sharp.Person,
                                contentDescription = ""
                            )
                        },
                    )
                    InputChip(
                        selected = false,
                        onClick = { },
                        label = { Text("Chip 20") },
                        avatar = {
                            Icon(
                                imageVector = Icons.Sharp.PersonAdd,
                                contentDescription = ""
                            )
                        },
                    )
                    InputChip(
                        selected = true,
                        enabled = false,
                        onClick = { },
                        label = { Text("Chip 21") },
                        avatar = {
                            Icon(
                                imageVector = Icons.Sharp.Person,
                                contentDescription = ""
                            )
                        },
                    )
                    InputChip(
                        selected = false,
                        enabled = false,
                        onClick = { },
                        label = { Text("Chip 22") },
                        avatar = {
                            Icon(
                                imageVector = Icons.Sharp.PersonAdd,
                                contentDescription = ""
                            )
                        },
                    )
                    InputChip(
                        selected = true,
                        onClick = { },
                        label = { Text("Chip 23") },
                        avatar = {
                            Icon(
                                imageVector = Icons.Sharp.Person,
                                contentDescription = ""
                            )
                        },
                        colors = colors,
                    )
                    InputChip(
                        selected = false,
                        onClick = { },
                        label = { Text("Chip 24") },
                        avatar = {
                            Icon(
                                imageVector = Icons.Sharp.PersonAdd,
                                contentDescription = ""
                            )
                        },
                        colors = colors,
                    )
                    InputChip(
                        selected = true,
                        enabled = false,
                        onClick = { },
                        label = { Text("Chip 25") },
                        avatar = {
                            Icon(
                                imageVector = Icons.Sharp.Person,
                                contentDescription = ""
                            )
                        },
                        colors = colors,
                    )
                    InputChip(
                        selected = false,
                        enabled = false,
                        onClick = { },
                        label = { Text("Chip 26") },
                        avatar = {
                            Icon(
                                imageVector = Icons.Sharp.PersonAdd,
                                contentDescription = ""
                            )
                        },
                        colors = colors,
                    )
                }
            },
            template = """
                <$flowRow>
                  <$inputChip $attrSelected="false">
                    <$text $attrTemplate="$templateLabel">Chip 1</$text>
                  </$inputChip>   
                  <$inputChip $attrSelected="true">
                    <$text $attrTemplate="$templateLabel">Chip 2</$text>
                  </$inputChip>                    
                  <$inputChip $attrSelected="false" $attrEnabled="false">
                    <$text $attrTemplate="$templateLabel">Chip 3</$text>
                  </$inputChip>     
                  <$inputChip $attrSelected="true" $attrEnabled="false">
                    <$text $attrTemplate="$templateLabel">Chip 4</$text>
                  </$inputChip>                     
                  <$inputChip $attrSelected="false">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 5</$text>
                  </$inputChip> 
                  <$inputChip $attrSelected="true">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 6</$text>
                  </$inputChip>                         
                  <$inputChip $attrSelected="false" $attrEnabled="false">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 7</$text>
                  </$inputChip>    
                  <$inputChip $attrSelected="true" $attrEnabled="false">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 8</$text>
                  </$inputChip>                                                                        
                  <$inputChip $attrSelected="false">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$icon $attrImageVector="filled:ChevronRight" $attrTemplate="$templateTrailingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 9</$text>
                  </$inputChip>
                  <$inputChip $attrSelected="true">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$icon $attrImageVector="filled:ChevronRight" $attrTemplate="$templateTrailingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 10</$text>
                  </$inputChip>   
                  <$inputChip $attrShape="rect" $attrSelected="false">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$icon $attrImageVector="filled:ChevronRight" $attrTemplate="$templateTrailingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 11</$text>
                  </$inputChip>  
                  <$inputChip $attrShape="rect" $attrSelected="true">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$icon $attrImageVector="filled:ChevronRight" $attrTemplate="$templateTrailingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 12</$text>
                  </$inputChip>                     
                  <$inputChip $attrBorder="{'$attrWidth': '2', '$attrColor': '$Red'}" $attrSelected="false">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$icon $attrImageVector="filled:ChevronRight" $attrTemplate="$templateTrailingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 13</$text>
                  </$inputChip> 
                  <$inputChip $attrBorder="{'$attrWidth': '2', '$attrColor': '$Red'}" $attrSelected="true">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$icon $attrImageVector="filled:ChevronRight" $attrTemplate="$templateTrailingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 14</$text>
                  </$inputChip>   
                  <$inputChip $attrColors="$colorsForTemplate" $attrSelected="false">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$icon $attrImageVector="filled:ChevronRight" $attrTemplate="$templateTrailingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 15</$text>
                  </$inputChip>                                   
                  <$inputChip $attrColors="$colorsForTemplate" $attrSelected="false" $attrEnabled="false">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$icon $attrImageVector="filled:ChevronRight" $attrTemplate="$templateTrailingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 16</$text>
                  </$inputChip>   
                  <$inputChip $attrColors="$colorsForTemplate" $attrSelected="true">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$icon $attrImageVector="filled:ChevronRight" $attrTemplate="$templateTrailingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 17</$text>
                  </$inputChip>    
                  <$inputChip $attrColors="$colorsForTemplate" $attrSelected="true" $attrEnabled="false">
                    <$icon $attrImageVector="filled:Check" $attrTemplate="$templateLeadingIcon"/>
                    <$icon $attrImageVector="filled:ChevronRight" $attrTemplate="$templateTrailingIcon"/>
                    <$text $attrTemplate="$templateLabel">Chip 18</$text>
                  </$inputChip>     
                  <$inputChip $attrSelected="true">
                    <$icon $attrImageVector="$sharp:Person" $attrTemplate="$templateAvatar"/>
                    <$text $attrTemplate="$templateLabel">Chip 19</$text>
                  </$inputChip>    
                  <$inputChip $attrSelected="false">
                    <$icon $attrImageVector="$sharp:PersonAdd" $attrTemplate="$templateAvatar"/>
                    <$text $attrTemplate="$templateLabel">Chip 20</$text>
                  </$inputChip>    
                  <$inputChip $attrSelected="true" $attrEnabled="false">
                    <$icon $attrImageVector="$sharp:Person" $attrTemplate="$templateAvatar"/>
                    <$text $attrTemplate="$templateLabel">Chip 21</$text>
                  </$inputChip>    
                  <$inputChip $attrSelected="false" $attrEnabled="false">
                    <$icon $attrImageVector="$sharp:PersonAdd" $attrTemplate="$templateAvatar"/>
                    <$text $attrTemplate="$templateLabel">Chip 22</$text>
                  </$inputChip>    
                  <$inputChip $attrColors="$colorsForTemplate" $attrSelected="true" >
                    <$icon $attrImageVector="$sharp:Person" $attrTemplate="$templateAvatar"/>
                    <$text $attrTemplate="$templateLabel">Chip 23</$text>
                  </$inputChip>    
                  <$inputChip $attrColors="$colorsForTemplate" $attrSelected="false">
                    <$icon $attrImageVector="$sharp:PersonAdd" $attrTemplate="$templateAvatar"/>
                    <$text $attrTemplate="$templateLabel">Chip 24</$text>
                  </$inputChip>    
                  <$inputChip $attrColors="$colorsForTemplate" $attrSelected="true" $attrEnabled="false">
                    <$icon $attrImageVector="$sharp:Person" $attrTemplate="$templateAvatar"/>
                    <$text $attrTemplate="$templateLabel">Chip 25</$text>
                  </$inputChip>    
                  <$inputChip $attrColors="$colorsForTemplate" $attrSelected="false" $attrEnabled="false">
                    <$icon $attrImageVector="$sharp:PersonAdd" $attrTemplate="$templateAvatar"/>
                    <$text $attrTemplate="$templateLabel">Chip 26</$text>
                  </$inputChip>                                                                                                                                                             
                </$flowRow>            
                """
        )
    }
}