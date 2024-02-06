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

@OptIn(ExperimentalMaterial3Api::class)
class SearchBarShotTest : LiveViewComposableTest() {

    @Test
    fun simpleSearchBarTest() {
        val colorsForTemplate = """
            {
            'containerColor': 'system-blue',
            'dividerColor': 'system-yellow',
            'inputFieldColors': {
              'unfocusedPlaceholderColor': 'system-white',
              'unfocusedLeadingIconColor': 'system-light-gray',
              'unfocusedTrailingIconColor': 'system-gray'        
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
                <Column>
                  <SearchBar phx-value="Text to search" 
                    phx-change="" phx-submit="" active="false" on-active-change="">
                    <Text>Search content</Text>
                  </SearchBar>
                  <SearchBar phx-value="" 
                    phx-change="" phx-submit="" active="false" on-active-change="">
                    <Text template="placeholder">Placeholder</Text>
                    <Text template="content">Search content</Text>
                  </SearchBar>                    
                  <SearchBar phx-value="" 
                    phx-change="" phx-submit="" active="false" on-active-change="">
                    <Icon template="leadingIcon" image-vector="filled:Search"/>
                    <Text template="placeholder">Placeholder</Text>
                    <Text template="content">Search content</Text>
                  </SearchBar>     
                  <SearchBar phx-value="" 
                    phx-change="" phx-submit="" active="false" on-active-change="">
                    <Icon template="leadingIcon" image-vector="filled:Search"/>
                    <Icon template="trailingIcon" image-vector="filled:Clear"/>
                    <Text template="placeholder">Placeholder</Text>
                    <Text template="content">Search content</Text>
                  </SearchBar>   
                  <SearchBar phx-value="" enabled="false"
                    phx-change="" phx-submit="" active="false" on-active-change="">
                    <Icon template="leadingIcon" image-vector="filled:Search"/>
                    <Icon template="trailingIcon" image-vector="filled:Clear"/>
                    <Text template="placeholder">Placeholder</Text>
                    <Text template="content">Search content</Text>
                  </SearchBar>                   
                  <SearchBar phx-value="" tonal-elevation="8" shadow-elevation="12"
                    phx-change="" phx-submit="" active="false" on-active-change="">
                    <Icon template="leadingIcon" image-vector="filled:Search"/>
                    <Icon template="trailingIcon" image-vector="filled:Clear"/>
                    <Text template="placeholder">Placeholder</Text>
                    <Text template="content">Search content</Text>
                  </SearchBar>                   
                  <SearchBar phx-value="" colors="$colorsForTemplate"
                    phx-change="" phx-submit="" active="false" on-active-change="">
                    <Icon template="leadingIcon" image-vector="filled:Search"/>
                    <Icon template="trailingIcon" image-vector="filled:Clear"/>
                    <Text template="placeholder">Placeholder</Text>
                    <Text template="content">Search content</Text>
                  </SearchBar>
                  <SearchBar phx-value="" colors="$colorsForTemplate" shape="rectangle"
                    phx-change="" phx-submit="" active="false" on-active-change="">
                    <Icon template="leadingIcon" image-vector="filled:Search"/>
                    <Icon template="trailingIcon" image-vector="filled:Clear"/>
                    <Text template="placeholder">Placeholder</Text>
                    <Text template="content">Search content</Text>
                  </SearchBar>                   
                </Column>  
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
                <SearchBar phx-value=""
                  phx-change="" phx-submit="" active="true" on-active-change="">
                  <Icon template="leadingIcon" image-vector="filled:Search"/>
                  <Icon template="trailingIcon" image-vector="filled:Clear"/>
                  <Text template="placeholder">Placeholder</Text>
                  <Text template="content">Search content</Text>
                </SearchBar>             
                """
        )
    }

    @Test
    fun activeSearchBarWithCustomColorsTest() {
        val colorsForTemplate = """
            {
            'containerColor': 'system-blue',
            'dividerColor': 'system-yellow',
            'inputFieldColors': {
              'unfocusedPlaceholderColor': 'system-white',
              'unfocusedLeadingIconColor': 'system-light-gray',
              'unfocusedTrailingIconColor': 'system-gray'        
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
                <SearchBar phx-value="" colors="$colorsForTemplate"
                  phx-change="" phx-submit="" active="true" on-active-change="">
                  <Icon template="leadingIcon" image-vector="filled:Search"/>
                  <Icon template="trailingIcon" image-vector="filled:Clear"/>
                  <Text template="placeholder">Placeholder</Text>
                  <Text template="content">Search content</Text>
                </SearchBar>             
                """
        )
    }

    @Test
    fun simpleDockedSearchBarTest() {
        val colorsForTemplate = """
            {
            'containerColor': 'system-blue',
            'dividerColor': 'system-yellow',
            'inputFieldColors': {
              'unfocusedPlaceholderColor': 'system-white',
              'unfocusedLeadingIconColor': 'system-light-gray',
              'unfocusedTrailingIconColor': 'system-gray'        
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
                <Column>
                  <DockedSearchBar phx-value="Text to search" 
                    phx-change="" phx-submit="" active="false" on-active-change="">
                    <Text>Search content</Text>
                  </DockedSearchBar>
                  <DockedSearchBar phx-value="" 
                    phx-change="" phx-submit="" active="false" on-active-change="">
                    <Text template="placeholder">Placeholder</Text>
                    <Text template="content">Search content</Text>
                  </DockedSearchBar>                    
                  <DockedSearchBar phx-value="" 
                    phx-change="" phx-submit="" active="false" on-active-change="">
                    <Icon template="leadingIcon" image-vector="filled:Search"/>
                    <Text template="placeholder">Placeholder</Text>
                    <Text template="content">Search content</Text>
                  </DockedSearchBar>     
                  <DockedSearchBar phx-value="" 
                    phx-change="" phx-submit="" active="false" on-active-change="">
                    <Icon template="leadingIcon" image-vector="filled:Search"/>
                    <Icon template="trailingIcon" image-vector="filled:Clear"/>
                    <Text template="placeholder">Placeholder</Text>
                    <Text template="content">Search content</Text>
                  </DockedSearchBar>   
                  <DockedSearchBar phx-value="" enabled="false"
                    phx-change="" phx-submit="" active="false" on-active-change="">
                    <Icon template="leadingIcon" image-vector="filled:Search"/>
                    <Icon template="trailingIcon" image-vector="filled:Clear"/>
                    <Text template="placeholder">Placeholder</Text>
                    <Text template="content">Search content</Text>
                  </DockedSearchBar>                   
                  <DockedSearchBar phx-value="" tonal-elevation="8" shadow-elevation="12"
                    phx-change="" phx-submit="" active="false" on-active-change="">
                    <Icon template="leadingIcon" image-vector="filled:Search"/>
                    <Icon template="trailingIcon" image-vector="filled:Clear"/>
                    <Text template="placeholder">Placeholder</Text>
                    <Text template="content">Search content</Text>
                  </DockedSearchBar>                   
                  <DockedSearchBar phx-value="" colors="$colorsForTemplate"
                    phx-change="" phx-submit="" active="false" on-active-change="">
                    <Icon template="leadingIcon" image-vector="filled:Search"/>
                    <Icon template="trailingIcon" image-vector="filled:Clear"/>
                    <Text template="placeholder">Placeholder</Text>
                    <Text template="content">Search content</Text>
                  </DockedSearchBar>
                  <DockedSearchBar phx-value="" colors="$colorsForTemplate" shape="rectangle"
                    phx-change="" phx-submit="" active="false" on-active-change="">
                    <Icon template="leadingIcon" image-vector="filled:Search"/>
                    <Icon template="trailingIcon" image-vector="filled:Clear"/>
                    <Text template="placeholder">Placeholder</Text>
                    <Text template="content">Search content</Text>
                  </DockedSearchBar>                   
                </Column>  
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
                <DockedSearchBar phx-value=""
                  phx-change="" phx-submit="" active="true" on-active-change="">
                  <Icon template="leadingIcon" image-vector="filled:Search"/>
                  <Icon template="trailingIcon" image-vector="filled:Clear"/>
                  <Text template="placeholder">Placeholder</Text>
                  <Text template="content">Search content</Text>
                </DockedSearchBar>             
                """
        )
    }

    @Test
    fun activeDockedSearchBarWithCustomColorsTest() {
        val colorsForTemplate = """
            {
            'containerColor': 'system-blue',
            'dividerColor': 'system-yellow',
            'inputFieldColors': {
              'unfocusedPlaceholderColor': 'system-white',
              'unfocusedLeadingIconColor': 'system-light-gray',
              'unfocusedTrailingIconColor': 'system-gray'        
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
                <DockedSearchBar phx-value="" colors="$colorsForTemplate"
                  phx-change="" phx-submit="" active="true" on-active-change="">
                  <Icon template="leadingIcon" image-vector="filled:Search"/>
                  <Icon template="trailingIcon" image-vector="filled:Clear"/>
                  <Text template="placeholder">Placeholder</Text>
                  <Text template="content">Search content</Text>
                </DockedSearchBar>             
                """
        )
    }
}