package org.phoenixframework.liveview.test.ui.view

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.phoenixframework.liveview.test.base.LiveViewComposableTest
import org.junit.Test
import org.phoenixframework.liveview.data.constants.AlignmentValues.bottomEnd
import org.phoenixframework.liveview.data.constants.AlignmentValues.bottomStart
import org.phoenixframework.liveview.data.constants.AlignmentValues.center
import org.phoenixframework.liveview.data.constants.AlignmentValues.topEnd
import org.phoenixframework.liveview.data.constants.AlignmentValues.topStart
import org.phoenixframework.liveview.data.constants.Attrs.attrImageVector
import org.phoenixframework.liveview.data.constants.Attrs.attrStyle
import org.phoenixframework.liveview.data.constants.Attrs.attrText
import org.phoenixframework.liveview.data.constants.Attrs.attrTextAlign
import org.phoenixframework.liveview.data.constants.ComposableTypes.box
import org.phoenixframework.liveview.data.constants.ComposableTypes.icon
import org.phoenixframework.liveview.data.constants.ComposableTypes.text
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierAlign
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierBackground
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierHeight
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierMatchParentSize
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierSize
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierWidth
import org.phoenixframework.liveview.data.constants.ModifierTypes.typeAlignment
import org.phoenixframework.liveview.data.constants.ModifierTypes.typeColor
import org.phoenixframework.liveview.data.constants.ModifierTypes.typeDp
import org.phoenixframework.liveview.data.constants.SystemColorValues.Blue
import org.phoenixframework.liveview.data.constants.SystemColorValues.Red
import org.phoenixframework.liveview.data.constants.SystemColorValues.Yellow
import org.phoenixframework.liveview.data.constants.TextAlignValues

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
              <$box $attrStyle="$modifierSize($typeDp(100));$modifierBackground($typeColor.$Red)">
                <$icon $attrImageVector="filled:Android" $attrStyle="$modifierAlign($typeAlignment.$topStart)" />
                <$text $attrStyle="$modifierAlign($typeAlignment.$center)">Text</$text>
                <$icon $attrImageVector="filled:Share" $attrStyle="$modifierAlign($typeAlignment.$bottomEnd)" />
              </$box>                
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
              <$box $attrStyle="$modifierWidth($typeDp(150));$modifierHeight($typeDp(100));$modifierBackground($typeColor.$Blue)">
                <$icon $attrImageVector="filled:Android" $attrStyle="$modifierAlign($typeAlignment.$topEnd)" />
                <$text $attrStyle="$modifierAlign($typeAlignment.$center)">Text Center</$text>
                <$icon $attrImageVector="filled:Share" $attrStyle="$modifierAlign($typeAlignment.$bottomStart)" />
              </$box>                
            """
        )
    }

    @Test
    fun boxWithChildMaxParentSizedTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Box(
                    Modifier
                        .size(200.dp)
                        .background(Color.Yellow)
                ) {
                    Text(
                        text = "MaxParentSize",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.matchParentSize()
                    )
                }
            },
            template = """
                <$box $attrStyle="$modifierSize($typeDp(200));$modifierBackground($typeColor.$Yellow)">
                    <$text
                        $attrText="MaxParentSize" 
                        $attrTextAlign="${TextAlignValues.center}"
                        $attrStyle="$modifierMatchParentSize()" />
                </$box>    
            """
        )
    }
}