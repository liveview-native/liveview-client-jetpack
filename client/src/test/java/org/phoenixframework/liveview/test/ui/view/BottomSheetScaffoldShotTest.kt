package org.phoenixframework.liveview.test.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.junit.Ignore
import org.junit.Test
import org.phoenixframework.liveview.data.constants.AlignmentValues.center
import org.phoenixframework.liveview.data.constants.Attrs.attrContainerColor
import org.phoenixframework.liveview.data.constants.Attrs.attrContentColor
import org.phoenixframework.liveview.data.constants.Attrs.attrFontSize
import org.phoenixframework.liveview.data.constants.Attrs.attrImageVector
import org.phoenixframework.liveview.data.constants.Attrs.attrMessage
import org.phoenixframework.liveview.data.constants.Attrs.attrSheetContainerColor
import org.phoenixframework.liveview.data.constants.Attrs.attrSheetContentColor
import org.phoenixframework.liveview.data.constants.Attrs.attrSheetPeekHeight
import org.phoenixframework.liveview.data.constants.Attrs.attrSheetShape
import org.phoenixframework.liveview.data.constants.Attrs.attrSheetSkipHiddenState
import org.phoenixframework.liveview.data.constants.Attrs.attrSheetValue
import org.phoenixframework.liveview.data.constants.Attrs.attrStyle
import org.phoenixframework.liveview.data.constants.Attrs.attrTemplate
import org.phoenixframework.liveview.data.constants.ComposableTypes.bottomSheetScaffold
import org.phoenixframework.liveview.data.constants.ComposableTypes.box
import org.phoenixframework.liveview.data.constants.ComposableTypes.icon
import org.phoenixframework.liveview.data.constants.ComposableTypes.snackbar
import org.phoenixframework.liveview.data.constants.ComposableTypes.text
import org.phoenixframework.liveview.data.constants.ComposableTypes.topAppBar
import org.phoenixframework.liveview.data.constants.IconPrefixValues.filled
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierAlign
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierFillMaxSize
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierFillMaxWidth
import org.phoenixframework.liveview.data.constants.ModifierTypes.typeAlignment
import org.phoenixframework.liveview.data.constants.SheetValues.expanded
import org.phoenixframework.liveview.data.constants.SheetValues.hidden
import org.phoenixframework.liveview.data.constants.SystemColorValues.Blue
import org.phoenixframework.liveview.data.constants.SystemColorValues.Green
import org.phoenixframework.liveview.data.constants.SystemColorValues.Red
import org.phoenixframework.liveview.data.constants.SystemColorValues.Yellow
import org.phoenixframework.liveview.data.constants.Templates.templateBody
import org.phoenixframework.liveview.data.constants.Templates.templateDragHandle
import org.phoenixframework.liveview.data.constants.Templates.templateSheetContent
import org.phoenixframework.liveview.data.constants.Templates.templateTitle
import org.phoenixframework.liveview.data.constants.Templates.templateTopBar
import org.phoenixframework.liveview.test.base.LiveViewComposableTest

@OptIn(ExperimentalMaterial3Api::class)
class BottomSheetScaffoldShotTest : LiveViewComposableTest() {
    @Test
    fun simpleBottomSheetScaffoldTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                BottomSheetScaffold(
                    sheetContent = {
                        Box(Modifier.fillMaxSize()) {
                            Text(text = "Sheet content")
                        }
                    },
                    content = {
                        Box(Modifier.fillMaxSize()) {
                            Text(text = "Screen content", fontSize = 24.sp)
                        }
                    }
                )
            },
            template = """
                <$bottomSheetScaffold>
                  <$box $attrStyle="$modifierFillMaxSize()" $attrTemplate="$templateSheetContent">
                    <$text>Sheet content</$text>
                  </$box>
                  <$box $attrStyle="$modifierFillMaxSize()" $attrTemplate="$templateBody">
                    <$text $attrFontSize="24">Screen content</$text>
                  </$box>
                </$bottomSheetScaffold>                
                """
        )
    }

    @Test
    fun bssWithSnackbarTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val snackbarHostState = remember { SnackbarHostState() }
                BottomSheetScaffold(
                    snackbarHost = {
                        SnackbarHost(snackbarHostState)
                    },
                    sheetContent = {
                        Box(Modifier.fillMaxSize()) {
                            Text(text = "Sheet content")
                        }
                    },
                    content = {
                        Box(Modifier.fillMaxSize()) {
                            Text(text = "Screen content", fontSize = 24.sp)
                        }
                    }
                )
                LaunchedEffect(Unit) {
                    snackbarHostState.showSnackbar("Hello World")
                }
            },
            template = """
                <$bottomSheetScaffold>
                  <$snackbar $attrMessage="Hello World" />
                  <$box $attrStyle="$modifierFillMaxSize()" $attrTemplate="$templateSheetContent">
                    <$text>Sheet content</$text>
                  </$box>
                  <$box $attrStyle="$modifierFillMaxSize()" $attrTemplate="$templateBody">
                    <$text $attrFontSize="24">Screen content</$text>
                  </$box>
                </$bottomSheetScaffold>                
                """
        )
    }

    @Test
    fun bssWithSheetPeekHeightTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                BottomSheetScaffold(
                    sheetPeekHeight = 60.dp,
                    sheetContent = {
                        Box(Modifier.fillMaxSize()) {
                            Text(text = "Sheet content")
                        }
                    },
                    content = {
                        Box(Modifier.fillMaxSize()) {
                            Text(text = "Screen content", fontSize = 24.sp)
                        }
                    }
                )
            },
            template = """
                <$bottomSheetScaffold $attrSheetPeekHeight="60">
                  <$box $attrStyle="$modifierFillMaxSize()" $attrTemplate="$templateSheetContent">
                    <$text>Sheet content</$text>
                  </$box>
                  <$box $attrStyle="$modifierFillMaxSize()" $attrTemplate="$templateBody">
                    <$text $attrFontSize="24">Screen content</$text>
                  </$box>
                </$bottomSheetScaffold>     
                """
        )
    }

    @Test
    fun bssWithCustomShapeTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                BottomSheetScaffold(
                    sheetShape = RoundedCornerShape(24.dp),
                    sheetPeekHeight = 60.dp,
                    sheetContent = {
                        Box(Modifier.fillMaxSize()) {
                            Text(text = "Sheet content")
                        }
                    },
                    content = {
                        Box(Modifier.fillMaxSize()) {
                            Text(text = "Screen content", fontSize = 24.sp)
                        }
                    }
                )
            },
            template = """
                <$bottomSheetScaffold $attrSheetPeekHeight="60" $attrSheetShape="24">
                  <$box $attrStyle="$modifierFillMaxSize()" $attrTemplate="$templateSheetContent">
                    <$text>Sheet content</$text>
                  </$box>
                  <$box $attrStyle="$modifierFillMaxSize()" $attrTemplate="$templateBody">
                    <$text $attrFontSize="24">Screen content</$text>
                  </$box>
                </$bottomSheetScaffold>              
                """
        )
    }

    @Test
    fun bssWithCustomSheetColorsTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                BottomSheetScaffold(
                    sheetShape = RoundedCornerShape(24.dp),
                    sheetPeekHeight = 60.dp,
                    sheetContainerColor = Color.Yellow,
                    sheetContentColor = Color.Blue,
                    sheetContent = {
                        Box(Modifier.fillMaxSize()) {
                            Text(text = "Sheet content")
                        }
                    },
                    content = {
                        Box(Modifier.fillMaxSize()) {
                            Text(text = "Screen content", fontSize = 24.sp)
                        }
                    }
                )
            },
            template = """
                <$bottomSheetScaffold $attrSheetPeekHeight="60" $attrSheetShape="24" 
                  $attrSheetContainerColor="$Yellow" $attrSheetContentColor="$Blue">
                  <$box $attrStyle="$modifierFillMaxSize()" $attrTemplate="$templateSheetContent">
                    <$text>Sheet content</$text>
                  </$box>
                  <$box $attrStyle="$modifierFillMaxSize()" $attrTemplate="$templateBody">
                    <$text $attrFontSize="24">Screen content</$text>
                  </$box>
                </$bottomSheetScaffold>              
                """
        )
    }

    @Test
    fun bssWithCustomDragHandleTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                BottomSheetScaffold(
                    sheetShape = RoundedCornerShape(24.dp),
                    sheetPeekHeight = 60.dp,
                    sheetContainerColor = Color.Yellow,
                    sheetContentColor = Color.Blue,
                    sheetDragHandle = {
                        Box(Modifier.fillMaxWidth()) {
                            Icon(
                                imageVector = Icons.Filled.ArrowUpward,
                                contentDescription = "",
                                modifier = Modifier.align(Alignment.Center),
                            )
                        }
                    },
                    sheetContent = {
                        Box(Modifier.fillMaxSize()) {
                            Text(text = "Sheet content")
                        }
                    },
                    content = {
                        Box(Modifier.fillMaxSize()) {
                            Text(text = "Screen content", fontSize = 24.sp)
                        }
                    }
                )
            },
            template = """
                <$bottomSheetScaffold $attrSheetPeekHeight="60" $attrSheetShape="24" 
                  $attrSheetContainerColor="$Yellow" $attrSheetContentColor="$Blue">
                  <$box $attrStyle="$modifierFillMaxWidth()" $attrTemplate="$templateDragHandle">
                    <$icon $attrImageVector="$filled:ArrowUpward" $attrStyle="$modifierAlign($typeAlignment.$center)" />
                  </$box>
                  <$box $attrStyle="$modifierFillMaxSize()" $attrTemplate="$templateSheetContent">
                    <$text>Sheet content</$text>
                  </$box>
                  <$box $attrStyle="$modifierFillMaxSize()" $attrTemplate="$templateBody">
                    <$text $attrFontSize="24">Screen content</$text>
                  </$box>
                </$bottomSheetScaffold>              
                """
        )
    }

    @Test
    fun bssWithTopBarTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                BottomSheetScaffold(
                    sheetShape = RoundedCornerShape(24.dp),
                    sheetPeekHeight = 60.dp,
                    sheetContainerColor = Color.Yellow,
                    sheetContentColor = Color.Blue,
                    sheetDragHandle = {
                        Box(Modifier.fillMaxWidth()) {
                            Icon(
                                imageVector = Icons.Filled.ArrowUpward,
                                contentDescription = "",
                                modifier = Modifier.align(Alignment.Center),
                            )
                        }
                    },
                    sheetContent = {
                        Box(Modifier.fillMaxSize()) {
                            Text(text = "Sheet content")
                        }
                    },
                    topBar = {
                        TopAppBar(title = { Text(text = "Title") })
                    },
                    content = {
                        Box(Modifier.fillMaxSize()) {
                            Text(text = "Screen content", fontSize = 24.sp)
                        }
                    }
                )
            },
            template = """
                <$bottomSheetScaffold $attrSheetPeekHeight="60" $attrSheetShape="24" 
                  $attrSheetContainerColor="$Yellow" $attrSheetContentColor="$Blue">
                  <$box $attrStyle="$modifierFillMaxWidth()" $attrTemplate="$templateDragHandle">
                    <$icon $attrImageVector="filled:ArrowUpward" $attrStyle="$modifierAlign($typeAlignment.$center)" />
                  </$box>
                  <$topAppBar $attrTemplate="$templateTopBar">
                    <$text $attrTemplate="$templateTitle">Title</$text>
                  </$topAppBar>
                  <$box $attrStyle="$modifierFillMaxSize()" $attrTemplate="$templateSheetContent">
                    <$text>Sheet content</$text>
                  </$box>
                  <$box $attrStyle="$modifierFillMaxSize()" $attrTemplate="$templateBody">
                    <$text $attrFontSize="24">Screen content</$text>
                  </$box>
                </$bottomSheetScaffold>              
                """
        )
    }

    @Test
    fun bssWithCustomColorsTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                BottomSheetScaffold(
                    sheetShape = RoundedCornerShape(24.dp),
                    sheetPeekHeight = 60.dp,
                    sheetContainerColor = Color.Yellow,
                    sheetContentColor = Color.Blue,
                    sheetDragHandle = {
                        Box(Modifier.fillMaxWidth()) {
                            Icon(
                                imageVector = Icons.Filled.ArrowUpward,
                                contentDescription = "",
                                modifier = Modifier.align(Alignment.Center),
                            )
                        }
                    },
                    sheetContent = {
                        Box(Modifier.fillMaxSize()) {
                            Text(text = "Sheet content")
                        }
                    },
                    topBar = {
                        TopAppBar(title = { Text(text = "Title") })
                    },
                    containerColor = Color.Red,
                    contentColor = Color.Green,
                    content = {
                        Box(Modifier.fillMaxSize()) {
                            Text(text = "Screen content", fontSize = 24.sp)
                        }
                    }
                )
            },
            template = """
                <$bottomSheetScaffold $attrSheetPeekHeight="60" $attrSheetShape="24" 
                  $attrSheetContainerColor="$Yellow" $attrSheetContentColor="$Blue"
                  $attrContainerColor="$Red" $attrContentColor="$Green">
                  <$box $attrStyle="$modifierFillMaxWidth()" $attrTemplate="$templateDragHandle">
                    <$icon $attrImageVector="filled:ArrowUpward" $attrStyle="$modifierAlign($typeAlignment.$center)" />
                  </$box>
                  <$topAppBar $attrTemplate="$templateTopBar">
                    <$text $attrTemplate="$templateTitle">Title</$text>
                  </$topAppBar>
                  <$box $attrStyle="$modifierFillMaxSize()" $attrTemplate="$templateSheetContent">
                    <$text>Sheet content</$text>
                  </$box>
                  <$box $attrStyle="$modifierFillMaxSize()" $attrTemplate="$templateBody">
                    <$text $attrFontSize="24">Screen content</$text>
                  </$box>
                </$bottomSheetScaffold>              
                """
        )
    }

    @Test
    fun bssWithSheetExpandedTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                BottomSheetScaffold(
                    scaffoldState = rememberBottomSheetScaffoldState(
                        bottomSheetState = rememberStandardBottomSheetState(
                            initialValue = SheetValue.Expanded
                        )
                    ),
                    sheetShape = RoundedCornerShape(24.dp),
                    sheetPeekHeight = 60.dp,
                    sheetContainerColor = Color.Yellow,
                    sheetContentColor = Color.Blue,
                    sheetDragHandle = {
                        Box(Modifier.fillMaxWidth()) {
                            Icon(
                                imageVector = Icons.Filled.ArrowUpward,
                                contentDescription = "",
                                modifier = Modifier.align(Alignment.Center),
                            )
                        }
                    },
                    sheetContent = {
                        Box(Modifier.fillMaxSize()) {
                            Text(text = "Sheet content")
                        }
                    },
                    topBar = {
                        TopAppBar(title = { Text(text = "Title") })
                    },
                    containerColor = Color.Red,
                    contentColor = Color.Green,
                    content = {
                        Box(Modifier.fillMaxSize()) {
                            Text(text = "Screen content", fontSize = 24.sp)
                        }
                    }
                )
            },
            template = """
                <$bottomSheetScaffold $attrSheetPeekHeight="60" $attrSheetShape="24" 
                  $attrSheetContainerColor="$Yellow" $attrSheetContentColor="$Blue"
                  $attrContainerColor="$Red" $attrContentColor="$Green"
                  $attrSheetValue="$expanded">
                  <$box $attrStyle="$modifierFillMaxWidth()" $attrTemplate="$templateDragHandle">
                    <$icon $attrImageVector="filled:ArrowUpward" $attrStyle="$modifierAlign($typeAlignment.$center)" />
                  </$box>
                  <$topAppBar $attrTemplate="$templateTopBar">
                    <$text $attrTemplate="$templateTitle">Title</$text>
                  </$topAppBar>
                  <$box $attrStyle="$modifierFillMaxSize()" $attrTemplate="$templateSheetContent">
                    <$text>Sheet content</$text>
                  </$box>
                  <$box $attrStyle="$modifierFillMaxSize()" $attrTemplate="$templateBody">
                    <$text $attrFontSize="24">Screen content</$text>
                  </$box>
                </$bottomSheetScaffold>              
                """
        )
    }

    @Ignore(
        "BottomSheetScaffold is not accepting Hidden as initial values. " +
                "See: https://issuetracker.google.com/issues/299973349"
    )
    @Test
    fun bssWithSheetHiddenTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val state = rememberBottomSheetScaffoldState(
                    bottomSheetState = rememberStandardBottomSheetState(
                        initialValue = SheetValue.Hidden,
                        skipHiddenState = false,
                    )
                )
                BottomSheetScaffold(
                    scaffoldState = state,
                    sheetShape = RoundedCornerShape(24.dp),
                    sheetPeekHeight = 60.dp,
                    sheetContainerColor = Color.Yellow,
                    sheetContentColor = Color.Blue,
                    sheetDragHandle = {
                        Box(Modifier.fillMaxWidth()) {
                            Icon(
                                imageVector = Icons.Filled.ArrowUpward,
                                contentDescription = "",
                                modifier = Modifier.align(Alignment.Center),
                            )
                        }
                    },
                    sheetContent = {
                        Box(Modifier.fillMaxSize()) {
                            Text(text = "Sheet content")
                        }
                    },
                    topBar = {
                        TopAppBar(title = { Text(text = "Title") })
                    },
                    containerColor = Color.Red,
                    contentColor = Color.Green,
                    content = {
                        Box(Modifier.fillMaxSize()) {
                            Text(text = "Screen content", fontSize = 24.sp)
                        }
                    }
                )
            },
            template = """
                <$bottomSheetScaffold $attrSheetPeekHeight="60" $attrSheetShape="24" 
                  $attrSheetContainerColor="$Yellow" $attrSheetContentColor="$Blue"
                  $attrContainerColor="$Red" $attrContentColor="$Green"
                  $attrSheetValue="$hidden" $attrSheetSkipHiddenState="false">
                  <$box $attrStyle="$modifierFillMaxWidth()" $attrTemplate="$templateDragHandle">
                    <$icon $attrImageVector="filled:ArrowUpward" $attrStyle="$modifierAlign($typeAlignment.$center)" />
                  </$box>
                  <$topAppBar $attrTemplate="$templateTopBar">
                    <$text $attrTemplate="$templateTitle">Title</$text>
                  </$topAppBar>
                  <$box $attrStyle="$modifierFillMaxSize()" $attrTemplate="$templateSheetContent">
                    <$text>Sheet content</$text>
                  </$box>
                  <$box $attrStyle="$modifierFillMaxSize()" $attrTemplate="$templateBody">
                    <$text $attrFontSize="24">Screen content</$text>
                  </$box>
                </$bottomSheetScaffold>              
                """
        )
    }
}