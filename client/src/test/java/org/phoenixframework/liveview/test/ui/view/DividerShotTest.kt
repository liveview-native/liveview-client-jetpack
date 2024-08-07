package org.phoenixframework.liveview.test.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.junit.Test
import org.phoenixframework.liveview.constants.Attrs.attrColor
import org.phoenixframework.liveview.constants.Attrs.attrStyle
import org.phoenixframework.liveview.constants.Attrs.attrThickness
import org.phoenixframework.liveview.constants.ComposableTypes.column
import org.phoenixframework.liveview.constants.ComposableTypes.horizontalDivider
import org.phoenixframework.liveview.constants.ComposableTypes.row
import org.phoenixframework.liveview.constants.ComposableTypes.text
import org.phoenixframework.liveview.constants.ComposableTypes.verticalDivider
import org.phoenixframework.liveview.constants.ModifierNames.modifierHeight
import org.phoenixframework.liveview.constants.ModifierNames.modifierWidth
import org.phoenixframework.liveview.constants.ModifierTypes.typeDp
import org.phoenixframework.liveview.constants.ModifierTypes.typeIntrinsicSize
import org.phoenixframework.liveview.test.base.LiveViewComposableTest

class DividerShotTest : LiveViewComposableTest() {

    @Test
    fun simpleHorizontalDividerTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Column(Modifier.width(100.dp)) {
                    Text(text = "Line 1")
                    HorizontalDivider()
                    Text(text = "Line 2")
                }
            },
            template = """
                <$column $attrStyle="$modifierWidth($typeDp(100))">
                  <$text>Line 1</$text>
                  <$horizontalDivider />
                  <$text>Line 2</$text>
                </$column>
                """
        )
    }

    @Test
    fun simpleVerticalDividerTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Row(
                    Modifier
                        .width(100.dp)
                        .height(IntrinsicSize.Min)
                ) {
                    Text(text = "Cell 1")
                    VerticalDivider()
                    Text(text = "Cell 2")
                }
            },
            template = """
                <$row $attrStyle="$modifierWidth($typeDp(100));$modifierHeight($typeIntrinsicSize.Min)">
                  <$text>Cell 1</$text>
                  <$verticalDivider />
                  <$text>Cell 2</$text>
                </$row>
                """
        )
    }

    @Test
    fun customHorizontalDividerTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Column(Modifier.width(100.dp)) {
                    Text(text = "Line 1")
                    HorizontalDivider(thickness = 2.dp, color = Color.Red)
                    Text(text = "Line 2")
                }
            },
            template = """
                <$column $attrStyle="$modifierWidth($typeDp(100))">
                  <$text>Line 1</$text>
                  <$horizontalDivider $attrThickness="2" $attrColor="#FFFF0000" />
                  <$text>Line 2</$text>
                </$column>
                """
        )
    }

    @Test
    fun customVerticalDividerTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Row(
                    Modifier
                        .width(100.dp)
                        .height(IntrinsicSize.Min)
                ) {
                    Text(text = "Cell 1")
                    VerticalDivider(thickness = 2.dp, color = Color.Red)
                    Text(text = "Cell 2")
                }
            },
            template = """
                <$row $attrStyle="$modifierWidth($typeDp(100));$modifierHeight($typeIntrinsicSize.Min)">
                  <$text>Cell 1</$text>
                  <$verticalDivider $attrThickness="2" $attrColor="#FFFF0000" />
                  <$text>Cell 2</$text>
                </$row>
                """
        )
    }
}