package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.captionBarPadding
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.mandatorySystemGesturesPadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.safeGesturesPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.systemGesturesPadding
import androidx.compose.foundation.layout.waterfallPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test
import org.phoenixframework.liveview.data.constants.Attrs.attrBackground
import org.phoenixframework.liveview.data.constants.Attrs.attrClass
import org.phoenixframework.liveview.data.constants.Attrs.attrHeight
import org.phoenixframework.liveview.data.constants.Attrs.attrSize
import org.phoenixframework.liveview.data.constants.Attrs.attrStyle
import org.phoenixframework.liveview.data.constants.Attrs.attrText
import org.phoenixframework.liveview.data.constants.Attrs.attrWidth
import org.phoenixframework.liveview.data.constants.SizeValues.fill
import org.phoenixframework.liveview.data.constants.SystemColorValues.Black
import org.phoenixframework.liveview.data.constants.SystemColorValues.Blue
import org.phoenixframework.liveview.data.constants.SystemColorValues.Cyan
import org.phoenixframework.liveview.data.constants.SystemColorValues.DarkGray
import org.phoenixframework.liveview.data.constants.SystemColorValues.Gray
import org.phoenixframework.liveview.data.constants.SystemColorValues.Green
import org.phoenixframework.liveview.data.constants.SystemColorValues.LightGray
import org.phoenixframework.liveview.data.constants.SystemColorValues.Magenta
import org.phoenixframework.liveview.data.constants.SystemColorValues.Red
import org.phoenixframework.liveview.data.constants.SystemColorValues.Transparent
import org.phoenixframework.liveview.data.constants.SystemColorValues.White
import org.phoenixframework.liveview.data.constants.SystemColorValues.Yellow
import org.phoenixframework.liveview.domain.base.ComposableTypes.box
import org.phoenixframework.liveview.domain.base.ComposableTypes.column
import org.phoenixframework.liveview.domain.base.ComposableTypes.row
import org.phoenixframework.liveview.domain.base.ComposableTypes.text

class ComposableViewShotTest : LiveViewComposableTest() {
    @Test
    fun systemColorsShotTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                @Composable
                fun boxWithColor(color: Color) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(color)
                    )
                }
                Column {
                    boxWithColor(color = Color.Red)
                    boxWithColor(color = Color.Green)
                    boxWithColor(color = Color.Blue)
                    boxWithColor(color = Color.Black)
                    boxWithColor(color = Color.White)
                    boxWithColor(color = Color.Gray)
                    boxWithColor(color = Color.LightGray)
                    boxWithColor(color = Color.DarkGray)
                    boxWithColor(color = Color.Yellow)
                    boxWithColor(color = Color.Magenta)
                    boxWithColor(color = Color.Cyan)
                    boxWithColor(color = Color.Transparent)
                    boxWithColor(color = Color.Unspecified)
                }
            },
            template = """
                <$column>
                    <$box $attrSize="40" $attrBackground="$Red" />
                    <$box $attrSize="40" $attrBackground="$Green" />
                    <$box $attrSize="40" $attrBackground="$Blue" />
                    <$box $attrSize="40" $attrBackground="$Black" />
                    <$box $attrSize="40" $attrBackground="$White" />
                    <$box $attrSize="40" $attrBackground="$Gray" />
                    <$box $attrSize="40" $attrBackground="$LightGray" />
                    <$box $attrSize="40" $attrBackground="$DarkGray" />
                    <$box $attrSize="40" $attrBackground="$Yellow" />
                    <$box $attrSize="40" $attrBackground="$Magenta" />
                    <$box $attrSize="40" $attrBackground="$Cyan" />
                    <$box $attrSize="40" $attrBackground="$Transparent" />
                    <$box $attrSize="40" $attrBackground="invalid-color" />
                </$column>
                """
        )
    }

    @Test
    fun rrggbbColorsShotTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                @Composable
                fun boxWithColor(color: Color) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(color)
                    )
                }
                Column {
                    boxWithColor(color = Color.Red)
                    boxWithColor(color = Color.Green)
                    boxWithColor(color = Color.Blue)
                    boxWithColor(color = Color.Black)
                    boxWithColor(color = Color.White)
                    boxWithColor(color = Color.Gray)
                    boxWithColor(color = Color.LightGray)
                    boxWithColor(color = Color.DarkGray)
                    boxWithColor(color = Color.Yellow)
                    boxWithColor(color = Color.Magenta)
                    boxWithColor(color = Color.Cyan)
                    boxWithColor(color = Color.Unspecified)
                }
            },
            template = """
                <$column>
                    <$box $attrSize="40" $attrBackground="#FF0000" />
                    <$box $attrSize="40" $attrBackground="#00FF00" />
                    <$box $attrSize="40" $attrBackground="#0000FF" />
                    <$box $attrSize="40" $attrBackground="#000000" />
                    <$box $attrSize="40" $attrBackground="#FFFFFF" />
                    <$box $attrSize="40" $attrBackground="#888888" />
                    <$box $attrSize="40" $attrBackground="#CCCCCC" />
                    <$box $attrSize="40" $attrBackground="#444444" />
                    <$box $attrSize="40" $attrBackground="#FFFF00" />
                    <$box $attrSize="40" $attrBackground="#FF00FF" />
                    <$box $attrSize="40" $attrBackground="#00FFFF" />
                    <$box $attrSize="40" $attrBackground="invalid-color" />
                </$column>
                """
        )
    }

    @Test
    fun percentageHeightAndWidthTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Row(
                    Modifier
                        .fillMaxHeight(0.5f)
                        .fillMaxWidth()
                ) {
                    Box(
                        Modifier
                            .fillMaxWidth(0.1f)
                            .fillMaxHeight(0.7f)
                            .background(Color.Red)
                    )
                    Box(
                        Modifier
                            .fillMaxWidth(0.15f)
                            .fillMaxHeight(0.5f)
                            .background(Color.Green)
                    )
                    Box(
                        Modifier
                            .fillMaxWidth(0.25f)
                            .fillMaxHeight(0.4f)
                            .background(Color.Blue)
                    )
                    Box(
                        Modifier
                            .fillMaxWidth(0.35f)
                            .fillMaxHeight(0.2f)
                            .background(Color.Cyan)
                    )
                    Box(
                        Modifier
                            .fillMaxWidth(0.15f)
                            .fillMaxHeight(0.1f)
                            .background(Color.Yellow)
                    )
                }
            },
            template = """
                <$row $attrHeight="50%" $attrWidth="$fill">
                    <$box $attrWidth="10%" $attrHeight="70%" $attrBackground="$Red"/>
                    <$box $attrWidth="15%" $attrHeight="50%" $attrBackground="$Green"/>
                    <$box $attrWidth="25%" $attrHeight="40%" $attrBackground="$Blue"/>
                    <$box $attrWidth="35%" $attrHeight="20%" $attrBackground="$Cyan"/>
                    <$box $attrWidth="15%" $attrHeight="10%" $attrBackground="$Yellow"/>
                </$row>
                """
        )
    }

    //region Modifiers test
    // These tests were defined here because these modifiers always create new instances make them
    // not able to checking using equality, so we're testing relying on the final visual result.
    @Test
    fun captionBarPaddingTest() {
        val style = """
            %{'captionBarPaddingTest' => [
                {:captionBarPadding, [], []},
            ]}
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Box(modifier = Modifier.captionBarPadding()) {
                    Text(
                        text = "Caption Bar Padding Test"
                    )
                }
            },
            template = """
                <$box $attrStyle="$style">
                    <$text $attrText="Caption Bar Padding Test" />
                </$box>
                """
        )
    }

    @Test
    fun displayCutoutPaddingTest() {
        val style = """
            %{'displayCutoutPadding' => [
                {:displayCutoutPadding, [], []},
            ]}
            """
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Box(modifier = Modifier.displayCutoutPadding()) {
                    Text(
                        text = "Display Cutout Padding Test"
                    )
                }
            },
            template = """
                <$box $attrStyle="$style">
                    <$text $attrText="Display Cutout Padding Test" />
                </$box>
                """
        )
    }

    @Test
    fun safeDrawingPaddingTest() {
        val style = """
            %{'safeDrawingPaddingTest' => [
                {:safeDrawingPadding, [], []},
            ]}
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Box(modifier = Modifier.safeDrawingPadding()) {
                    Text(
                        text = "Safe Drawing Padding Test"
                    )
                }
            },
            template = """
                <$box $attrStyle="$style">
                    <$text $attrText="Safe Drawing Padding Test" />
                </$box>
                """
        )
    }

    @Test
    fun imePaddingTest() {
        val style = """
            %{'imePaddingTest' => [
                {:imePadding, [], []},
            ]}
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Box(modifier = Modifier.imePadding()) {
                    Text(
                        text = "IME Padding Test"
                    )
                }
            },
            template = """
                <$box $attrStyle="$style">
                    <$text $attrText="IME Padding Test" />
                </$box>
                """
        )
    }

    @Test
    fun mandatorySystemGesturesPaddingTest() {
        val style = """
            %{'mandatorySystemGesturesPaddingTest' => [
                {:mandatorySystemGesturesPadding, [], []},
            ]}
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Box(modifier = Modifier.mandatorySystemGesturesPadding()) {
                    Text(
                        text = "Mandatory System Gestures Padding Test"
                    )
                }
            },
            template = """
                <$box $attrStyle="$style">
                    <$text $attrText="Mandatory System Gestures Padding Test" />
                </$box>
                """
        )
    }

    @Test
    fun navigationBarsPaddingTest() {
        val style = """
            %{'navigationBarsPaddingTest' => [
                {:navigationBarsPadding, [], []},
            ]}
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Box(modifier = Modifier.navigationBarsPadding()) {
                    Text(
                        text = "Navigation Bars Padding Test"
                    )
                }
            },
            template = """
                <$box $attrStyle="$style">
                    <$text $attrText="Navigation Bars Padding Test" />
                </$box>
                """
        )
    }

    @Test
    fun safeContentPaddingTest() {
        val style = """
            %{'safeContentPaddingTest' => [
                {:safeContentPadding, [], []},
            ]}
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Box(modifier = Modifier.safeContentPadding()) {
                    Text(
                        text = "Safe Content Padding Test"
                    )
                }
            },
            template = """
                <$box $attrStyle="$style">
                    <$text $attrText="Safe Content Padding Test" />
                </$box>
                """
        )
    }

    @Test
    fun safeGesturesPaddingTest() {
        val style = """
            %{'safeGesturesPaddingTest' => [
                {:safeGesturesPadding, [], []},
            ]}
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Box(modifier = Modifier.safeGesturesPadding()) {
                    Text(
                        text = "Safe Gestures Padding Test"
                    )
                }
            },
            template = """
                <$box $attrStyle="$style">
                    <$text $attrText="Safe Gestures Padding Test" />
                </$box>
                """
        )
    }

    @Test
    fun statusBarsPaddingTest() {
        val style = """
            %{'statusBarsPaddingTest' => [
                {:statusBarsPadding, [], []},
            ]}
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Box(modifier = Modifier.statusBarsPadding()) {
                    Text(
                        text = "Status Bars Padding Test"
                    )
                }
            },
            template = """
                <$box $attrStyle="$style">
                    <$text $attrText="Status Bars Padding Test" />
                </$box>
                """
        )
    }

    @Test
    fun systemBarsPaddingTest() {
        val style = """
            %{'systemBarsPaddingTest' => [
                {:systemBarsPadding, [], []},
            ]}
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Box(modifier = Modifier.systemBarsPadding()) {
                    Text(
                        text = "System Bars Padding Test"
                    )
                }
            },
            template = """
                <$box $attrStyle="$style">
                    <$text $attrText="System Bars Padding Test" />
                </$box>
                """
        )
    }

    @Test
    fun systemGesturesPaddingTest() {
        val style = """
            %{'systemGesturesPaddingTest' => [
                {:systemGesturesPadding, [], []},
            ]}
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Box(modifier = Modifier.systemGesturesPadding()) {
                    Text(
                        text = "System Gestures Padding Test"
                    )
                }
            },
            template = """
                <$box $attrStyle="$style">
                    <$text $attrText="System Gestures Padding Test" />
                </$box>
                """
        )
    }

    @Test
    fun waterfallPaddingTest() {
        val style = """
            %{'waterfallPaddingTest' => [
                {:waterfallPadding, [], []},
            ]}
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Box(modifier = Modifier.waterfallPadding()) {
                    Text(
                        text = "Waterfall Padding Test"
                    )
                }
            },
            template = """
                <$box $attrStyle="$style">
                    <$text $attrText="Waterfall Padding Test" />
                </$box>
                """
        )
    }

    @Test
    fun windowInsetsPaddingTest() {
        val style = """
            %{'windowInsetsPadding' => [ 
                {:windowInsetsPadding, [], [{:WindowInsets, [], [10, 20, 30, 40]}]} 
            ]}
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Box(
                    modifier = Modifier.windowInsetsPadding(
                        WindowInsets(
                            10.dp,
                            20.dp,
                            30.dp,
                            40.dp
                        )
                    )
                ) {
                    Text(
                        text = "Window Insets Padding Test"
                    )
                }
            },
            template = """
                <$box $attrClass="$style">
                    <$text $attrText="Window Insets Padding Test" />
                </$box>                  
                """
        )
    }
    //endregion
}