package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Ignore
import org.junit.Test

// FIXME Seems like there are issues with BottomSheet
//  https://github.com/pedrovgs/Shot/issues?q=is%3Aissue+BottomSheet
class ModalBottomSheetShotTest : LiveViewComposableTest() {
    @OptIn(ExperimentalMaterial3Api::class)
    @Ignore("Seems like there are issues with BottomSheet")
    @Test
    fun simpleModalBottomSheetTest() {
        val testTag = "sheet"
        compareNativeComposableWithTemplate(
            testTag = testTag, nativeComposable = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag(testTag)
                ) {
                    ModalBottomSheet(onDismissRequest = {}) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                        ) {
                            Text(text = "BottomSheet Content")
                        }
                    }
                }
            }, template = """
                <Box size="fill"  test-tag="$testTag">
                  <ModalBottomSheet on-changed="" sheet-value="">
                    <Box content-alignment="center" width="fill" height="200">
                      <Text>BottomSheet Content</Text>
                    </Box>
                  </ModalBottomSheet>                
                </Box>
                """
        )
    }
}