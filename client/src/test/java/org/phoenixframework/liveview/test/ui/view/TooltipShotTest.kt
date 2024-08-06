package org.phoenixframework.liveview.test.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.RichTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.junit.Test
import org.phoenixframework.liveview.data.constants.Attrs.attrColors
import org.phoenixframework.liveview.data.constants.Attrs.attrInitialIsVisible
import org.phoenixframework.liveview.data.constants.Attrs.attrIsPersistent
import org.phoenixframework.liveview.data.constants.Attrs.attrShape
import org.phoenixframework.liveview.data.constants.Attrs.attrStyle
import org.phoenixframework.liveview.data.constants.Attrs.attrTemplate
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrActionContentColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrContainerColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrContentColor
import org.phoenixframework.liveview.data.constants.ColorAttrs.colorAttrTitleContentColor
import org.phoenixframework.liveview.data.constants.ComposableTypes.box
import org.phoenixframework.liveview.data.constants.ComposableTypes.button
import org.phoenixframework.liveview.data.constants.ComposableTypes.plainTooltip
import org.phoenixframework.liveview.data.constants.ComposableTypes.richTooltip
import org.phoenixframework.liveview.data.constants.ComposableTypes.text
import org.phoenixframework.liveview.data.constants.ComposableTypes.tooltipBox
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierBackground
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierSize
import org.phoenixframework.liveview.data.constants.ModifierTypes.typeColor
import org.phoenixframework.liveview.data.constants.ModifierTypes.typeDp
import org.phoenixframework.liveview.data.constants.SystemColorValues.Green
import org.phoenixframework.liveview.data.constants.SystemColorValues.Red
import org.phoenixframework.liveview.data.constants.SystemColorValues.White
import org.phoenixframework.liveview.data.constants.SystemColorValues.Yellow
import org.phoenixframework.liveview.data.constants.Templates.templateAction
import org.phoenixframework.liveview.data.constants.Templates.templateContent
import org.phoenixframework.liveview.data.constants.Templates.templateText
import org.phoenixframework.liveview.data.constants.Templates.templateTitle
import org.phoenixframework.liveview.data.constants.Templates.templateTooltip
import org.phoenixframework.liveview.test.base.LiveViewComposableTest

@OptIn(ExperimentalMaterial3Api::class)
class TooltipShotTest : LiveViewComposableTest() {
    @Test
    fun simpleTooltipBoxTest() {
        compareNativeComposableWithTemplate(
            captureScreenImage = true,
            nativeComposable = {
                Box(
                    Modifier
                        .size(200.dp)
                        .background(Color.Green),
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
                <$box $attrStyle="$modifierSize($typeDp(200));$modifierBackground($typeColor.$Green)">
                  <$tooltipBox $attrInitialIsVisible="true" $attrIsPersistent="true">
                    <$plainTooltip $attrTemplate="$templateTooltip">
                      <$text>Tooltip</$text>
                    </$plainTooltip>
                    <$text $attrTemplate="$templateContent">Content</$text>
                  </$tooltipBox>
                </$box>
                """
        )
    }

    @Test
    fun simpleRichTooltipTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                RichTooltip(
                    text = {
                        Text(text = "Text")
                    },
                )
            },
            template = """
                <$richTooltip>
                  <$text $attrTemplate="$templateText">Text</$text>
                </$richTooltip>
                """
        )
    }

    @Test
    fun richTooltipWithTitleTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                RichTooltip(
                    title = {
                        Text(text = "Title")
                    },
                    text = {
                        Text(text = "Text")
                    },
                )
            },
            template = """
                <$richTooltip>
                  <$text $attrTemplate="$templateTitle">Title</$text>
                  <$text $attrTemplate="$templateText">Text</$text>
                </$richTooltip>
                """
        )
    }

    @Test
    fun richTooltipWithTitleAndActionTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                RichTooltip(
                    title = {
                        Text(text = "Title")
                    },
                    text = {
                        Text(text = "Text")
                    },
                    action = {
                        Button(onClick = {}) {
                            Text(text = "Action")
                        }
                    }
                )
            },
            template = """
                <$richTooltip>
                  <$text $attrTemplate="$templateTitle">Title</$text>
                  <$text $attrTemplate="$templateText">Text</$text>
                  <$button $attrTemplate="$templateAction">
                    <$text>Action</$text>
                  </$button>
                </$richTooltip>
                """
        )
    }

    @Test
    fun richTooltipWithCustomColorAndShapeTest() {
        val colorsForTemplate = """
            {
            '$colorAttrContainerColor': '$Red',
            '$colorAttrContentColor': '$White',
            '$colorAttrTitleContentColor': '$Yellow',
            '$colorAttrActionContentColor': '$Green'
            }
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val colors = TooltipDefaults.richTooltipColors(
                    containerColor = Color.Red,
                    contentColor = Color.White,
                    titleContentColor = Color.Yellow,
                    actionContentColor = Color.Green,
                )
                RichTooltip(
                    shape = RoundedCornerShape(4.dp),
                    colors = colors,
                    title = {
                        Text(text = "Title")
                    },
                    text = {
                        Text(text = "Text")
                    },
                    action = {
                        Text(text = "Action")
                    }
                )
            },
            template = """
                <$richTooltip $attrColors="$colorsForTemplate" $attrShape="4">
                  <$text $attrTemplate="$templateTitle">Title</$text>
                  <$text $attrTemplate="$templateText">Text</$text>
                  <$text $attrTemplate="$templateAction">Action</$text>
                </$richTooltip>
                """
        )
    }
}