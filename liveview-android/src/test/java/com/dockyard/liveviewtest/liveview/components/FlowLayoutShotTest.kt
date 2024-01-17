package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test

@OptIn(ExperimentalLayoutApi::class)
class FlowLayoutShotTest : LiveViewComposableTest() {
    @Test
    fun simpleFlowColumnTest() {
        val range = 0..30
        val childrenForTemplate = range.fold("") { s, i ->
            "$s<OutlinedButton><Text>Item $i</Text></OutlinedButton>"
        }
        compareNativeComposableWithTemplate(
            nativeComposable = {
                FlowColumn(Modifier.height(400.dp)) {
                    range.forEach {
                        OutlinedButton(onClick = {}) {
                            Text(text = "Item $it")
                        }
                    }
                }
            },
            template = """
                <FlowColumn height="400">
                    $childrenForTemplate
                </FlowColumn>
                """
        )
    }

    @Test
    fun simpleFlowRowTest() {
        val range = 0..30
        val childrenForTemplate = range.fold("") { s, i ->
            "$s<OutlinedButton><Text>Item $i</Text></OutlinedButton>"
        }
        compareNativeComposableWithTemplate(
            nativeComposable = {
                FlowRow {
                    range.forEach {
                        OutlinedButton(onClick = {}) {
                            Text(text = "Item $it")
                        }
                    }
                }
            },
            template = """
                <FlowRow>
                    $childrenForTemplate
                </FlowRow>
                """
        )
    }

    @Test
    fun flowColumnWithMaxItemsInEachColumnTest() {
        val range = 0..30
        val childrenForTemplate = range.fold("") { s, i ->
            "$s<OutlinedButton><Text>Item $i</Text></OutlinedButton>"
        }
        compareNativeComposableWithTemplate(
            nativeComposable = {
                FlowColumn(maxItemsInEachColumn = 5) {
                    range.forEach {
                        OutlinedButton(onClick = {}) {
                            Text(text = "Item $it")
                        }
                    }
                }
            },
            template = """
                <FlowColumn max-items-in-each-column="5">
                    $childrenForTemplate
                </FlowColumn>
                """
        )
    }

    @Test
    fun flowRowWithMaxItemsInEachRowTest() {
        val range = 0..30
        val childrenForTemplate = range.fold("") { s, i ->
            "$s<OutlinedButton><Text>Item $i</Text></OutlinedButton>"
        }
        compareNativeComposableWithTemplate(
            nativeComposable = {
                FlowRow(maxItemsInEachRow = 3) {
                    range.forEach {
                        OutlinedButton(onClick = {}) {
                            Text(text = "Item $it")
                        }
                    }
                }
            },
            template = """
                <FlowRow max-items-in-each-row="3">
                    $childrenForTemplate
                </FlowRow>
                """
        )
    }

    @Test
    fun flowColumnWithArrangementTest() {
        val range = 0..12
        val childrenForTemplate = range.fold("") { s, i ->
            "$s<OutlinedButton><Text>Item $i</Text></OutlinedButton>"
        }
        compareNativeComposableWithTemplate(
            nativeComposable = {
                FlowColumn(
                    modifier = Modifier.fillMaxSize(),
                    maxItemsInEachColumn = 5,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalArrangement = Arrangement.Center,
                ) {
                    range.forEach {
                        OutlinedButton(onClick = {}) {
                            Text(text = "Item $it")
                        }
                    }
                }
            },
            template = """
                <FlowColumn size="fill" max-items-in-each-column="5" 
                  horizontal-arrangement="spaceEvenly" vertical-arrangement="center">
                    $childrenForTemplate
                </FlowColumn>
                """
        )
    }

    @Test
    fun flowRowWithArrangementTest() {
        val range = 0..12
        val childrenForTemplate = range.fold("") { s, i ->
            "$s<OutlinedButton><Text>Item $i</Text></OutlinedButton>"
        }
        compareNativeComposableWithTemplate(
            nativeComposable = {
                FlowRow(
                    modifier = Modifier.fillMaxSize(),
                    maxItemsInEachRow = 3,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalArrangement = Arrangement.Bottom,
                ) {
                    range.forEach {
                        OutlinedButton(onClick = {}) {
                            Text(text = "Item $it")
                        }
                    }
                }
            },
            template = """
                <FlowRow size="fill" max-items-in-each-row="3" 
                  horizontal-arrangement="spaceBetween" vertical-arrangement="bottom">
                    $childrenForTemplate
                </FlowRow>
                """
        )
    }
}