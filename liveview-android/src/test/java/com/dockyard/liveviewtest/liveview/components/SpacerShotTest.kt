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
                <Row vertical-alignment="center">
                    <Text>Left</Text>
                    <Column horizontal-alignment="center">
                        <Text>Top</Text>
                        <Spacer size="80" />
                        <Text>Bottom</Text>
                    </Column>
                    <Text>Right</Text>
                </Row>
                """
        )
    }
}
