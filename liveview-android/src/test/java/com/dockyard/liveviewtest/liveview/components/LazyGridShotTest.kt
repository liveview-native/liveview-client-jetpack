package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test
import org.phoenixframework.liveview.data.constants.Attrs.attrColumns
import org.phoenixframework.liveview.data.constants.Attrs.attrCount
import org.phoenixframework.liveview.data.constants.Attrs.attrHorizontalAlignment
import org.phoenixframework.liveview.data.constants.Attrs.attrHorizontalArrangement
import org.phoenixframework.liveview.data.constants.Attrs.attrMinSize
import org.phoenixframework.liveview.data.constants.Attrs.attrReverseLayout
import org.phoenixframework.liveview.data.constants.Attrs.attrRows
import org.phoenixframework.liveview.data.constants.Attrs.attrStyle
import org.phoenixframework.liveview.data.constants.Attrs.attrType
import org.phoenixframework.liveview.data.constants.Attrs.attrVerticalArrangement
import org.phoenixframework.liveview.data.constants.HorizontalAlignmentValues
import org.phoenixframework.liveview.data.constants.HorizontalArrangementValues
import org.phoenixframework.liveview.data.constants.LazyGridColumnTypeValues.adaptive
import org.phoenixframework.liveview.data.constants.LazyGridColumnTypeValues.fixed
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierFillMaxSize
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierFillMaxWidth
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierSize
import org.phoenixframework.liveview.data.constants.ModifierTypes.typeDp
import org.phoenixframework.liveview.data.constants.VerticalArrangementValues
import org.phoenixframework.liveview.data.constants.ComposableTypes.column
import org.phoenixframework.liveview.data.constants.ComposableTypes.lazyHorizontalGrid
import org.phoenixframework.liveview.data.constants.ComposableTypes.lazyVerticalGrid
import org.phoenixframework.liveview.data.constants.ComposableTypes.text

class LazyGridShotTest : LiveViewComposableTest() {

    private fun cellsForTemplate(count: Int): String {
        return StringBuffer().apply {
            (1..count).forEach {
                append(
                    """
                    <$column 
                      $attrVerticalArrangement="${VerticalArrangementValues.center}" 
                      $attrHorizontalAlignment="${HorizontalAlignmentValues.centerHorizontally}" 
                      $attrStyle="$modifierSize($typeDp(100))">
                      <$text>Item ${it}</$text>
                      <$text>#${it}</$text>
                    </$column>     
                    """
                )
            }
        }.toString()
    }

    private fun LazyGridScope.cellForComposable(cellCount: Int) {
        items((1..cellCount).toList()) { num ->
            Column(
                modifier = Modifier.size(100.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(text = "Item $num")
                Text(text = "#$num")
            }
        }
    }

    @Test
    fun simpleLazyVerticalGridTest() {
        val cellCount = 30
        compareNativeComposableWithTemplate(
            nativeComposable = {
                LazyVerticalGrid(columns = GridCells.Fixed(3)) {
                    cellForComposable(cellCount)
                }
            }, template = """
                <$lazyVerticalGrid $attrColumns="{'$attrType': '$fixed', '$attrCount': '3'}">
                  ${cellsForTemplate(cellCount)}
                </$lazyVerticalGrid>
                """
        )
    }

    @Test
    fun lazyVerticalGridReverseTest() {
        val cellCount = 30
        compareNativeComposableWithTemplate(
            nativeComposable = {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    reverseLayout = true,
                ) {
                    cellForComposable(cellCount)
                }
            }, template = """
                <$lazyVerticalGrid $attrReverseLayout="true" $attrColumns="{'$attrType': '$fixed', '$attrCount': '3'}">
                  ${cellsForTemplate(cellCount)}
                </$lazyVerticalGrid>
                """
        )
    }

    @Test
    fun lazyVerticalGridVerticalArrangement() {
        val cellCount = 5
        compareNativeComposableWithTemplate(
            nativeComposable = {
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    columns = GridCells.Fixed(3),
                    verticalArrangement = Arrangement.Center
                ) {
                    cellForComposable(cellCount)
                }
            }, template = """
                <$lazyVerticalGrid 
                  $attrStyle="$modifierFillMaxSize()"
                  $attrVerticalArrangement="${VerticalArrangementValues.center}" 
                  $attrColumns="{'$attrType': '$fixed', '$attrCount': '3'}">
                  ${cellsForTemplate(cellCount)}
                </$lazyVerticalGrid>
                """
        )
    }

    @Test
    fun lazyVerticalGridHorizontalArrangement() {
        val cellCount = 2
        compareNativeComposableWithTemplate(
            nativeComposable = {
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    columns = GridCells.Adaptive(100.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    cellForComposable(cellCount)
                }
            }, template = """
                <$lazyVerticalGrid 
                  $attrStyle="$modifierFillMaxSize()" 
                  $attrVerticalArrangement="${VerticalArrangementValues.center}" 
                  $attrHorizontalArrangement="${HorizontalArrangementValues.spaceBetween}" 
                  $attrColumns="{'$attrType': '$adaptive', '$attrMinSize': '100'}">
                  ${cellsForTemplate(cellCount)}
                </$lazyVerticalGrid>
                """
        )
    }

    @Test
    fun simpleLazyHorizontalGridTest() {
        val cellCount = 30
        compareNativeComposableWithTemplate(
            nativeComposable = {
                LazyHorizontalGrid(rows = GridCells.Fixed(3)) {
                    cellForComposable(cellCount)
                }
            }, template = """
                <$lazyHorizontalGrid $attrRows="{'$attrType': '$fixed', '$attrCount': '3'}">
                  ${cellsForTemplate(cellCount)}
                </$lazyHorizontalGrid>
                """
        )
    }

    @Test
    fun lazyHorizontalGridReverseTest() {
        val cellCount = 30
        compareNativeComposableWithTemplate(
            nativeComposable = {
                LazyHorizontalGrid(
                    rows = GridCells.Fixed(3),
                    reverseLayout = true,
                ) {
                    cellForComposable(cellCount)
                }
            }, template = """
                <$lazyHorizontalGrid $attrReverseLayout="true" $attrRows="{'$attrType': '$fixed', '$attrCount': '3'}">
                  ${cellsForTemplate(cellCount)}
                </$lazyHorizontalGrid>
                """
        )
    }

    @Test
    fun lazyHorizontalGridVerticalArrangement() {
        val cellCount = 5
        compareNativeComposableWithTemplate(
            nativeComposable = {
                LazyHorizontalGrid(
                    modifier = Modifier.fillMaxSize(),
                    rows = GridCells.Fixed(3),
                    verticalArrangement = Arrangement.Center
                ) {
                    cellForComposable(cellCount)
                }
            }, template = """
                <$lazyHorizontalGrid 
                  $attrStyle="$modifierFillMaxSize()"
                  $attrVerticalArrangement="${VerticalArrangementValues.center}" 
                  $attrColumns="{'$attrType': '$fixed', '$attrCount': '3'}">
                  ${cellsForTemplate(cellCount)}
                </$lazyHorizontalGrid>
                """
        )
    }

    @Test
    fun lazyHorizontalGridHorizontalArrangement() {
        val cellCount = 2
        compareNativeComposableWithTemplate(
            nativeComposable = {
                LazyHorizontalGrid(
                    modifier = Modifier.fillMaxWidth(),
                    rows = GridCells.Adaptive(100.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    cellForComposable(cellCount)
                }
            }, template = """
                <$lazyHorizontalGrid 
                  $attrStyle="$modifierFillMaxWidth()"
                  $attrVerticalArrangement="${VerticalArrangementValues.center}" 
                  $attrHorizontalArrangement="${HorizontalArrangementValues.spaceBetween}" 
                  $attrColumns="{'$attrType': '$adaptive', '$attrMinSize': '100'}">
                  ${cellsForTemplate(cellCount)}
                </$lazyHorizontalGrid>
                """
        )
    }
}