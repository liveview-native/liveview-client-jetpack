package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test
import org.phoenixframework.liveview.data.constants.Attrs.attrHeight
import org.phoenixframework.liveview.data.constants.Attrs.attrHorizontalArrangement
import org.phoenixframework.liveview.data.constants.Attrs.attrPadding
import org.phoenixframework.liveview.data.constants.Attrs.attrReverseLayout
import org.phoenixframework.liveview.data.constants.Attrs.attrVerticalAlignment
import org.phoenixframework.liveview.data.constants.Attrs.attrWeight
import org.phoenixframework.liveview.data.constants.Attrs.attrWidth
import org.phoenixframework.liveview.data.constants.HorizontalArrangementValues
import org.phoenixframework.liveview.data.constants.SizeValues.fill
import org.phoenixframework.liveview.data.constants.VerticalAlignmentValues
import org.phoenixframework.liveview.domain.base.ComposableTypes.column
import org.phoenixframework.liveview.domain.base.ComposableTypes.lazyRow
import org.phoenixframework.liveview.domain.base.ComposableTypes.text
import org.phoenixframework.liveview.domain.extensions.optional

class LazyRowShotTest : LiveViewComposableTest() {

    private fun cellForTemplate(count: Int, fill: Boolean = true): String {
        return StringBuffer().apply {
            (1..count).forEach {
                append(
                    """
                    <$column $attrPadding="8">
                      <$text ${if (fill) "$attrWeight=\"1\"" else ""}>Item ${it}</$text>
                      <$text>#${it}</$text>
                    </$column>     
                    """
                )
            }
        }.toString()
    }

    private fun LazyListScope.cellsForComposable(rowCount: Int, fill: Boolean = true) {
        items((1..rowCount).toList()) { num ->
            Column(Modifier.padding(8.dp)) {
                Text(text = "Item $num", Modifier.optional(fill, Modifier.weight(1f)))
                Text(text = "#$num")
            }
        }
    }

    @Test
    fun simpleLazyRowTest() {
        val rowCount = 20
        compareNativeComposableWithTemplate(
            nativeComposable = {
                LazyRow(Modifier.height(150.dp)) {
                    cellsForComposable(rowCount)
                }
            }, template = """
                <$lazyRow $attrHeight="150">
                  ${cellForTemplate(rowCount)}
                </$lazyRow>
                """
        )
    }

    @Test
    fun lazyRowReverseTest() {
        val rowCount = 30
        compareNativeComposableWithTemplate(
            nativeComposable = {
                LazyRow(Modifier.height(150.dp), reverseLayout = true) {
                    cellsForComposable(rowCount)
                }
            }, template = """
                <$lazyRow $attrHeight="150" $attrReverseLayout="true">
                  ${cellForTemplate(rowCount)}
                </$lazyRow>
                """
        )
    }

    @Test
    fun lazyRowHorizontalArrangement() {
        val rowCount = 5
        compareNativeComposableWithTemplate(
            nativeComposable = {
                LazyRow(
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                ) {
                    cellsForComposable(rowCount)
                }
            }, template = """
                <$lazyRow 
                  $attrHeight="100" $attrWidth="$fill" 
                  $attrHorizontalArrangement="${HorizontalArrangementValues.spaceAround}">
                  ${cellForTemplate(rowCount)}
                </$lazyRow>
                """
        )
    }

    @Test
    fun lazyRowVerticalAlignment() {
        val rowCount = 5
        compareNativeComposableWithTemplate(
            nativeComposable = {
                LazyRow(
                    Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    verticalAlignment = Alignment.Bottom,
                ) {
                    cellsForComposable(rowCount, false)
                }
            }, template = """
                <$lazyRow 
                  $attrWidth="$fill" $attrHeight="200" 
                  $attrVerticalAlignment="${VerticalAlignmentValues.bottom}">
                  ${cellForTemplate(rowCount, false)}
                </$lazyRow>
                """
        )
    }
}