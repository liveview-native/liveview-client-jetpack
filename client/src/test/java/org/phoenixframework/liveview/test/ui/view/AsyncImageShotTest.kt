package org.phoenixframework.liveview.test.ui.view

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
import org.junit.Before
import org.junit.Test
import org.phoenixframework.liveview.data.constants.Attrs.attrAlpha
import org.phoenixframework.liveview.data.constants.Attrs.attrContentScale
import org.phoenixframework.liveview.data.constants.Attrs.attrStyle
import org.phoenixframework.liveview.data.constants.Attrs.attrUrl
import org.phoenixframework.liveview.data.constants.ComposableTypes.asyncImage
import org.phoenixframework.liveview.data.constants.ComposableTypes.box
import org.phoenixframework.liveview.data.constants.ContentScaleValues.fillHeight
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierFillMaxWidth
import org.phoenixframework.liveview.test.base.LiveViewComposableTest

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
                <$box $attrStyle="$modifierFillMaxWidth()">
                  <$asyncImage $attrUrl="$url" $attrAlpha="0.5" $attrContentScale="$fillHeight" />
                </$box>    
                """
        )
    }
}