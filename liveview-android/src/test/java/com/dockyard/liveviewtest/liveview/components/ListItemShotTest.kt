package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test
import org.phoenixframework.liveview.data.constants.Attrs.attrColors
import org.phoenixframework.liveview.data.constants.Attrs.attrImageVector
import org.phoenixframework.liveview.data.constants.Attrs.attrShadowElevation
import org.phoenixframework.liveview.data.constants.Attrs.attrSize
import org.phoenixframework.liveview.data.constants.Attrs.attrTemplate
import org.phoenixframework.liveview.data.constants.Attrs.attrTonalElevation
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrHeadlineColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrLeadingIconColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrOverlineColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrSupportingColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrTrailingIconColor
import org.phoenixframework.liveview.data.constants.SizeValues.fill
import org.phoenixframework.liveview.data.constants.SystemColorValues.Blue
import org.phoenixframework.liveview.data.constants.SystemColorValues.Cyan
import org.phoenixframework.liveview.data.constants.SystemColorValues.LightGray
import org.phoenixframework.liveview.data.constants.SystemColorValues.Magenta
import org.phoenixframework.liveview.data.constants.SystemColorValues.Red
import org.phoenixframework.liveview.data.constants.SystemColorValues.Yellow
import org.phoenixframework.liveview.data.constants.Templates.templateHeadlineContent
import org.phoenixframework.liveview.data.constants.Templates.templateLeadingContent
import org.phoenixframework.liveview.data.constants.Templates.templateOverlineContent
import org.phoenixframework.liveview.data.constants.Templates.templateSupportingContent
import org.phoenixframework.liveview.data.constants.Templates.templateTrailingContent
import org.phoenixframework.liveview.domain.base.ComposableTypes.column
import org.phoenixframework.liveview.domain.base.ComposableTypes.icon
import org.phoenixframework.liveview.domain.base.ComposableTypes.listItem
import org.phoenixframework.liveview.domain.base.ComposableTypes.text

class ListItemShotTest : LiveViewComposableTest() {
    @Test
    fun simpleListItemTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Column(Modifier.fillMaxSize()) {
                    ListItem(
                        headlineContent = { Text(text = "Headline") }
                    )
                    ListItem(
                        headlineContent = { Text(text = "Headline") },
                        tonalElevation = 8.dp,
                        shadowElevation = 8.dp,
                    )
                    ListItem(
                        headlineContent = { Text(text = "Headline") },
                        leadingContent = {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = ""
                            )
                        },
                        trailingContent = {
                            Icon(
                                imageVector = Icons.Filled.ChevronRight,
                                contentDescription = ""
                            )
                        },
                    )
                    ListItem(
                        headlineContent = { Text(text = "Headline") },
                        overlineContent = { Text(text = "Overline") },
                    )
                    ListItem(
                        headlineContent = { Text(text = "Headline") },
                        overlineContent = { Text(text = "Overline") },
                        leadingContent = {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = ""
                            )
                        },
                        trailingContent = {
                            Icon(
                                imageVector = Icons.Filled.ChevronRight,
                                contentDescription = ""
                            )
                        },
                    )
                    ListItem(
                        headlineContent = { Text(text = "Headline") },
                        overlineContent = { Text(text = "Overline") },
                        supportingContent = { Text(text = "Supporting") },
                    )
                    ListItem(
                        headlineContent = { Text(text = "Headline") },
                        overlineContent = { Text(text = "Overline") },
                        supportingContent = { Text(text = "Supporting") },
                        leadingContent = {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = ""
                            )
                        },
                    )
                    ListItem(
                        headlineContent = { Text(text = "Headline") },
                        overlineContent = { Text(text = "Overline") },
                        supportingContent = { Text(text = "Supporting") },
                        leadingContent = {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = ""
                            )
                        },
                        trailingContent = {
                            Icon(
                                imageVector = Icons.Filled.ChevronRight,
                                contentDescription = ""
                            )
                        },
                    )
                }
            },
            template = """
                <$column $attrSize="$fill">
                  <$listItem>
                    <$text $attrTemplate="$templateHeadlineContent">Headline</$text>
                  </$listItem>
                  <$listItem $attrTonalElevation="8" $attrShadowElevation="8" >
                    <$text $attrTemplate="$templateHeadlineContent">Headline</$text>
                  </$listItem>                  
                  <$listItem>
                    <$text $attrTemplate="$templateHeadlineContent">Headline</$text>
                    <$icon $attrTemplate="$templateLeadingContent" $attrImageVector="filled:Add" />
                    <$icon $attrTemplate="$templateTrailingContent" $attrImageVector="filled:ChevronRight" />
                  </$listItem>                  
                  <$listItem>
                    <$text $attrTemplate="$templateHeadlineContent">Headline</$text>
                    <$text $attrTemplate="$templateOverlineContent">Overline</$text>
                  </$listItem>   
                  <$listItem>
                    <$text $attrTemplate="$templateHeadlineContent">Headline</$text>
                    <$text $attrTemplate="$templateOverlineContent">Overline</$text>
                    <$icon $attrTemplate="$templateLeadingContent" $attrImageVector="filled:Add" />
                    <$icon $attrTemplate="$templateTrailingContent" $attrImageVector="filled:ChevronRight" />
                  </$listItem> 
                  <$listItem>
                    <$text $attrTemplate="$templateHeadlineContent">Headline</$text>
                    <$text $attrTemplate="$templateOverlineContent">Overline</$text>
                    <$text $attrTemplate="$templateSupportingContent">Supporting</$text>
                  </$listItem>  
                  <$listItem>
                    <$text $attrTemplate="$templateHeadlineContent">Headline</$text>
                    <$text $attrTemplate="$templateOverlineContent">Overline</$text>
                    <$text $attrTemplate="$templateSupportingContent">Supporting</$text>
                    <$icon $attrTemplate="$templateLeadingContent" $attrImageVector="filled:Add" />
                  </$listItem>  
                  <$listItem>
                    <$text $attrTemplate="$templateHeadlineContent">Headline</$text>
                    <$text $attrTemplate="$templateOverlineContent">Overline</$text>
                    <$text $attrTemplate="$templateSupportingContent">Supporting</$text>
                    <$icon $attrTemplate="$templateLeadingContent" $attrImageVector="filled:Add" />
                    <$icon $attrTemplate="$templateTrailingContent" $attrImageVector="filled:ChevronRight" />
                  </$listItem>                                                                   
                </$column>
                """
        )
    }

    @Test
    fun listItemWithCustomColorsTest() {
        val colorsForTemplate = """
            {
            '$colorAttrContainerColor': '$LightGray',
            '$colorAttrHeadlineColor': '$Blue',
            '$colorAttrLeadingIconColor': '$Red',
            '$colorAttrOverlineColor': '$Yellow',
            '$colorAttrSupportingColor': '$Cyan',
            '$colorAttrTrailingIconColor': '$Magenta'
            }
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val colors = ListItemDefaults.colors(
                    containerColor = Color.LightGray,
                    headlineColor = Color.Blue,
                    leadingIconColor = Color.Red,
                    overlineColor = Color.Yellow,
                    supportingColor = Color.Cyan,
                    trailingIconColor = Color.Magenta,
                )
                Column(Modifier.fillMaxSize()) {
                    ListItem(
                        headlineContent = { Text(text = "Headline") },
                        colors = colors,
                    )
                    ListItem(
                        headlineContent = { Text(text = "Headline") },
                        leadingContent = {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = ""
                            )
                        },
                        trailingContent = {
                            Icon(
                                imageVector = Icons.Filled.ChevronRight,
                                contentDescription = ""
                            )
                        },
                        colors = colors,
                    )
                    ListItem(
                        headlineContent = { Text(text = "Headline") },
                        overlineContent = { Text(text = "Overline") },
                        colors = colors,
                    )
                    ListItem(
                        headlineContent = { Text(text = "Headline") },
                        overlineContent = { Text(text = "Overline") },
                        leadingContent = {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = ""
                            )
                        },
                        trailingContent = {
                            Icon(
                                imageVector = Icons.Filled.ChevronRight,
                                contentDescription = ""
                            )
                        },
                        colors = colors,
                    )
                    ListItem(
                        headlineContent = { Text(text = "Headline") },
                        overlineContent = { Text(text = "Overline") },
                        supportingContent = { Text(text = "Supporting") },
                        colors = colors,
                    )
                    ListItem(
                        headlineContent = { Text(text = "Headline") },
                        overlineContent = { Text(text = "Overline") },
                        supportingContent = { Text(text = "Supporting") },
                        leadingContent = {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = ""
                            )
                        },
                        colors = colors,
                    )
                    ListItem(
                        headlineContent = { Text(text = "Headline") },
                        overlineContent = { Text(text = "Overline") },
                        supportingContent = { Text(text = "Supporting") },
                        leadingContent = {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = ""
                            )
                        },
                        trailingContent = {
                            Icon(
                                imageVector = Icons.Filled.ChevronRight,
                                contentDescription = ""
                            )
                        },
                        colors = colors,
                    )
                }
            },
            template = """
                <$column $attrSize="$fill">
                  <$listItem $attrColors="$colorsForTemplate">
                    <$text $attrTemplate="$templateHeadlineContent">Headline</$text>
                  </$listItem>                 
                  <$listItem $attrColors="$colorsForTemplate">
                    <$text $attrTemplate="$templateHeadlineContent">Headline</$text>
                    <$icon $attrTemplate="$templateLeadingContent" $attrImageVector="filled:Add" />
                    <$icon $attrTemplate="$templateTrailingContent" $attrImageVector="filled:ChevronRight" />
                  </$listItem>                  
                  <$listItem $attrColors="$colorsForTemplate">
                    <$text $attrTemplate="$templateHeadlineContent">Headline</$text>
                    <$text $attrTemplate="$templateOverlineContent">Overline</$text>
                  </$listItem>   
                  <$listItem $attrColors="$colorsForTemplate">
                    <$text $attrTemplate="$templateHeadlineContent">Headline</$text>
                    <$text $attrTemplate="$templateOverlineContent">Overline</$text>
                    <$icon $attrTemplate="$templateLeadingContent" $attrImageVector="filled:Add" />
                    <$icon $attrTemplate="$templateTrailingContent" $attrImageVector="filled:ChevronRight" />
                  </$listItem> 
                  <$listItem $attrColors="$colorsForTemplate">
                    <$text $attrTemplate="$templateHeadlineContent">Headline</$text>
                    <$text $attrTemplate="$templateOverlineContent">Overline</$text>
                    <$text $attrTemplate="$templateSupportingContent">Supporting</$text>
                  </$listItem>  
                  <$listItem $attrColors="$colorsForTemplate">
                    <$text $attrTemplate="$templateHeadlineContent">Headline</$text>
                    <$text $attrTemplate="$templateOverlineContent">Overline</$text>
                    <$text $attrTemplate="$templateSupportingContent">Supporting</$text>
                    <$icon $attrTemplate="$templateLeadingContent" $attrImageVector="filled:Add" />
                  </$listItem>  
                  <$listItem $attrColors="$colorsForTemplate">
                    <$text $attrTemplate="$templateHeadlineContent">Headline</$text>
                    <$text $attrTemplate="$templateOverlineContent">Overline</$text>
                    <$text $attrTemplate="$templateSupportingContent">Supporting</$text>
                    <$icon $attrTemplate="$templateLeadingContent" $attrImageVector="filled:Add" />
                    <$icon $attrTemplate="$templateTrailingContent" $attrImageVector="filled:ChevronRight" />
                  </$listItem>                                                                   
                </$column>
                """
        )
    }
}