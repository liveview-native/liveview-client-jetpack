package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Ignore
import org.junit.Test

@OptIn(ExperimentalMaterial3Api::class)
class TooltipShotTest : LiveViewComposableTest() {
    @Ignore("See https://github.com/takahirom/roborazzi/issues/258")
    @Test
    fun simpleTooltipBoxTest() {
        val testTag = "tooltipSimple"
        compareNativeComposableWithTemplate(
            testTag = testTag,
            nativeComposable = {
                Box(
                    Modifier
                        .size(200.dp)
                        .background(Color.Green)
                        .testTag(testTag)
                ) {
                    TooltipBox(
                        positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
                        state = rememberTooltipState(initialIsVisible = true, isPersistent = true),
                        tooltip = {
                            PlainTooltip {
                                Text(text = "Tooltip")
                            }
                        },
                        content = {
                            Text(text = "Content")
                        }
                    )
                }
            },
            template = """
                <Box size="200" background="system-green" test-tag="$testTag">
                  <TooltipBox initial-is-visible="true" is-persistent="true">
                    <PlainTooltip template="tooltip">
                      <Text>Tooltip</Text>
                    </PlainTooltip>
                    <Text template="content">Content</Text>
                  </TooltipBox>
                </Box>
                """
        )
    }
}