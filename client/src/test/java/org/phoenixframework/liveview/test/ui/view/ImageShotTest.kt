package org.phoenixframework.liveview.test.ui.view

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
import org.phoenixframework.liveview.test.base.LiveViewComposableTest
import org.junit.Test
import org.phoenixframework.liveview.data.constants.AlignmentValues
import org.phoenixframework.liveview.data.constants.Attrs.attrAlignment
import org.phoenixframework.liveview.data.constants.Attrs.attrContentScale
import org.phoenixframework.liveview.data.constants.Attrs.attrResource
import org.phoenixframework.liveview.data.constants.Attrs.attrStyle
import org.phoenixframework.liveview.data.constants.ComposableTypes.column
import org.phoenixframework.liveview.data.constants.ComposableTypes.image
import org.phoenixframework.liveview.data.constants.ContentScaleValues
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierFillMaxSize
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierFillMaxWidth
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierSize
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierWeight
import org.phoenixframework.liveview.data.constants.ModifierTypes.typeDp
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
                <$image $attrResource="android_icon" />
                """
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
                <$column $attrStyle="$modifierFillMaxSize()">
                  <$image $attrResource="android_icon" $attrStyle="$modifierFillMaxWidth();$modifierWeight(1)" 
                    $attrAlignment="${AlignmentValues.topStart}"/>
                  <$image $attrResource="android_icon" $attrStyle="$modifierFillMaxWidth();$modifierWeight(1)" 
                    $attrAlignment="${AlignmentValues.topCenter}"/>
                  <$image $attrResource="android_icon" $attrStyle="$modifierFillMaxWidth();$modifierWeight(1)" 
                    $attrAlignment="${AlignmentValues.topEnd}"/>
                </$column>
                """
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
                <$column>
                  <$image $attrResource="narwin" $attrStyle="$modifierSize($typeDp(200))" 
                    $attrContentScale="${ContentScaleValues.crop}"/>
                  <$image $attrResource="narwin" $attrStyle="$modifierSize($typeDp(200))" 
                    $attrContentScale="${ContentScaleValues.fit}"/>
                  <$image $attrResource="narwin" $attrStyle="$modifierSize($typeDp(200))"
                    $attrContentScale="${ContentScaleValues.inside}"/>
                </$column>                
                """
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
                <$column>
                  <$image $attrResource="narwin" $attrStyle="$modifierSize($typeDp(200))" 
                    $attrContentScale="${ContentScaleValues.fillHeight}"/>
                  <$image $attrResource="narwin" $attrStyle="$modifierSize($typeDp(200))"
                    $attrContentScale="${ContentScaleValues.fillWidth}"/>
                  <$image $attrResource="narwin" $attrStyle="$modifierSize($typeDp(200))" 
                    $attrContentScale="${ContentScaleValues.fillBounds}"/>
                </$column>                
                """
        )
    }
}