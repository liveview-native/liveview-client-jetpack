package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.HorizontalDistribute
import androidx.compose.material.icons.filled.House
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.VerticalDistribute
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test
import org.phoenixframework.liveview.data.constants.Attrs.attrContainerColor
import org.phoenixframework.liveview.data.constants.Attrs.attrContentColor
import org.phoenixframework.liveview.data.constants.Attrs.attrImageVector
import org.phoenixframework.liveview.data.constants.Attrs.attrShape
import org.phoenixframework.liveview.data.constants.Attrs.attrTemplate
import org.phoenixframework.liveview.data.constants.Templates.templateAction
import org.phoenixframework.liveview.data.constants.Templates.templateFab
import org.phoenixframework.liveview.domain.base.ComposableTypes.bottomAppBar
import org.phoenixframework.liveview.domain.base.ComposableTypes.floatingActionButton
import org.phoenixframework.liveview.domain.base.ComposableTypes.icon
import org.phoenixframework.liveview.domain.base.ComposableTypes.iconButton

class BottomAppBarShotTest : LiveViewComposableTest() {
    @Test
    fun simpleBottomAppBarTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                BottomAppBar(
                    actions = {
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Filled.HorizontalDistribute,
                                contentDescription = ""
                            )
                        }
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Filled.VerticalDistribute,
                                contentDescription = ""
                            )
                        }
                    },
                    floatingActionButton = {
                        FloatingActionButton(onClick = {}, shape = RoundedCornerShape(12.dp)) {
                            Icon(imageVector = Icons.Filled.Add, contentDescription = "")
                        }
                    }
                )
            },
            template = """
                <$bottomAppBar>
                  <$iconButton $attrTemplate="$templateAction">
                    <$icon $attrImageVector="filled:HorizontalDistribute" />
                  </$iconButton>
                  <$iconButton $attrTemplate="$templateAction">
                    <$icon $attrImageVector="filled:VerticalDistribute" />
                  </$iconButton>
                  <$floatingActionButton $attrTemplate="$templateFab" $attrShape="12">
                    <$icon $attrImageVector="filled:Add"/>
                  </$floatingActionButton>
                </$bottomAppBar>
                """
        )
    }

    @Test
    fun bottomAppBarWithCustomColors() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                BottomAppBar(
                    containerColor = Color.Gray,
                    contentColor = Color.DarkGray,
                    actions = {
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Filled.House,
                                contentDescription = ""
                            )
                        }
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = ""
                            )
                        }
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Filled.Settings,
                                contentDescription = ""
                            )
                        }
                    },
                    floatingActionButton = {
                        FloatingActionButton(onClick = {}) {
                            Icon(imageVector = Icons.Filled.Delete, contentDescription = "")
                        }
                    }
                )
            },
            template = """
                <$bottomAppBar $attrContainerColor="#FF888888" $attrContentColor="#FF444444">
                  <$iconButton $attrTemplate="$templateAction">
                    <$icon $attrImageVector="filled:House" />
                  </$iconButton>
                  <$iconButton $attrTemplate="$templateAction">
                    <$icon $attrImageVector="filled:Search" />
                  </$iconButton>                  
                  <$iconButton $attrTemplate="$templateAction">
                    <$icon $attrImageVector="filled:Settings" />
                  </$iconButton>
                  <$floatingActionButton $attrTemplate="$templateFab">
                    <$icon $attrImageVector="filled:Delete"/>
                  </$floatingActionButton>
                </$bottomAppBar>
                """
        )
    }
}