package org.phoenixframework.liveview.test.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import org.junit.Test
import org.phoenixframework.liveview.data.constants.ComposableTypes.circularProgressIndicator
import org.phoenixframework.liveview.data.constants.ComposableTypes.column
import org.phoenixframework.liveview.data.constants.ComposableTypes.linearProgressIndicator
import org.phoenixframework.liveview.test.base.LiveViewComposableTest

class ProgressIndicatorShotTest : LiveViewComposableTest() {
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
                <$column>
                  <$circularProgressIndicator />
                  <$linearProgressIndicator />
                </$column>
                """,
        )
    }
}