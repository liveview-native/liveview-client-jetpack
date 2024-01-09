package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test

class LazyGridShotTest : LiveViewComposableTest() {

    private fun cellsForTemplate(count: Int): String {
        return StringBuffer().apply {
            (1..count).forEach {
                append(
                    """
                    <Column vertical-arrangement="center" horizontal-alignment="center" size="100">
                      <Text>Item ${it}</Text>
                      <Text>#${it}</Text>
                    </Column>     
                    """
                )
            }
        }.toString()
    }

    @Test
    fun simpleLazyVerticalGridTest() {
        val cellCount = 30
        compareNativeComposableWithTemplate(
            nativeComposable = {
                LazyVerticalGrid(columns = GridCells.Fixed(3)) {
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
            }, template = """
                <LazyVerticalGrid columns="{'type': 'fixed', 'count': '3'}">
                  ${cellsForTemplate(cellCount)}
                </LazyVerticalGrid>
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
            }, template = """
                <LazyVerticalGrid reverse-layout="true" columns="{'type': 'fixed', 'count': '3'}">
                  ${cellsForTemplate(cellCount)}
                </LazyVerticalGrid>
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
            }, template = """
                <LazyVerticalGrid 
                  size="fill" 
                  vertical-arrangement="center" 
                  columns="{'type': 'fixed', 'count': '3'}">
                  ${cellsForTemplate(cellCount)}
                </LazyVerticalGrid>
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
            }, template = """
                <LazyVerticalGrid 
                  size="fill" 
                  vertical-arrangement="center" 
                  horizontal-arrangement="spaceBetween" 
                  columns="{'type': 'adaptive', 'minSize': '100'}">
                  ${cellsForTemplate(cellCount)}
                </LazyVerticalGrid>
                """
        )
    }


    @Test
    fun simpleLazyHorizontalGridTest() {
        val cellCount = 30
        compareNativeComposableWithTemplate(
            nativeComposable = {
                LazyHorizontalGrid(rows = GridCells.Fixed(3)) {
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
            }, template = """
                <LazyHorizontalGrid rows="{'type': 'fixed', 'count': '3'}">
                  ${cellsForTemplate(cellCount)}
                </LazyHorizontalGrid>
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
            }, template = """
                <LazyHorizontalGrid reverse-layout="true" rows="{'type': 'fixed', 'count': '3'}">
                  ${cellsForTemplate(cellCount)}
                </LazyHorizontalGrid>
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
            }, template = """
                <LazyHorizontalGrid 
                  size="fill" 
                  vertical-arrangement="center" 
                  columns="{'type': 'fixed', 'count': '3'}">
                  ${cellsForTemplate(cellCount)}
                </LazyHorizontalGrid>
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
            }, template = """
                <LazyHorizontalGrid 
                  width="fill" 
                  vertical-arrangement="center" 
                  horizontal-arrangement="spaceBetween" 
                  columns="{'type': 'adaptive', 'minSize': '100'}">
                  ${cellsForTemplate(cellCount)}
                </LazyHorizontalGrid>
                """
        )
    }
}