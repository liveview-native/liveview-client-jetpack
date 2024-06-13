package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test
import org.phoenixframework.liveview.data.constants.Attrs.attrHorizontalAlignment
import org.phoenixframework.liveview.data.constants.Attrs.attrStyle
import org.phoenixframework.liveview.data.constants.Attrs.attrVerticalAlignment
import org.phoenixframework.liveview.data.constants.HorizontalAlignmentValues.centerHorizontally
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierSize
import org.phoenixframework.liveview.data.constants.ModifierTypes.typeDp
import org.phoenixframework.liveview.data.constants.VerticalAlignmentValues.centerVertically
import org.phoenixframework.liveview.data.constants.ComposableTypes.column
import org.phoenixframework.liveview.data.constants.ComposableTypes.row
import org.phoenixframework.liveview.data.constants.ComposableTypes.spacer
import org.phoenixframework.liveview.data.constants.ComposableTypes.text

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
