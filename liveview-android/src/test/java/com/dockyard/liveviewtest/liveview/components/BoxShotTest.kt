package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test

class BoxShotTest : LiveViewComposableTest() {
    @Test
    fun boxWithChildrenAligned1Test() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Box(
                    Modifier
                        .size(100.dp)
                        .background(Color.Red)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Android,
                        contentDescription = "",
                        modifier = Modifier.align(
                            Alignment.TopStart
                        )
                    )
                    Text(text = "Text", Modifier.align(Alignment.Center))
                    Icon(
                        imageVector = Icons.Filled.Share,
                        contentDescription = "",
                        modifier = Modifier.align(
                            Alignment.BottomEnd
                        )
                    )
                }
            },
            template = """
              <Box size="100" background="#FFFF0000">
                <Icon image-vector="filled:Android" align="topStart"/>
                <Text align="center">Text</Text>
                <Icon image-vector="filled:Share" align="bottomEnd"/>
              </Box>                
            """
        )
    }

    @Test
    fun boxWithChildrenAligned2Test() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Box(
                    Modifier
                        .width(150.dp)
                        .height(100.dp)
                        .background(Color.Blue)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Android,
                        contentDescription = "",
                        modifier = Modifier.align(
                            Alignment.TopEnd
                        )
                    )
                    Text(text = "Text Center", Modifier.align(Alignment.Center))
                    Icon(
                        imageVector = Icons.Filled.Share,
                        contentDescription = "",
                        modifier = Modifier.align(
                            Alignment.BottomStart
                        )
                    )
                }
            },
            template = """
              <Box width="150" height="100" background="#FF0000FF">
                <Icon image-vector="filled:Android" align="topEnd"/>
                <Text align="center">Text Center</Text>
                <Icon image-vector="filled:Share" align="bottomStart"/>
              </Box>                
            """
        )
    }
}