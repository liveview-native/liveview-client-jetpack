package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test
import org.phoenixframework.liveview.domain.base.ComposableTypes.circularProgressIndicator
import org.phoenixframework.liveview.domain.base.ComposableTypes.column
import org.phoenixframework.liveview.domain.base.ComposableTypes.linearProgressIndicator

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