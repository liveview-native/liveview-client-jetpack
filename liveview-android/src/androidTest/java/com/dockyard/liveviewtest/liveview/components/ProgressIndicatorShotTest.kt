package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Ignore
import org.junit.Test

class ProgressIndicatorShotTest: LiveViewComposableTest() {
    @Test
    fun simpleProgressIndicatorTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Column {
                    CircularProgressIndicator()
                    LinearProgressIndicator()
                }
            },
            template = """
                <Column>
                  <CircularProgressIndicator />
                  <LinearProgressIndicator />
                </Column>
                """,
        )
    }
}