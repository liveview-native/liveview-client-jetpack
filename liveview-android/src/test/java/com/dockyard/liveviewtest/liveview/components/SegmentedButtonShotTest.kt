package com.dockyard.liveviewtest.liveview.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.RectangleShape
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test

@OptIn(ExperimentalMaterial3Api::class)
class SegmentedButtonShotTest : LiveViewComposableTest() {
    @Test
    fun simpleSingleChoiceSegmentedButtonTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                SingleChoiceSegmentedButtonRow {
                    SegmentedButton(selected = false, onClick = {}, shape = RectangleShape) {
                        Text(text = "Option 1")
                    }
                    SegmentedButton(selected = true, onClick = {}, shape = RectangleShape) {
                        Text(text = "Option 2")
                    }
                    SegmentedButton(selected = false, onClick = {}, shape = RectangleShape) {
                        Text(text = "Option 3")
                    }
                }
            },
            template = """
                <SingleChoiceSegmentedButtonRow>
                  <SegmentedButton selected="false" phx-click="">
                    <Text template="label">Option 1</Text>
                  </SegmentedButton>
                  <SegmentedButton selected="true" phx-click="">
                    <Text template="label">Option 2</Text>
                  </SegmentedButton>
                  <SegmentedButton selected="false" phx-click="">
                    <Text template="label">Option 3</Text>
                  </SegmentedButton>
                </SingleChoiceSegmentedButtonRow>            
                """
        )
    }

    @Test
    fun simpleMultiChoiceSegmentedButtonTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                MultiChoiceSegmentedButtonRow {
                    SegmentedButton(checked = true, onCheckedChange = {}, shape = RectangleShape) {
                        Text(text = "Option 1")
                    }
                    SegmentedButton(checked = false, onCheckedChange = {}, shape = RectangleShape) {
                        Text(text = "Option 2")
                    }
                    SegmentedButton(checked = true, onCheckedChange = {}, shape = RectangleShape) {
                        Text(text = "Option 3")
                    }
                }
            },
            template = """
                <MultiChoiceSegmentedButtonRow>
                  <SegmentedButton checked="true" phx-change="">
                    <Text template="label">Option 1</Text>
                  </SegmentedButton>
                  <SegmentedButton checked="false" phx-change="">
                    <Text template="label">Option 2</Text>
                  </SegmentedButton>
                  <SegmentedButton checked="true" phx-change="">
                    <Text template="label">Option 3</Text>
                  </SegmentedButton>
                </MultiChoiceSegmentedButtonRow>            
                """
        )
    }
}