package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test

class LazyColumnShotTest : LiveViewComposableTest() {

    private fun rowsForTemplate(count: Int, fill: Boolean = true): String {
        return StringBuffer().apply {
            (1..count).forEach {
                append(
                    """
                    <Row padding="8">
                      <Text ${if (fill) "weight=\"1\"" else ""}>Item ${it}</Text>
                      <Text>#${it}</Text>
                    </Row>     
                    """
                )
            }
        }.toString()
    }

    @Test
    fun simpleLazyColumnTest() {
        val rowCount = 30
        compareNativeComposableWithTemplate(
            nativeComposable = {
                LazyColumn {
                    items((1..rowCount).toList()) { num ->
                        Row(Modifier.padding(8.dp)) {
                            Text(text = "Item $num", Modifier.weight(1f))
                            Text(text = "#$num")
                        }
                    }
                }
            }, template = """
                <LazyColumn>
                  ${rowsForTemplate(rowCount)}
                </LazyColumn>
                """.templateToTest()
        )
    }

    @Test
    fun lazyColumnReverseTest() {
        val rowCount = 30
        compareNativeComposableWithTemplate(
            nativeComposable = {
                LazyColumn(reverseLayout = true) {
                    items((1..rowCount).toList()) { num ->
                        Row(Modifier.padding(8.dp)) {
                            Text(text = "Item $num", Modifier.weight(1f))
                            Text(text = "#$num")
                        }
                    }
                }
            }, template = """
                <LazyColumn reverse-layout="true">
                  ${rowsForTemplate(rowCount)}
                </LazyColumn>
                """.templateToTest()
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
                    items((1..rowCount).toList()) { num ->
                        Row(Modifier.padding(8.dp)) {
                            Text(text = "Item $num", Modifier.weight(1f))
                            Text(text = "#$num")
                        }
                    }
                }
            }, template = """
                <LazyColumn size="fill" vertical-arrangement="spaceAround">
                  ${rowsForTemplate(rowCount)}
                </LazyColumn>
                """.templateToTest()
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
                    items((1..rowCount).toList()) { num ->
                        Row(Modifier.padding(8.dp)) {
                            Text(text = "Item $num")
                            Text(text = "#$num")
                        }
                    }
                }
            }, template = """
                <LazyColumn width="fill" horizontal-alignment="end">
                  ${rowsForTemplate(rowCount, false)}
                </LazyColumn>
                """.templateToTest()
        )
    }
}