package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test

class BadgeBoxShotTest : LiveViewComposableTest() {

    @OptIn(ExperimentalMaterial3Api::class)
    @Test
    fun simpleBadgeBoxTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.size(100.dp)) {
                    BadgedBox(
                        badge = {
                            Badge(
                                containerColor = Color.Blue,
                                contentColor = Color.Red,
                            ) {
                                Text(text = "+99")
                            }
                        },
                    ) {
                        Icon(imageVector = Icons.Filled.Add, contentDescription = "")
                    }
                }
            },
            template = """
                <Box size="100" content-alignment="center">
                  <BadgedBox container-color="#FF0000FF" content-color="#FFFF0000">
                    <Text template="badge">+99</Text>
                    <Icon image-vector="filled:Add" />
                  </BadgedBox>                
                </Box>  
                """.templateToTest(),
        )
    }
}