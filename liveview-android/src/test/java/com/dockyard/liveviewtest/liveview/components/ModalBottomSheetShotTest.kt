package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test
import org.phoenixframework.liveview.data.constants.AlignmentValues
import org.phoenixframework.liveview.data.constants.Attrs.attrContentAlignment
import org.phoenixframework.liveview.data.constants.Attrs.attrHeight
import org.phoenixframework.liveview.data.constants.Attrs.attrSheetValue
import org.phoenixframework.liveview.data.constants.Attrs.attrSize
import org.phoenixframework.liveview.data.constants.Attrs.attrTestTag
import org.phoenixframework.liveview.data.constants.Attrs.attrWidth
import org.phoenixframework.liveview.data.constants.SheetValues.expanded
import org.phoenixframework.liveview.data.constants.SizeValues.fill
import org.phoenixframework.liveview.domain.base.ComposableTypes.box
import org.phoenixframework.liveview.domain.base.ComposableTypes.modalBottomSheet
import org.phoenixframework.liveview.domain.base.ComposableTypes.text

@OptIn(ExperimentalMaterial3Api::class)
class ModalBottomSheetShotTest : LiveViewComposableTest() {
    @Test
    fun simpleModalBottomSheetTest() {
        val testTag = "sheet"
        compareNativeComposableWithTemplate(
            testTag = testTag,
            delayBeforeScreenshot = 1000,
            captureScreenImage = true,
            nativeComposable = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag(testTag)
                ) {
                    val sheetState = rememberModalBottomSheetState()
                    ModalBottomSheet(onDismissRequest = {}, sheetState = sheetState) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                        ) {
                            Text(text = "BottomSheet Content")
                        }
                    }
                    LaunchedEffect(Unit) {
                        sheetState.expand()
                    }
                }
            },
            template = """
                <$box $attrSize="$fill" $attrTestTag="$testTag">
                  <$modalBottomSheet $attrSheetValue="$expanded">
                    <$box 
                      $attrContentAlignment="${AlignmentValues.center}" 
                      $attrWidth="$fill" $attrHeight="200">
                      <$text>BottomSheet Content</$text>
                    </$box>
                  </$modalBottomSheet>                
                </$box>
                """
        )
    }
}