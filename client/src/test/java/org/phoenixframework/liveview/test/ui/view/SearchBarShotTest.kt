package org.phoenixframework.liveview.test.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import org.junit.Test
import org.phoenixframework.liveview.constants.Attrs.attrActive
import org.phoenixframework.liveview.constants.Attrs.attrColors
import org.phoenixframework.liveview.constants.Attrs.attrEnabled
import org.phoenixframework.liveview.constants.Attrs.attrImageVector
import org.phoenixframework.liveview.constants.Attrs.attrQuery
import org.phoenixframework.liveview.constants.Attrs.attrShadowElevation
import org.phoenixframework.liveview.constants.Attrs.attrShape
import org.phoenixframework.liveview.constants.Attrs.attrTemplate
import org.phoenixframework.liveview.constants.Attrs.attrTonalElevation
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrContainerColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrDividerColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrInputFieldColors
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrUnfocusedLeadingIconColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrUnfocusedPlaceholderColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrUnfocusedTrailingIconColor
import org.phoenixframework.liveview.constants.ComposableTypes.column
import org.phoenixframework.liveview.constants.ComposableTypes.dockedSearchBar
import org.phoenixframework.liveview.constants.ComposableTypes.icon
import org.phoenixframework.liveview.constants.ComposableTypes.searchBar
import org.phoenixframework.liveview.constants.ComposableTypes.text
import org.phoenixframework.liveview.constants.IconPrefixValues.filled
import org.phoenixframework.liveview.constants.ShapeValues.rectangle
import org.phoenixframework.liveview.constants.SystemColorValues.Blue
import org.phoenixframework.liveview.constants.SystemColorValues.Gray
import org.phoenixframework.liveview.constants.SystemColorValues.LightGray
import org.phoenixframework.liveview.constants.SystemColorValues.White
import org.phoenixframework.liveview.constants.SystemColorValues.Yellow
import org.phoenixframework.liveview.constants.Templates.templateContent
import org.phoenixframework.liveview.constants.Templates.templateLeadingIcon
import org.phoenixframework.liveview.constants.Templates.templatePlaceholder
import org.phoenixframework.liveview.constants.Templates.templateTrailingIcon
import org.phoenixframework.liveview.test.base.LiveViewComposableTest

@OptIn(ExperimentalMaterial3Api::class)
class SearchBarShotTest : LiveViewComposableTest() {

    @Test
    fun simpleSearchBarTest() {
        val colorsForTemplate = """
            {
            '$colorAttrContainerColor': '$Blue',
            '$colorAttrDividerColor': '$Yellow',
            '$colorAttrInputFieldColors': {
              '$colorAttrUnfocusedPlaceholderColor': '$White',
              '$colorAttrUnfocusedLeadingIconColor': '$LightGray',
              '$colorAttrUnfocusedTrailingIconColor': '$Gray'        
              }
            }
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val colors = SearchBarDefaults.colors(
                    containerColor = Color.Blue,
                    dividerColor = Color.Yellow,
                    inputFieldColors = TextFieldDefaults.colors(
                        unfocusedPlaceholderColor = Color.White,
                        unfocusedLeadingIconColor = Color.LightGray,
                        unfocusedTrailingIconColor = Color.Gray
                    )
                )
                Column {
                    SearchBar(
                        query = "Text to search",
                        onQueryChange = {},
                        onSearch = {},
                        active = false,
                        onActiveChange = {}
                    ) {
                        Text(text = "Search content")
                    }
                    SearchBar(
                        query = "",
                        onQueryChange = {},
                        onSearch = {},
                        active = false,
                        onActiveChange = {},
                        placeholder = {
                            Text(text = "Placeholder")
                        }
                    ) {
                        Text(text = "Search content")
                    }
                    SearchBar(
                        query = "",
                        onQueryChange = {},
                        onSearch = {},
                        active = false,
                        onActiveChange = {},
                        placeholder = {
                            Text(text = "Placeholder")
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Search, contentDescription = "")
                        }
                    ) {
                        Text(text = "Search content")
                    }
                    SearchBar(
                        query = "",
                        onQueryChange = {},
                        onSearch = {},
                        active = false,
                        onActiveChange = {},
                        placeholder = {
                            Text(text = "Placeholder")
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Search, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.Clear, contentDescription = "")
                        }
                    ) {
                        Text(text = "Search content")
                    }
                    SearchBar(
                        query = "",
                        onQueryChange = {},
                        onSearch = {},
                        active = false,
                        onActiveChange = {},
                        placeholder = {
                            Text(text = "Placeholder")
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Search, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.Clear, contentDescription = "")
                        },
                        enabled = false,
                    ) {
                        Text(text = "Search content")
                    }
                    SearchBar(
                        query = "",
                        onQueryChange = {},
                        onSearch = {},
                        active = false,
                        onActiveChange = {},
                        placeholder = {
                            Text(text = "Placeholder")
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Search, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.Clear, contentDescription = "")
                        },
                        tonalElevation = 8.dp,
                        shadowElevation = 12.dp
                    ) {
                        Text(text = "Search content")
                    }
                    SearchBar(
                        query = "",
                        onQueryChange = {},
                        onSearch = {},
                        active = false,
                        onActiveChange = {},
                        placeholder = {
                            Text(text = "Placeholder")
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Search, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.Clear, contentDescription = "")
                        },
                        colors = colors
                    ) {
                        Text(text = "Search content")
                    }
                    SearchBar(
                        query = "",
                        onQueryChange = {},
                        onSearch = {},
                        active = false,
                        onActiveChange = {},
                        placeholder = {
                            Text(text = "Placeholder")
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Search, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.Clear, contentDescription = "")
                        },
                        colors = colors,
                        shape = RectangleShape,
                    ) {
                        Text(text = "Search content")
                    }
                }
            },
            template = """
                <$column>
                  <$searchBar $attrQuery="Text to search" 
                    $attrActive="false">
                    <$text>Search content</$text>
                  </$searchBar>
                  <$searchBar $attrQuery="" 
                    $attrActive="false">
                    <$text $attrTemplate="$templatePlaceholder">Placeholder</$text>
                    <$text $attrTemplate="$templateContent">Search content</$text>
                  </$searchBar>                    
                  <$searchBar $attrQuery="" 
                    $attrActive="false">
                    <$icon $attrTemplate="$templateLeadingIcon" $attrImageVector="$filled:Search"/>
                    <$text $attrTemplate="$templatePlaceholder">Placeholder</$text>
                    <$text $attrTemplate="$templateContent">Search content</$text>
                  </$searchBar>     
                  <$searchBar $attrQuery="" 
                    $attrActive="false">
                    <$icon $attrTemplate="$templateLeadingIcon" $attrImageVector="$filled:Search"/>
                    <$icon $attrTemplate="$templateTrailingIcon" $attrImageVector="$filled:Clear"/>
                    <$text $attrTemplate="$templatePlaceholder">Placeholder</$text>
                    <$text $attrTemplate="$templateContent">Search content</$text>
                  </$searchBar>   
                  <$searchBar $attrQuery="" $attrEnabled="false"
                    $attrActive="false">
                    <$icon $attrTemplate="$templateLeadingIcon" $attrImageVector="$filled:Search"/>
                    <$icon $attrTemplate="$templateTrailingIcon" $attrImageVector="$filled:Clear"/>
                    <$text $attrTemplate="$templatePlaceholder">Placeholder</$text>
                    <$text $attrTemplate="$templateContent">Search content</$text>
                  </$searchBar>                   
                  <$searchBar $attrQuery="" $attrTonalElevation="8" $attrShadowElevation="12"
                    $attrActive="false">
                    <$icon $attrTemplate="$templateLeadingIcon" $attrImageVector="$filled:Search"/>
                    <$icon $attrTemplate="$templateTrailingIcon" $attrImageVector="$filled:Clear"/>
                    <$text $attrTemplate="$templatePlaceholder">Placeholder</$text>
                    <$text $attrTemplate="$templateContent">Search content</$text>
                  </$searchBar>                   
                  <$searchBar $attrQuery="" $attrColors="$colorsForTemplate"
                    $attrActive="false">
                    <$icon $attrTemplate="$templateLeadingIcon" $attrImageVector="$filled:Search"/>
                    <$icon $attrTemplate="$templateTrailingIcon" $attrImageVector="filled:Clear"/>
                    <$text $attrTemplate="$templatePlaceholder">Placeholder</$text>
                    <$text $attrTemplate="$templateContent">Search content</$text>
                  </$searchBar>
                  <$searchBar $attrQuery="" $attrColors="$colorsForTemplate" $attrShape="$rectangle"
                    $attrActive="false">
                    <$icon $attrTemplate="$templateLeadingIcon" $attrImageVector="filled:Search"/>
                    <$icon $attrTemplate="$templateTrailingIcon" $attrImageVector="filled:Clear"/>
                    <$text $attrTemplate="$templatePlaceholder">Placeholder</$text>
                    <$text $attrTemplate="$templateContent">Search content</$text>
                  </$searchBar>                   
                </$column>  
                """
        )
    }

    @Test
    fun activeSearchBarTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                SearchBar(
                    query = "",
                    onQueryChange = {},
                    onSearch = {},
                    active = true,
                    onActiveChange = {},
                    placeholder = {
                        Text(text = "Placeholder")
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Filled.Search, contentDescription = "")
                    },
                    trailingIcon = {
                        Icon(imageVector = Icons.Filled.Clear, contentDescription = "")
                    },
                ) {
                    Text(text = "Search content")
                }
            },
            template = """
                <$searchBar $attrQuery=""
                  $attrActive="true">
                  <$icon $attrTemplate="$templateLeadingIcon" $attrImageVector="filled:Search"/>
                  <$icon $attrTemplate="$templateTrailingIcon" $attrImageVector="filled:Clear"/>
                  <$text $attrTemplate="$templatePlaceholder">Placeholder</$text>
                  <$text $attrTemplate="$templateContent">Search content</$text>
                </$searchBar>             
                """
        )
    }

    @Test
    fun activeSearchBarWithCustomColorsTest() {
        val colorsForTemplate = """
            {
            '$colorAttrContainerColor': '$Blue',
            '$colorAttrDividerColor': '$Yellow',
            '$colorAttrInputFieldColors': {
              '$colorAttrUnfocusedPlaceholderColor': '$White',
              '$colorAttrUnfocusedLeadingIconColor': '$LightGray',
              '$colorAttrUnfocusedTrailingIconColor': '$Gray'        
              }
            }
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val colors = SearchBarDefaults.colors(
                    containerColor = Color.Blue,
                    dividerColor = Color.Yellow,
                    inputFieldColors = TextFieldDefaults.colors(
                        unfocusedPlaceholderColor = Color.White,
                        unfocusedLeadingIconColor = Color.LightGray,
                        unfocusedTrailingIconColor = Color.Gray
                    )
                )
                SearchBar(
                    query = "",
                    onQueryChange = {},
                    onSearch = {},
                    active = true,
                    onActiveChange = {},
                    placeholder = {
                        Text(text = "Placeholder")
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Filled.Search, contentDescription = "")
                    },
                    trailingIcon = {
                        Icon(imageVector = Icons.Filled.Clear, contentDescription = "")
                    },
                    colors = colors,
                ) {
                    Text(text = "Search content")
                }
            },
            template = """
                <$searchBar $attrQuery="" $attrColors="$colorsForTemplate"
                  $attrActive="true">
                  <$icon $attrTemplate="$templateLeadingIcon" $attrImageVector="filled:Search"/>
                  <$icon $attrTemplate="$templateTrailingIcon" $attrImageVector="filled:Clear"/>
                  <$text $attrTemplate="$templatePlaceholder">Placeholder</$text>
                  <$text $attrTemplate="$templateContent">Search content</$text>
                </$searchBar>             
                """
        )
    }

    @Test
    fun simpleDockedSearchBarTest() {
        val colorsForTemplate = """
            {
            '$colorAttrContainerColor': '$Blue',
            '$colorAttrDividerColor': '$Yellow',
            '$colorAttrInputFieldColors': {
              '$colorAttrUnfocusedPlaceholderColor': '$White',
              '$colorAttrUnfocusedLeadingIconColor': '$LightGray',
              '$colorAttrUnfocusedTrailingIconColor': '$Gray'        
              }
            }
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val colors = SearchBarDefaults.colors(
                    containerColor = Color.Blue,
                    dividerColor = Color.Yellow,
                    inputFieldColors = TextFieldDefaults.colors(
                        unfocusedPlaceholderColor = Color.White,
                        unfocusedLeadingIconColor = Color.LightGray,
                        unfocusedTrailingIconColor = Color.Gray
                    )
                )
                Column {
                    DockedSearchBar(
                        query = "Text to search",
                        onQueryChange = {},
                        onSearch = {},
                        active = false,
                        onActiveChange = {}
                    ) {
                        Text(text = "Search content")
                    }
                    DockedSearchBar(
                        query = "",
                        onQueryChange = {},
                        onSearch = {},
                        active = false,
                        onActiveChange = {},
                        placeholder = {
                            Text(text = "Placeholder")
                        }
                    ) {
                        Text(text = "Search content")
                    }
                    DockedSearchBar(
                        query = "",
                        onQueryChange = {},
                        onSearch = {},
                        active = false,
                        onActiveChange = {},
                        placeholder = {
                            Text(text = "Placeholder")
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Search, contentDescription = "")
                        }
                    ) {
                        Text(text = "Search content")
                    }
                    DockedSearchBar(
                        query = "",
                        onQueryChange = {},
                        onSearch = {},
                        active = false,
                        onActiveChange = {},
                        placeholder = {
                            Text(text = "Placeholder")
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Search, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.Clear, contentDescription = "")
                        }
                    ) {
                        Text(text = "Search content")
                    }
                    DockedSearchBar(
                        query = "",
                        onQueryChange = {},
                        onSearch = {},
                        active = false,
                        onActiveChange = {},
                        placeholder = {
                            Text(text = "Placeholder")
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Search, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.Clear, contentDescription = "")
                        },
                        enabled = false,
                    ) {
                        Text(text = "Search content")
                    }
                    DockedSearchBar(
                        query = "",
                        onQueryChange = {},
                        onSearch = {},
                        active = false,
                        onActiveChange = {},
                        placeholder = {
                            Text(text = "Placeholder")
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Search, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.Clear, contentDescription = "")
                        },
                        tonalElevation = 8.dp,
                        shadowElevation = 12.dp
                    ) {
                        Text(text = "Search content")
                    }
                    DockedSearchBar(
                        query = "",
                        onQueryChange = {},
                        onSearch = {},
                        active = false,
                        onActiveChange = {},
                        placeholder = {
                            Text(text = "Placeholder")
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Search, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.Clear, contentDescription = "")
                        },
                        colors = colors
                    ) {
                        Text(text = "Search content")
                    }
                    DockedSearchBar(
                        query = "",
                        onQueryChange = {},
                        onSearch = {},
                        active = false,
                        onActiveChange = {},
                        placeholder = {
                            Text(text = "Placeholder")
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Search, contentDescription = "")
                        },
                        trailingIcon = {
                            Icon(imageVector = Icons.Filled.Clear, contentDescription = "")
                        },
                        colors = colors,
                        shape = RectangleShape,
                    ) {
                        Text(text = "Search content")
                    }
                }
            },
            template = """
                <$column>
                  <$dockedSearchBar $attrQuery="Text to search" $attrActive="false">
                    <$text>Search content</$text>
                  </$dockedSearchBar>
                  <$dockedSearchBar $attrQuery="" $attrActive="false">
                    <$text $attrTemplate="$templatePlaceholder">Placeholder</$text>
                    <$text $attrTemplate="$templateContent">Search content</$text>
                  </$dockedSearchBar>                    
                  <$dockedSearchBar $attrQuery="" $attrActive="false">
                    <$icon $attrTemplate="$templateLeadingIcon" $attrImageVector="filled:Search"/>
                    <$text $attrTemplate="$templatePlaceholder">Placeholder</$text>
                    <$text $attrTemplate="$templateContent">Search content</$text>
                  </$dockedSearchBar>     
                  <$dockedSearchBar $attrQuery="" $attrActive="false">
                    <$icon $attrTemplate="$templateLeadingIcon" $attrImageVector="filled:Search"/>
                    <$icon $attrTemplate="$templateTrailingIcon" $attrImageVector="filled:Clear"/>
                    <$text $attrTemplate="$templatePlaceholder">Placeholder</$text>
                    <$text $attrTemplate="$templateContent">Search content</$text>
                  </$dockedSearchBar>   
                  <$dockedSearchBar $attrQuery="" $attrEnabled="false" $attrActive="false">
                    <$icon $attrTemplate="$templateLeadingIcon" $attrImageVector="filled:Search"/>
                    <$icon $attrTemplate="$templateTrailingIcon" $attrImageVector="filled:Clear"/>
                    <$text $attrTemplate="$templatePlaceholder">Placeholder</$text>
                    <$text $attrTemplate="$templateContent">Search content</$text>
                  </$dockedSearchBar>                   
                  <$dockedSearchBar $attrQuery="" $attrTonalElevation="8" $attrShadowElevation="12" $attrActive="false">
                    <$icon $attrTemplate="$templateLeadingIcon" $attrImageVector="filled:Search"/>
                    <$icon $attrTemplate="$templateTrailingIcon" $attrImageVector="filled:Clear"/>
                    <$text $attrTemplate="$templatePlaceholder">Placeholder</$text>
                    <$text $attrTemplate="$templateContent">Search content</$text>
                  </$dockedSearchBar>                   
                  <$dockedSearchBar $attrQuery="" $attrColors="$colorsForTemplate" $attrActive="false">
                    <$icon $attrTemplate="$templateLeadingIcon" $attrImageVector="filled:Search"/>
                    <$icon $attrTemplate="$templateTrailingIcon" $attrImageVector="filled:Clear"/>
                    <$text $attrTemplate="$templatePlaceholder">Placeholder</$text>
                    <$text $attrTemplate="$templateContent">Search content</$text>
                  </$dockedSearchBar>
                  <$dockedSearchBar $attrQuery="" $attrColors="$colorsForTemplate" $attrShape="$rectangle" $attrActive="false">
                    <$icon $attrTemplate="$templateLeadingIcon" $attrImageVector="filled:Search"/>
                    <$icon $attrTemplate="$templateTrailingIcon" $attrImageVector="filled:Clear"/>
                    <$text $attrTemplate="$templatePlaceholder">Placeholder</$text>
                    <$text $attrTemplate="$templateContent">Search content</$text>
                  </$dockedSearchBar>                   
                </$column>  
                """
        )
    }

    @Test
    fun activeDockedSearchBarTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                DockedSearchBar(
                    query = "",
                    onQueryChange = {},
                    onSearch = {},
                    active = true,
                    onActiveChange = {},
                    placeholder = {
                        Text(text = "Placeholder")
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Filled.Search, contentDescription = "")
                    },
                    trailingIcon = {
                        Icon(imageVector = Icons.Filled.Clear, contentDescription = "")
                    },
                ) {
                    Text(text = "Search content")
                }
            },
            template = """
                <$dockedSearchBar $attrQuery="" $attrActive="true">
                  <$icon $attrTemplate="$templateLeadingIcon" $attrImageVector="filled:Search"/>
                  <$icon $attrTemplate="$templateTrailingIcon" $attrImageVector="filled:Clear"/>
                  <$text $attrTemplate="$templatePlaceholder">Placeholder</$text>
                  <$text $attrTemplate="$templateContent">Search content</$text>
                </$dockedSearchBar>             
                """
        )
    }

    @Test
    fun activeDockedSearchBarWithCustomColorsTest() {
        val colorsForTemplate = """
            {
            '$colorAttrContainerColor': '$Blue',
            '$colorAttrDividerColor': '$Yellow',
            '$colorAttrInputFieldColors': {
              '$colorAttrUnfocusedPlaceholderColor': '$White',
              '$colorAttrUnfocusedLeadingIconColor': '$LightGray',
              '$colorAttrUnfocusedTrailingIconColor': '$Gray'        
              }
            }
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val colors = SearchBarDefaults.colors(
                    containerColor = Color.Blue,
                    dividerColor = Color.Yellow,
                    inputFieldColors = TextFieldDefaults.colors(
                        unfocusedPlaceholderColor = Color.White,
                        unfocusedLeadingIconColor = Color.LightGray,
                        unfocusedTrailingIconColor = Color.Gray
                    )
                )
                DockedSearchBar(
                    query = "",
                    onQueryChange = {},
                    onSearch = {},
                    active = true,
                    onActiveChange = {},
                    placeholder = {
                        Text(text = "Placeholder")
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Filled.Search, contentDescription = "")
                    },
                    trailingIcon = {
                        Icon(imageVector = Icons.Filled.Clear, contentDescription = "")
                    },
                    colors = colors,
                ) {
                    Text(text = "Search content")
                }
            },
            template = """
                <$dockedSearchBar $attrQuery="" $attrColors="$colorsForTemplate" $attrActive="true">
                  <$icon $attrTemplate="$templateLeadingIcon" $attrImageVector="filled:Search"/>
                  <$icon $attrTemplate="$templateTrailingIcon" $attrImageVector="filled:Clear"/>
                  <$text $attrTemplate="$templatePlaceholder">Placeholder</$text>
                  <$text $attrTemplate="$templateContent">Search content</$text>
                </$dockedSearchBar>             
                """
        )
    }
}