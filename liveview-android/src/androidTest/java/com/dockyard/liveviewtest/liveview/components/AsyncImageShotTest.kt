package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test

class AsyncImageShotTest : LiveViewComposableTest() {
    @Test
    fun simpleAsyncImageLoadingTest() {
        val url = "https://assets.dockyard.com/images/narwin-home-flare.jpg"
        compareNativeComposableWithTemplate(
            delayBeforeScreenshot = 2000,
            nativeComposable = {
                Box(modifier = Modifier.fillMaxWidth()) {
                    AsyncImage(
                        model = url,
                        contentDescription = "",
                        alpha = 0.5f,
                        contentScale = ContentScale.FillHeight,
                    )
                }
            },
            template = """
                <Box width="fill">
                  <AsyncImage url="$url" alpha="0.5" content-scale="fillHeight" />
                </Box>    
                """
        )
    }
}