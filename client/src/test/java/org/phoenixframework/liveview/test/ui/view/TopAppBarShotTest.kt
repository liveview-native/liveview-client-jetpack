package org.phoenixframework.liveview.test.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.graphics.Color
import org.phoenixframework.liveview.test.base.LiveViewComposableTest
import org.junit.Test
import org.phoenixframework.liveview.data.constants.Attrs.attrColors
import org.phoenixframework.liveview.data.constants.Attrs.attrImageVector
import org.phoenixframework.liveview.data.constants.Attrs.attrTemplate
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrActionIconContentColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrNavigationIconContentColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrTitleContentColor
import org.phoenixframework.liveview.data.constants.ComposableTypes.centerAlignedTopAppBar
import org.phoenixframework.liveview.data.constants.ComposableTypes.column
import org.phoenixframework.liveview.data.constants.ComposableTypes.icon
import org.phoenixframework.liveview.data.constants.ComposableTypes.iconButton
import org.phoenixframework.liveview.data.constants.ComposableTypes.largeTopAppBar
import org.phoenixframework.liveview.data.constants.ComposableTypes.mediumTopAppBar
import org.phoenixframework.liveview.data.constants.ComposableTypes.text
import org.phoenixframework.liveview.data.constants.ComposableTypes.topAppBar
import org.phoenixframework.liveview.data.constants.Templates.templateAction
import org.phoenixframework.liveview.data.constants.Templates.templateNavigationIcon
import org.phoenixframework.liveview.data.constants.Templates.templateTitle

@OptIn(ExperimentalMaterial3Api::class)
class TopAppBarShotTest : LiveViewComposableTest() {
    @Test
    fun simpleTopAppBarTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Column {
                    TopAppBar(
                        title = {
                            Text(text = "App Bar 1")
                        },
                    )
                    TopAppBar(
                        title = {
                            Text(text = "App Bar 2")
                        },
                        navigationIcon = {
                            IconButton(onClick = {}) {
                                Icon(imageVector = Icons.Filled.Menu, contentDescription = "")
                            }
                        },
                    )
                    TopAppBar(
                        title = {
                            Text(text = "App Bar 3")
                        },
                        navigationIcon = {
                            IconButton(onClick = {}) {
                                Icon(imageVector = Icons.Filled.Menu, contentDescription = "")
                            }
                        },
                        actions = {
                            IconButton(onClick = {}) {
                                Icon(imageVector = Icons.Filled.Add, contentDescription = "")
                            }
                        }
                    )
                }
            },
            template = """
                <$column>
                  <$topAppBar>
                    <$text $attrTemplate="$templateTitle">App Bar 1</$text>
                  </$topAppBar>   
                  <$topAppBar>
                    <$text $attrTemplate="$templateTitle">App Bar 2</$text>
                    <$iconButton $attrTemplate="$templateNavigationIcon">
                      <$icon $attrImageVector="filled:Menu" />
                    </$iconButton>
                  </$topAppBar>   
                  <$topAppBar>
                    <$text $attrTemplate="$templateTitle">App Bar 3</$text>
                    <$iconButton $attrTemplate="$templateNavigationIcon">
                      <$icon $attrImageVector="filled:Menu" />
                    </$iconButton>                    
                    <$iconButton $attrTemplate="$templateAction">
                      <$icon $attrImageVector="filled:Add" />
                    </$iconButton>
                  </$topAppBar>                                                     
                </$column>
                """
        )
    }

    @Test
    fun topAppBarWithColors() {
        val colorsForTemplate = """
            {
            '$colorAttrContainerColor': '#FF0000FF',
            '$colorAttrNavigationIconContentColor': '#FFFFFF00',
            '$colorAttrTitleContentColor': '#FFFFFFFF',
            '$colorAttrActionIconContentColor': '#FF00FFFF'
            }
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Blue,
                    navigationIconContentColor = Color.Yellow,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.Cyan
                )
                TopAppBar(
                    title = {
                        Text(text = "App Bar 1")
                    },
                    navigationIcon = {
                        IconButton(onClick = {}) {
                            Icon(imageVector = Icons.Filled.Menu, contentDescription = "")
                        }
                    },
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(imageVector = Icons.Filled.Add, contentDescription = "")
                        }
                    },
                    colors = colors
                )
            },
            template = """
                <$topAppBar $attrColors="$colorsForTemplate">
                  <$text $attrTemplate="$templateTitle">App Bar 1</$text>
                  <$iconButton $attrTemplate="$templateNavigationIcon">
                    <$icon $attrImageVector="filled:Menu" />
                  </$iconButton>                    
                  <$iconButton $attrTemplate="$templateAction">
                    <$icon $attrImageVector="filled:Add" />
                  </$iconButton>
                </$topAppBar>                   
                """
        )
    }

    @Test
    fun simpleCenterAlignedTopAppBarTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Column {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(text = "App Bar 1")
                        },
                    )
                    CenterAlignedTopAppBar(
                        title = {
                            Text(text = "App Bar 2")
                        },
                        navigationIcon = {
                            IconButton(onClick = {}) {
                                Icon(imageVector = Icons.Filled.Menu, contentDescription = "")
                            }
                        },
                    )
                    CenterAlignedTopAppBar(
                        title = {
                            Text(text = "App Bar 3")
                        },
                        navigationIcon = {
                            IconButton(onClick = {}) {
                                Icon(imageVector = Icons.Filled.Menu, contentDescription = "")
                            }
                        },
                        actions = {
                            IconButton(onClick = {}) {
                                Icon(imageVector = Icons.Filled.Add, contentDescription = "")
                            }
                        }
                    )
                }
            },
            template = """
                <$column>
                  <$centerAlignedTopAppBar>
                    <$text $attrTemplate="$templateTitle">App Bar 1</$text>
                  </$centerAlignedTopAppBar>   
                  <$centerAlignedTopAppBar>
                    <$text $attrTemplate="$templateTitle">App Bar 2</$text>
                    <$iconButton $attrTemplate="$templateNavigationIcon">
                      <$icon $attrImageVector="filled:Menu" />
                    </$iconButton>
                  </$centerAlignedTopAppBar>   
                  <$centerAlignedTopAppBar>
                    <$text $attrTemplate="$templateTitle">App Bar 3</$text>
                    <$iconButton $attrTemplate="$templateNavigationIcon">
                      <$icon $attrImageVector="filled:Menu" />
                    </$iconButton>                    
                    <$iconButton $attrTemplate="$templateAction">
                      <$icon $attrImageVector="filled:Add" />
                    </$iconButton>
                  </$centerAlignedTopAppBar>                                                     
                </$column>
                """
        )
    }

    @Test
    fun centerAlignedTopAppBarWithColors() {
        val colorsForTemplate = """
            {
            '$colorAttrContainerColor': '#FF0000FF',
            '$colorAttrNavigationIconContentColor': '#FFFFFF00',
            '$colorAttrTitleContentColor': '#FFFFFFFF',
            '$colorAttrActionIconContentColor': '#FF00FFFF'
            }
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Blue,
                    navigationIconContentColor = Color.Yellow,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.Cyan
                )
                CenterAlignedTopAppBar(
                    title = {
                        Text(text = "App Bar 1")
                    },
                    navigationIcon = {
                        IconButton(onClick = {}) {
                            Icon(imageVector = Icons.Filled.Menu, contentDescription = "")
                        }
                    },
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(imageVector = Icons.Filled.Add, contentDescription = "")
                        }
                    },
                    colors = colors
                )
            },
            template = """
                <$centerAlignedTopAppBar $attrColors="$colorsForTemplate">
                  <$text $attrTemplate="$templateTitle">App Bar 1</$text>
                  <$iconButton $attrTemplate="$templateNavigationIcon">
                    <$icon $attrImageVector="filled:Menu" />
                  </$iconButton>                    
                  <$iconButton $attrTemplate="$templateAction">
                    <$icon $attrImageVector="filled:Add" />
                  </$iconButton>
                </$centerAlignedTopAppBar>                   
                """
        )
    }

    @Test
    fun simpleMediumTopAppBarTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Column {
                    MediumTopAppBar(
                        title = {
                            Text(text = "App Bar 1")
                        },
                    )
                    MediumTopAppBar(
                        title = {
                            Text(text = "App Bar 2")
                        },
                        navigationIcon = {
                            IconButton(onClick = {}) {
                                Icon(imageVector = Icons.Filled.Menu, contentDescription = "")
                            }
                        },
                    )
                    MediumTopAppBar(
                        title = {
                            Text(text = "App Bar 3")
                        },
                        navigationIcon = {
                            IconButton(onClick = {}) {
                                Icon(imageVector = Icons.Filled.Menu, contentDescription = "")
                            }
                        },
                        actions = {
                            IconButton(onClick = {}) {
                                Icon(imageVector = Icons.Filled.Add, contentDescription = "")
                            }
                        }
                    )
                }
            },
            template = """
                <$column>
                  <$mediumTopAppBar>
                    <$text $attrTemplate="$templateTitle">App Bar 1</$text>
                  </$mediumTopAppBar>   
                  <$mediumTopAppBar>
                    <$text $attrTemplate="$templateTitle">App Bar 2</$text>
                    <$iconButton $attrTemplate="$templateNavigationIcon">
                      <$icon $attrImageVector="filled:Menu" />
                    </$iconButton>
                  </$mediumTopAppBar>   
                  <$mediumTopAppBar>
                    <$text $attrTemplate="$templateTitle">App Bar 3</$text>
                    <$iconButton $attrTemplate="$templateNavigationIcon">
                      <$icon $attrImageVector="filled:Menu" />
                    </$iconButton>                    
                    <$iconButton $attrTemplate="$templateAction">
                      <$icon $attrImageVector="filled:Add" />
                    </$iconButton>
                  </$mediumTopAppBar>                                                     
                </$column>
                """
        )
    }

    @Test
    fun mediumTopAppBarWithColors() {
        val colorsForTemplate = """
            {
            '$colorAttrContainerColor': '#FF0000FF',
            '$colorAttrNavigationIconContentColor': '#FFFFFF00',
            '$colorAttrTitleContentColor': '#FFFFFFFF',
            '$colorAttrActionIconContentColor': '#FF00FFFF'
            }
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.Blue,
                    navigationIconContentColor = Color.Yellow,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.Cyan
                )
                MediumTopAppBar(
                    title = {
                        Text(text = "App Bar 1")
                    },
                    navigationIcon = {
                        IconButton(onClick = {}) {
                            Icon(imageVector = Icons.Filled.Menu, contentDescription = "")
                        }
                    },
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(imageVector = Icons.Filled.Add, contentDescription = "")
                        }
                    },
                    colors = colors
                )
            },
            template = """
                <$mediumTopAppBar $attrColors="$colorsForTemplate">
                  <$text $attrTemplate="$templateTitle">App Bar 1</$text>
                  <$iconButton $attrTemplate="$templateNavigationIcon">
                    <$icon $attrImageVector="filled:Menu" />
                  </$iconButton>                    
                  <$iconButton $attrTemplate="$templateAction">
                    <$icon $attrImageVector="filled:Add" />
                  </$iconButton>
                </$mediumTopAppBar>                   
                """
        )
    }

    @Test
    fun simpleLargeTopAppBarTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Column {
                    LargeTopAppBar(
                        title = {
                            Text(text = "App Bar 1")
                        },
                    )
                    LargeTopAppBar(
                        title = {
                            Text(text = "App Bar 2")
                        },
                        navigationIcon = {
                            IconButton(onClick = {}) {
                                Icon(imageVector = Icons.Filled.Menu, contentDescription = "")
                            }
                        },
                    )
                    LargeTopAppBar(
                        title = {
                            Text(text = "App Bar 3")
                        },
                        navigationIcon = {
                            IconButton(onClick = {}) {
                                Icon(imageVector = Icons.Filled.Menu, contentDescription = "")
                            }
                        },
                        actions = {
                            IconButton(onClick = {}) {
                                Icon(imageVector = Icons.Filled.Add, contentDescription = "")
                            }
                        }
                    )
                }
            },
            template = """
                <$column>
                  <$largeTopAppBar>
                    <$text $attrTemplate="$templateTitle">App Bar 1</$text>
                  </$largeTopAppBar>   
                  <$largeTopAppBar>
                    <$text $attrTemplate="$templateTitle">App Bar 2</$text>
                    <$iconButton $attrTemplate="$templateNavigationIcon">
                      <$icon $attrImageVector="filled:Menu" />
                    </$iconButton>
                  </$largeTopAppBar>   
                  <$largeTopAppBar>
                    <$text $attrTemplate="$templateTitle">App Bar 3</$text>
                    <$iconButton $attrTemplate="$templateNavigationIcon">
                      <$icon $attrImageVector="filled:Menu" />
                    </$iconButton>                    
                    <$iconButton $attrTemplate="$templateAction">
                      <$icon $attrImageVector="filled:Add" />
                    </$iconButton>
                  </$largeTopAppBar>                                                     
                </$column>
                """
        )
    }

    @Test
    fun largeTopAppBarWithColors() {
        val colorsForTemplate = """
            {
            '$colorAttrContainerColor': '#FF0000FF',
            '$colorAttrNavigationIconContentColor': '#FFFFFF00',
            '$colorAttrTitleContentColor': '#FFFFFFFF',
            '$colorAttrActionIconContentColor': '#FF00FFFF'
            }
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = Color.Blue,
                    navigationIconContentColor = Color.Yellow,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.Cyan
                )
                LargeTopAppBar(
                    title = {
                        Text(text = "App Bar 1")
                    },
                    navigationIcon = {
                        IconButton(onClick = {}) {
                            Icon(imageVector = Icons.Filled.Menu, contentDescription = "")
                        }
                    },
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(imageVector = Icons.Filled.Add, contentDescription = "")
                        }
                    },
                    colors = colors
                )
            },
            template = """
                <$largeTopAppBar $attrColors="$colorsForTemplate">
                  <$text $attrTemplate="$templateTitle">App Bar 1</$text>
                  <$iconButton $attrTemplate="$templateNavigationIcon">
                    <$icon $attrImageVector="filled:Menu" />
                  </$iconButton>                    
                  <$iconButton $attrTemplate="$templateAction">
                    <$icon $attrImageVector="filled:Add" />
                  </$iconButton>
                </$largeTopAppBar>                   
                """
        )
    }
}