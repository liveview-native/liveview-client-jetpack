package com.dockyard.liveviewtest.liveview.components

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
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test

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
                <Box size="200" background="system-green">
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
                <RichTooltip>
                    <Text template="text">Text</Text>
                </RichTooltip>
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
                <RichTooltip>
                    <Text template="title">Title</Text>
                    <Text template="text">Text</Text>
                </RichTooltip>
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
                <RichTooltip>
                    <Text template="title">Title</Text>
                    <Text template="text">Text</Text>
                    <Button template="action">
                        <Text>Action</Text>
                    </Button>
                </RichTooltip>
                """
        )
    }

    @Test
    fun richTooltipWithCustomColorAndShapeTest() {
        val colorsForTemplate = """
            {
            'containerColor': 'system-red',
            'contentColor': 'system-white',
            'titleContentColor': 'system-yellow',
            'actionContentColor': 'system-green'
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
                <RichTooltip colors="$colorsForTemplate" shape="4">
                  <Text template="title">Title</Text>
                  <Text template="text">Text</Text>
                  <Text template="action">Action</Text>
                </RichTooltip>
                """
        )
    }
}