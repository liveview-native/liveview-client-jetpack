package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test

class LazyRowShotTest : LiveViewComposableTest() {

    private fun cellForTemplate(count: Int, fill: Boolean = true): String {
        return StringBuffer().apply {
            (1..count).forEach {
                append(
                    """
                    <Column padding="8">
                      <Text ${if (fill) "weight=\"1\"" else ""}>Item ${it}</Text>
                      <Text>#${it}</Text>
                    </Column>     
                    """
                )
            }
        }.toString()
    }

    @Test
    fun simpleLazyRowTest() {
        val rowCount = 20
        compareNativeComposableWithTemplate(
            nativeComposable = {
                LazyRow(Modifier.height(150.dp)) {
                    items((1..rowCount).toList()) { num ->
                        Column(Modifier.padding(8.dp)) {
                            Text(text = "Item $num", Modifier.weight(1f))
                            Text(text = "#$num")
                        }
                    }
                }
            }, template = """
                <LazyRow height="150">
                  ${cellForTemplate(rowCount)}
                </LazyRow>
                """.templateToTest()
        )
    }

    @Test
    fun lazyRowReverseTest() {
        val rowCount = 30
        compareNativeComposableWithTemplate(
            nativeComposable = {
                LazyRow(Modifier.height(150.dp), reverseLayout = true) {
                    items((1..rowCount).toList()) { num ->
                        Column(Modifier.padding(8.dp)) {
                            Text(text = "Item $num", Modifier.weight(1f))
                            Text(text = "#$num")
                        }
                    }
                }
            }, template = """
                <LazyRow height="150" reverse-layout="true">
                  ${cellForTemplate(rowCount)}
                </LazyRow>
                """.templateToTest()
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
                    items((1..rowCount).toList()) { num ->
                        Column(Modifier.padding(8.dp)) {
                            Text(text = "Item $num", Modifier.weight(1f))
                            Text(text = "#$num")
                        }
                    }
                }
            }, template = """
                <LazyRow height="100" width="fill" horizontal-arrangement="spaceAround">
                  ${cellForTemplate(rowCount)}
                </LazyRow>
                """.templateToTest()
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
                    items((1..rowCount).toList()) { num ->
                        Column(Modifier.padding(8.dp)) {
                            Text(text = "Item $num")
                            Text(text = "#$num")
                        }
                    }
                }
            }, template = """
                <LazyRow width="fill" height="200" vertical-alignment="bottom">
                  ${cellForTemplate(rowCount, false)}
                </LazyRow>
                """.templateToTest()
        )
    }
}