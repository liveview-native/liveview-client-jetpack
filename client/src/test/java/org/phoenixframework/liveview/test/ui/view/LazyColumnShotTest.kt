package org.phoenixframework.liveview.test.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.phoenixframework.liveview.test.base.LiveViewComposableTest
import org.junit.Test
import org.phoenixframework.liveview.data.constants.Attrs.attrHorizontalAlignment
import org.phoenixframework.liveview.data.constants.Attrs.attrReverseLayout
import org.phoenixframework.liveview.data.constants.Attrs.attrStyle
import org.phoenixframework.liveview.data.constants.Attrs.attrVerticalArrangement
import org.phoenixframework.liveview.data.constants.ComposableTypes.lazyColumn
import org.phoenixframework.liveview.data.constants.ComposableTypes.row
import org.phoenixframework.liveview.data.constants.ComposableTypes.text
import org.phoenixframework.liveview.data.constants.HorizontalAlignmentValues
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierFillMaxSize
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierFillMaxWidth
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierPadding
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierWeight
import org.phoenixframework.liveview.data.constants.ModifierTypes.typeDp
import org.phoenixframework.liveview.data.constants.VerticalArrangementValues
import org.phoenixframework.liveview.domain.extensions.optional

class LazyColumnShotTest : LiveViewComposableTest() {

    private fun rowsForTemplate(count: Int, fill: Boolean = true): String {
        return StringBuffer().apply {
            (1..count).forEach {
                append(
                    """
                    <$row $attrStyle="$modifierPadding($typeDp(8))">
                      <$text ${if (fill) "$attrStyle=\"$modifierWeight(1)\"" else ""}>Item ${it}</$text>
                      <$text>#${it}</$text>
                    </$row>     
                    """
                )
            }
        }.toString()
    }

    private fun LazyListScope.rowsForNativeComposable(rowCount: Int, fill: Boolean = true) {
        items((1..rowCount).toList()) { num ->
            Row(Modifier.padding(8.dp)) {
                Text(text = "Item $num", Modifier.optional(fill, Modifier.weight(1f)))
                Text(text = "#$num")
            }
        }
    }

    @Test
    fun simpleLazyColumnTest() {
        val rowCount = 30
        compareNativeComposableWithTemplate(
            nativeComposable = {
                LazyColumn {
                    this.rowsForNativeComposable(rowCount)
                }
            }, template = """
                <$lazyColumn>
                  ${rowsForTemplate(rowCount)}
                </$lazyColumn>
                """
        )
    }

    @Test
    fun lazyColumnReverseTest() {
        val rowCount = 30
        compareNativeComposableWithTemplate(
            nativeComposable = {
                LazyColumn(reverseLayout = true) {
                    this.rowsForNativeComposable(rowCount)
                }
            }, template = """
                <$lazyColumn $attrReverseLayout="true">
                  ${rowsForTemplate(rowCount)}
                </$lazyColumn>
                """
        )
    }

    @Test
    fun lazyColumnVerticalArrangement() {
        val rowCount = 5
        compareNativeComposableWithTemplate(
            nativeComposable = {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceAround,
                ) {
                    this.rowsForNativeComposable(rowCount)
                }
            }, template = """
                <$lazyColumn $attrStyle="$modifierFillMaxSize()"
                  $attrVerticalArrangement="${VerticalArrangementValues.spaceAround}">
                  ${rowsForTemplate(rowCount)}
                </$lazyColumn>
                """
        )
    }

    @Test
    fun lazyColumnHorizontalAlignment() {
        val rowCount = 5
        compareNativeComposableWithTemplate(
            nativeComposable = {
                LazyColumn(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.End,
                ) {
                    this.rowsForNativeComposable(rowCount, false)
                }
            }, template = """
                <$lazyColumn $attrStyle="$modifierFillMaxWidth()" 
                  $attrHorizontalAlignment="${HorizontalAlignmentValues.end}">
                  ${rowsForTemplate(rowCount, false)}
                </$lazyColumn>
                """
        )
    }
}