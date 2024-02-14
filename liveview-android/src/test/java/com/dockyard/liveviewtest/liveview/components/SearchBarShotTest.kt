package com.dockyard.liveviewtest.liveview.components

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
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test
import org.phoenixframework.liveview.data.constants.Attrs.attrActive
import org.phoenixframework.liveview.data.constants.Attrs.attrColors
import org.phoenixframework.liveview.data.constants.Attrs.attrEnabled
import org.phoenixframework.liveview.data.constants.Attrs.attrImageVector
import org.phoenixframework.liveview.data.constants.Attrs.attrPhxValue
import org.phoenixframework.liveview.data.constants.Attrs.attrShadowElevation
import org.phoenixframework.liveview.data.constants.Attrs.attrShape
import org.phoenixframework.liveview.data.constants.Attrs.attrTemplate
import org.phoenixframework.liveview.data.constants.Attrs.attrTonalElevation
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrDividerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrInputFieldColors
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrUnfocusedLeadingIconColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrUnfocusedPlaceholderColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrUnfocusedTrailingIconColor
import org.phoenixframework.liveview.data.constants.IconPrefixValues.filled
import org.phoenixframework.liveview.data.constants.ShapeValues.rectangle
import org.phoenixframework.liveview.data.constants.SystemColorValues.Blue
import org.phoenixframework.liveview.data.constants.SystemColorValues.Gray
import org.phoenixframework.liveview.data.constants.SystemColorValues.LightGray
import org.phoenixframework.liveview.data.constants.SystemColorValues.White
import org.phoenixframework.liveview.data.constants.SystemColorValues.Yellow
import org.phoenixframework.liveview.data.constants.Templates.templateContent
import org.phoenixframework.liveview.data.constants.Templates.templateLeadingIcon
import org.phoenixframework.liveview.data.constants.Templates.templatePlaceholder
import org.phoenixframework.liveview.data.constants.Templates.templateTrailingIcon
import org.phoenixframework.liveview.domain.base.ComposableTypes.column
import org.phoenixframework.liveview.domain.base.ComposableTypes.dockedSearchBar
import org.phoenixframework.liveview.domain.base.ComposableTypes.icon
import org.phoenixframework.liveview.domain.base.ComposableTypes.searchBar
import org.phoenixframework.liveview.domain.base.ComposableTypes.text

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
                  <$searchBar $attrPhxValue="Text to search" 
                    $attrActive="false">
                    <$text>Search content</$text>
                  </$searchBar>
                  <$searchBar $attrPhxValue="" 
                    $attrActive="false">
                    <$text $attrTemplate="$templatePlaceholder">Placeholder</$text>
                    <$text $attrTemplate="$templateContent">Search content</$text>
                  </$searchBar>                    
                  <$searchBar $attrPhxValue="" 
                    $attrActive="false">
                    <$icon $attrTemplate="$templateLeadingIcon" $attrImageVector="$filled:Search"/>
                    <$text $attrTemplate="$templatePlaceholder">Placeholder</$text>
                    <$text $attrTemplate="$templateContent">Search content</$text>
                  </$searchBar>     
                  <$searchBar $attrPhxValue="" 
                    $attrActive="false">
                    <$icon $attrTemplate="$templateLeadingIcon" $attrImageVector="$filled:Search"/>
                    <$icon $attrTemplate="$templateTrailingIcon" $attrImageVector="$filled:Clear"/>
                    <$text $attrTemplate="$templatePlaceholder">Placeholder</$text>
                    <$text $attrTemplate="$templateContent">Search content</$text>
                  </$searchBar>   
                  <$searchBar $attrPhxValue="" $attrEnabled="false"
                    $attrActive="false">
                    <$icon $attrTemplate="$templateLeadingIcon" $attrImageVector="$filled:Search"/>
                    <$icon $attrTemplate="$templateTrailingIcon" $attrImageVector="$filled:Clear"/>
                    <$text $attrTemplate="$templatePlaceholder">Placeholder</$text>
                    <$text $attrTemplate="$templateContent">Search content</$text>
                  </$searchBar>                   
                  <$searchBar $attrPhxValue="" $attrTonalElevation="8" $attrShadowElevation="12"
                    $attrActive="false">
                    <$icon $attrTemplate="$templateLeadingIcon" $attrImageVector="$filled:Search"/>
                    <$icon $attrTemplate="$templateTrailingIcon" $attrImageVector="$filled:Clear"/>
                    <$text $attrTemplate="$templatePlaceholder">Placeholder</$text>
                    <$text $attrTemplate="$templateContent">Search content</$text>
                  </$searchBar>                   
                  <$searchBar $attrPhxValue="" $attrColors="$colorsForTemplate"
                    $attrActive="false">
                    <$icon $attrTemplate="$templateLeadingIcon" $attrImageVector="$filled:Search"/>
                    <$icon $attrTemplate="$templateTrailingIcon" $attrImageVector="filled:Clear"/>
                    <$text $attrTemplate="$templatePlaceholder">Placeholder</$text>
                    <$text $attrTemplate="$templateContent">Search content</$text>
                  </$searchBar>
                  <$searchBar $attrPhxValue="" $attrColors="$colorsForTemplate" $attrShape="$rectangle"
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
                <$searchBar $attrPhxValue=""
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
                <$searchBar $attrPhxValue="" $attrColors="$colorsForTemplate"
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
                  <$dockedSearchBar $attrPhxValue="Text to search" $attrActive="false">
                    <$text>Search content</$text>
                  </$dockedSearchBar>
                  <$dockedSearchBar $attrPhxValue="" $attrActive="false">
                    <$text $attrTemplate="$templatePlaceholder">Placeholder</$text>
                    <$text $attrTemplate="$templateContent">Search content</$text>
                  </$dockedSearchBar>                    
                  <$dockedSearchBar $attrPhxValue="" $attrActive="false">
                    <$icon $attrTemplate="$templateLeadingIcon" $attrImageVector="filled:Search"/>
                    <$text $attrTemplate="$templatePlaceholder">Placeholder</$text>
                    <$text $attrTemplate="$templateContent">Search content</$text>
                  </$dockedSearchBar>     
                  <$dockedSearchBar $attrPhxValue="" $attrActive="false">
                    <$icon $attrTemplate="$templateLeadingIcon" $attrImageVector="filled:Search"/>
                    <$icon $attrTemplate="$templateTrailingIcon" $attrImageVector="filled:Clear"/>
                    <$text $attrTemplate="$templatePlaceholder">Placeholder</$text>
                    <$text $attrTemplate="$templateContent">Search content</$text>
                  </$dockedSearchBar>   
                  <$dockedSearchBar $attrPhxValue="" $attrEnabled="false" $attrActive="false">
                    <$icon $attrTemplate="$templateLeadingIcon" $attrImageVector="filled:Search"/>
                    <$icon $attrTemplate="$templateTrailingIcon" $attrImageVector="filled:Clear"/>
                    <$text $attrTemplate="$templatePlaceholder">Placeholder</$text>
                    <$text $attrTemplate="$templateContent">Search content</$text>
                  </$dockedSearchBar>                   
                  <$dockedSearchBar $attrPhxValue="" $attrTonalElevation="8" $attrShadowElevation="12" $attrActive="false">
                    <$icon $attrTemplate="$templateLeadingIcon" $attrImageVector="filled:Search"/>
                    <$icon $attrTemplate="$templateTrailingIcon" $attrImageVector="filled:Clear"/>
                    <$text $attrTemplate="$templatePlaceholder">Placeholder</$text>
                    <$text $attrTemplate="$templateContent">Search content</$text>
                  </$dockedSearchBar>                   
                  <$dockedSearchBar $attrPhxValue="" $attrColors="$colorsForTemplate" $attrActive="false">
                    <$icon $attrTemplate="$templateLeadingIcon" $attrImageVector="filled:Search"/>
                    <$icon $attrTemplate="$templateTrailingIcon" $attrImageVector="filled:Clear"/>
                    <$text $attrTemplate="$templatePlaceholder">Placeholder</$text>
                    <$text $attrTemplate="$templateContent">Search content</$text>
                  </$dockedSearchBar>
                  <$dockedSearchBar $attrPhxValue="" $attrColors="$colorsForTemplate" $attrShape="$rectangle" $attrActive="false">
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
                <$dockedSearchBar $attrPhxValue="" $attrActive="true">
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
                <$dockedSearchBar $attrPhxValue="" $attrColors="$colorsForTemplate" $attrActive="true">
                  <$icon $attrTemplate="$templateLeadingIcon" $attrImageVector="filled:Search"/>
                  <$icon $attrTemplate="$templateTrailingIcon" $attrImageVector="filled:Clear"/>
                  <$text $attrTemplate="$templatePlaceholder">Placeholder</$text>
                  <$text $attrTemplate="$templateContent">Search content</$text>
                </$dockedSearchBar>             
                """
        )
    }
}