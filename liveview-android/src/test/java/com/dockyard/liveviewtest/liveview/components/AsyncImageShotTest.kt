package com.dockyard.liveviewtest.liveview.components

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.Coil
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import coil.test.FakeImageLoaderEngine
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Before
import org.junit.Test

class AsyncImageShotTest : LiveViewComposableTest() {
    private val url = "https://assets.dockyard.com/images/narwin-home-flare.jpg"

    @OptIn(ExperimentalCoilApi::class)
    @Before
    fun before() {
        // https://coil-kt.github.io/coil/testing/
        val engine = FakeImageLoaderEngine.Builder()
            .intercept(url, ColorDrawable(Color.RED))
            .default(ColorDrawable(Color.BLUE))
            .build()
        val imageLoader = ImageLoader.Builder(composeRule.activity)
            .components { add(engine) }
            .build()
        Coil.setImageLoader(imageLoader)
    }

    @Test
    fun simpleAsyncImageLoadingTest() {
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