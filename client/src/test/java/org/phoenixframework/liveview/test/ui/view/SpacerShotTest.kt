package org.phoenixframework.liveview.test.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.junit.Test
import org.phoenixframework.liveview.constants.Attrs.attrHorizontalAlignment
import org.phoenixframework.liveview.constants.Attrs.attrStyle
import org.phoenixframework.liveview.constants.Attrs.attrVerticalAlignment
import org.phoenixframework.liveview.constants.ComposableTypes.column
import org.phoenixframework.liveview.constants.ComposableTypes.row
import org.phoenixframework.liveview.constants.ComposableTypes.spacer
import org.phoenixframework.liveview.constants.ComposableTypes.text
import org.phoenixframework.liveview.constants.HorizontalAlignmentValues.centerHorizontally
import org.phoenixframework.liveview.constants.ModifierNames.modifierSize
import org.phoenixframework.liveview.constants.ModifierTypes.typeDp
import org.phoenixframework.liveview.constants.VerticalAlignmentValues.centerVertically
import org.phoenixframework.liveview.test.base.LiveViewComposableTest

class SpacerShotTest : LiveViewComposableTest() {
    @Test
    fun simpleSpacerTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Left")
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Top")
                        Spacer(modifier = Modifier.size(80.dp))
                        Text("Bottom")
                    }
                    Text("Right")
                }
            },
            template = """
                <$row $attrVerticalAlignment="$centerVertically">
                    <$text>Left</$text>
                    <$column $attrHorizontalAlignment="$centerHorizontally">
                        <$text>Top</$text>
                        <$spacer $attrStyle="$modifierSize($typeDp(80))" />
                        <$text>Bottom</$text>
                    </$column>
                    <$text>Right</$text>
                </$row>
                """
        )
    }
}
