package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.HorizontalDistribute
import androidx.compose.material.icons.filled.VerticalDistribute
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test
import org.phoenixframework.liveview.data.constants.AlignmentValues.center
import org.phoenixframework.liveview.data.constants.Attrs.attrContainerColor
import org.phoenixframework.liveview.data.constants.Attrs.attrContentAlignment
import org.phoenixframework.liveview.data.constants.Attrs.attrContentColor
import org.phoenixframework.liveview.data.constants.Attrs.attrFabPosition
import org.phoenixframework.liveview.data.constants.Attrs.attrImageVector
import org.phoenixframework.liveview.data.constants.Attrs.attrPhxValue
import org.phoenixframework.liveview.data.constants.Attrs.attrSelected
import org.phoenixframework.liveview.data.constants.Attrs.attrSize
import org.phoenixframework.liveview.data.constants.Attrs.attrTemplate
import org.phoenixframework.liveview.data.constants.FabPositionValues
import org.phoenixframework.liveview.data.constants.IconPrefixValues.filled
import org.phoenixframework.liveview.data.constants.SizeValues.fill
import org.phoenixframework.liveview.data.constants.Templates.templateBody
import org.phoenixframework.liveview.data.constants.Templates.templateBottomBar
import org.phoenixframework.liveview.data.constants.Templates.templateFab
import org.phoenixframework.liveview.data.constants.Templates.templateIcon
import org.phoenixframework.liveview.data.constants.Templates.templateTitle
import org.phoenixframework.liveview.data.constants.Templates.templateTopBar
import org.phoenixframework.liveview.domain.base.ComposableTypes.box
import org.phoenixframework.liveview.domain.base.ComposableTypes.floatingActionButton
import org.phoenixframework.liveview.domain.base.ComposableTypes.icon
import org.phoenixframework.liveview.domain.base.ComposableTypes.navigationBar
import org.phoenixframework.liveview.domain.base.ComposableTypes.navigationBarItem
import org.phoenixframework.liveview.domain.base.ComposableTypes.scaffold
import org.phoenixframework.liveview.domain.base.ComposableTypes.text
import org.phoenixframework.liveview.domain.base.ComposableTypes.topAppBar

@OptIn(ExperimentalMaterial3Api::class)
class ScaffoldShotTest : LiveViewComposableTest() {
    @Test
    fun simpleScaffoldTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Scaffold {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it),
                    ) {
                        Text(text = "Scaffold Body")
                    }
                }
            },
            template = """
                <$scaffold>
                  <$box $attrSize="$fill" $attrContentAlignment="$center" $attrTemplate="$templateBody">
                    <$text>Scaffold Body</$text> 
                  </$box>
                </$scaffold>
                """
        )
    }

    @Test
    fun scaffoldWithColorsTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Scaffold(
                    containerColor = Color.Magenta,
                    contentColor = Color.Blue
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it),
                    ) {
                        Text(text = "Scaffold Body")
                    }
                }
            },
            template = """
                <$scaffold $attrContainerColor="#FFFF00FF" $attrContentColor="#FF0000FF">
                  <$box $attrSize="$fill" $attrContentAlignment="$center" $attrTemplate="$templateBody">
                    <$text>Scaffold Body</$text> 
                  </$box>
                </$scaffold>
                """
        )
    }

    @Test
    fun scaffoldWithTitleTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = "Top Bar")
                            },
                        )
                    },
                    content = {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it),
                        ) {
                            Text(text = "Scaffold Body")
                        }
                    },
                )
            },
            template = """
                <$scaffold>
                  <$topAppBar $attrTemplate="$templateTopBar">
                    <$text $attrTemplate="$templateTitle">Top Bar</$text>
                  </$topAppBar>  
                  <$box $attrSize="$fill" $attrContentAlignment="$center" $attrTemplate="$templateBody">
                    <$text>Scaffold Body</$text> 
                  </$box>
                </$scaffold>
                """
        )
    }

    @Test
    fun scaffoldWithBottomBarTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = "Top Bar")
                            },
                        )
                    },
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(
                                selected = true,
                                onClick = { },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Filled.HorizontalDistribute,
                                        contentDescription = ""
                                    )
                                },
                            )
                            NavigationBarItem(
                                selected = false,
                                onClick = { },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Filled.VerticalDistribute,
                                        contentDescription = ""
                                    )
                                },
                            )
                        }
                    },
                    content = {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it),
                        ) {
                            Text(text = "Scaffold Body")
                        }
                    },
                )
            },
            template = """
                <$scaffold>
                  <$topAppBar $attrTemplate="$templateTopBar">
                    <$text $attrTemplate="$templateTitle">Top Bar</$text>
                  </$topAppBar>  
                  <$navigationBar $attrTemplate="$templateBottomBar">
                    <$navigationBarItem $attrSelected="true" $attrPhxValue="0">
                      <$icon $attrImageVector="$filled:HorizontalDistribute" $attrTemplate="$templateIcon"/>
                    </$navigationBarItem>
                    <$navigationBarItem $attrSelected="false" $attrPhxValue="1">
                      <$icon $attrImageVector="$filled:VerticalDistribute" $attrTemplate="$templateIcon" />
                    </$navigationBarItem>     
                  </$navigationBar>               
                  <$box $attrSize="$fill" $attrContentAlignment="$center" $attrTemplate="$templateBody">
                    <$text>Scaffold Body</$text> 
                  </$box>
                </$scaffold>
                """
        )
    }

    @Test
    fun scaffoldWithFabTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = "Top Bar")
                            },
                        )
                    },
                    floatingActionButton = {
                        FloatingActionButton(onClick = { }) {
                            Icon(imageVector = Icons.Filled.Add, contentDescription = "")
                        }
                    },
                    content = {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it),
                        ) {
                            Text(text = "Scaffold Body")
                        }
                    },
                )
            },
            template = """
                <$scaffold>
                  <$topAppBar $attrTemplate="$templateTopBar">
                    <$text $attrTemplate="$templateTitle">Top Bar</$text>
                  </$topAppBar>  
                  <$floatingActionButton $attrTemplate="$templateFab">
                    <$icon $attrImageVector="$filled:Add"/>
                  </$floatingActionButton>
                  <$box $attrSize="$fill" $attrContentAlignment="$center" $attrTemplate="$templateBody">
                    <$text>Scaffold Body</$text> 
                  </$box>
                </$scaffold>
                """
        )
    }

    @Test
    fun scaffoldWithFabOnCenterTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = "Top Bar")
                            },
                        )
                    },
                    floatingActionButtonPosition = FabPosition.Center,
                    floatingActionButton = {
                        FloatingActionButton(onClick = { }) {
                            Icon(imageVector = Icons.Filled.Add, contentDescription = "")
                        }
                    },
                    content = {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it),
                        ) {
                            Text(text = "Scaffold Body")
                        }
                    },
                )
            },
            template = """
                <$scaffold $attrFabPosition="${FabPositionValues.center}">
                  <$topAppBar $attrTemplate="$templateTopBar">
                    <$text $attrTemplate="$templateTitle">Top Bar</$text>
                  </$topAppBar>  
                  <$floatingActionButton $attrTemplate="$templateFab">
                    <$icon $attrImageVector="filled:Add"/>
                  </$floatingActionButton>
                  <$box $attrSize="$fill" $attrContentAlignment="$center" $attrTemplate="$templateBody">
                    <$text>Scaffold Body</$text> 
                  </$box>
                </$scaffold>
                """
        )
    }

    @Test
    fun scaffoldWithFabAndBottomBarTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = "Top Bar")
                            },
                        )
                    },
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(
                                selected = true,
                                onClick = { },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Filled.HorizontalDistribute,
                                        contentDescription = ""
                                    )
                                },
                            )
                            NavigationBarItem(
                                selected = false,
                                onClick = { },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Filled.VerticalDistribute,
                                        contentDescription = ""
                                    )
                                },
                            )
                        }
                    },
                    floatingActionButton = {
                        FloatingActionButton(onClick = { }) {
                            Icon(imageVector = Icons.Filled.Add, contentDescription = "")
                        }
                    },
                    content = {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it),
                        ) {
                            Text(text = "Scaffold Body")
                        }
                    },
                )
            },
            template = """
                <$scaffold>
                  <$topAppBar $attrTemplate="$templateTopBar">
                    <$text $attrTemplate="$templateTitle">Top Bar</$text>
                  </$topAppBar>  
                  <$navigationBar $attrTemplate="$templateBottomBar">
                    <$navigationBarItem $attrSelected="true" $attrPhxValue="0">
                      <$icon $attrImageVector="filled:HorizontalDistribute" $attrTemplate="$templateIcon"/>
                    </$navigationBarItem>
                    <$navigationBarItem $attrSelected="false" $attrPhxValue="1">
                      <$icon $attrImageVector="filled:VerticalDistribute" $attrTemplate="$templateIcon" />
                    </$navigationBarItem>     
                  </$navigationBar>
                  <$floatingActionButton $attrTemplate="$templateFab">
                    <$icon $attrImageVector="filled:Add"/>
                  </$floatingActionButton>
                  <$box $attrSize="$fill" $attrContentAlignment="$center" $attrTemplate="$templateBody">
                    <$text>Scaffold Body</$text> 
                  </$box>
                </$scaffold>
                """
        )
    }

    @Test
    fun scaffoldWithFabOnCenterAndBottomBarTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = "Top Bar")
                            },
                        )
                    },
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(
                                selected = true,
                                onClick = { },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Filled.HorizontalDistribute,
                                        contentDescription = ""
                                    )
                                },
                            )
                            NavigationBarItem(
                                selected = false,
                                onClick = { },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Filled.VerticalDistribute,
                                        contentDescription = ""
                                    )
                                },
                            )
                        }
                    },
                    floatingActionButtonPosition = FabPosition.Center,
                    floatingActionButton = {
                        FloatingActionButton(onClick = { }) {
                            Icon(imageVector = Icons.Filled.Add, contentDescription = "")
                        }
                    },
                    content = {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it),
                        ) {
                            Text(text = "Scaffold Body")
                        }
                    },
                )
            },
            template = """
                <$scaffold $attrFabPosition="${FabPositionValues.center}">
                  <$topAppBar $attrTemplate="$templateTopBar">
                    <$text $attrTemplate="$templateTitle">Top Bar</$text>
                  </$topAppBar>  
                  <$navigationBar $attrTemplate="$templateBottomBar">
                    <$navigationBarItem $attrSelected="true" $attrPhxValue="0">
                      <$icon $attrImageVector="filled:HorizontalDistribute" $attrTemplate="$templateIcon"/>
                    </$navigationBarItem>
                    <$navigationBarItem $attrSelected="false" $attrPhxValue="1">
                      <$icon $attrImageVector="filled:VerticalDistribute" $attrTemplate="$templateIcon" />
                    </$navigationBarItem>     
                  </$navigationBar>
                  <$floatingActionButton $attrTemplate="$templateFab">
                    <$icon $attrImageVector="filled:Add"/>
                  </$floatingActionButton>
                  <$box $attrSize="$fill" $attrContentAlignment="$center" $attrTemplate="$templateBody">
                    <$text>Scaffold Body</$text> 
                  </$box>
                </$scaffold>
                """
        )
    }
}