package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test
import org.phoenixframework.liveview.test.R

class ImageShotTest : LiveViewComposableTest() {
    @Test
    fun simpleImageTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Image(
                    painter = painterResource(id = R.drawable.android_icon),
                    contentDescription = ""
                )
            },
            template = """
                <Image resource="android_icon" />
                """.templateToTest()
        )
    }

    @Test
    fun imageAlignmentTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Column(Modifier.fillMaxSize()) {
                    Image(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        painter = painterResource(id = R.drawable.android_icon),
                        contentDescription = "",
                        alignment = Alignment.TopStart
                    )
                    Image(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        painter = painterResource(id = R.drawable.android_icon),
                        contentDescription = "",
                        alignment = Alignment.TopCenter
                    )
                    Image(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        painter = painterResource(id = R.drawable.android_icon),
                        contentDescription = "",
                        alignment = Alignment.TopEnd
                    )
                }

            },
            template = """
                <Column size="fill">
                    <Image resource="android_icon" weight="1" width="fill" alignment="topStart"/>
                    <Image resource="android_icon" weight="1" width="fill" alignment="topCenter"/>
                    <Image resource="android_icon" weight="1" width="fill" alignment="topEnd"/>
                </Column>
                """.templateToTest()
        )
    }

    @Test
    fun imageScaleTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Column {
                    Image(
                        modifier = Modifier.size(200.dp),
                        painter = painterResource(id = R.drawable.narwin),
                        contentDescription = "",
                        contentScale = ContentScale.Crop
                    )
                    Image(
                        modifier = Modifier.size(200.dp),
                        painter = painterResource(id = R.drawable.narwin),
                        contentDescription = "",
                        contentScale = ContentScale.Fit
                    )
                    Image(
                        modifier = Modifier.size(200.dp),
                        painter = painterResource(id = R.drawable.narwin),
                        contentDescription = "",
                        contentScale = ContentScale.Inside
                    )
                }
            },
            template = """
                <Column>
                    <Image resource="narwin" size="200" content-scale="crop"/>
                    <Image resource="narwin" size="200" content-scale="fit"/>
                    <Image resource="narwin" size="200" content-scale="inside"/>
                </Column>                
                """.templateToTest()
        )
    }

    @Test
    fun imageScale2Test() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Column {
                    Image(
                        modifier = Modifier.size(200.dp),
                        painter = painterResource(id = R.drawable.narwin),
                        contentDescription = "",
                        contentScale = ContentScale.FillHeight
                    )
                    Image(
                        modifier = Modifier.size(200.dp),
                        painter = painterResource(id = R.drawable.narwin),
                        contentDescription = "",
                        contentScale = ContentScale.FillWidth
                    )
                    Image(
                        modifier = Modifier.size(200.dp),
                        painter = painterResource(id = R.drawable.narwin),
                        contentDescription = "",
                        contentScale = ContentScale.FillBounds
                    )
                }
            },
            template = """
                <Column>
                    <Image resource="narwin" size="200" content-scale="fillHeight"/>
                    <Image resource="narwin" size="200" content-scale="fillWidth"/>
                    <Image resource="narwin" size="200" content-scale="fillBounds"/>
                </Column>                
                """.templateToTest()
        )
    }
}