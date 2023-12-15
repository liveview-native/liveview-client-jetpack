package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test

class TextShotTest : LiveViewComposableTest() {
    @Test
    fun textTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Column {
                    Text(text = "Simple Text")
                    Text(text = "Text with color", color = Color.Red)
                    Text(text = "Text small", fontSize = 12.sp)
                    Text(text = "Text medium", fontSize = 16.sp)
                    Text(text = "Text large", fontSize = 24.sp)
                    Text(text = "Text min lines", minLines = 3)
                    Text(text = "Text Italic", fontStyle = FontStyle.Italic)
                    Text(text = "Text with letter spacing", letterSpacing = 12.sp)
                    Text(text = "Text Line Through", textDecoration = TextDecoration.LineThrough)
                    Text(text = "Text Underline", textDecoration = TextDecoration.Underline)
                    Text(
                        text = "All of above",
                        fontSize = 28.sp,
                        fontStyle = FontStyle.Italic,
                        letterSpacing = 4.sp,
                        minLines = 2,
                        textDecoration = TextDecoration.Underline
                    )
                }
            },
            template = """
                <Column>
                    <Text>Simple Text</Text>
                    <Text color="#FFFF0000">Text with color</Text>
                    <Text font-size="12">Text small</Text>
                    <Text font-size="16">Text medium</Text>
                    <Text font-size="24">Text large</Text>
                    <Text min-lines="3">Text min lines</Text>
                    <Text font-style="italic">Text Italic</Text>
                    <Text letter-spacing="12">Text with letter spacing</Text>
                    <Text text-decoration="lineThrough">Text Line Through</Text>
                    <Text text-decoration="underline">Text Underline</Text>
                    <Text 
                      font-size="28" font-style="italic" min-lines="2"
                      letter-spacing="4" text-decoration="underline">
                      All of above
                    </Text>  
                </Column>
                """.templateToTest()
        )
    }

    @Test
    fun textWeightTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Column {
                    Text(text = "Text Thin weight", fontWeight = FontWeight.Thin)
                    Text(text = "Text Extra Light weight", fontWeight = FontWeight.ExtraLight)
                    Text(text = "Text Light weight", fontWeight = FontWeight.Light)
                    Text(text = "Text Normal weight", fontWeight = FontWeight.Normal)
                    Text(text = "Text Medium weight", fontWeight = FontWeight.Medium)
                    Text(text = "Text Semi Bold weight", fontWeight = FontWeight.SemiBold)
                    Text(text = "Text Bold weight", fontWeight = FontWeight.Bold)
                    Text(text = "Text Extra Bold weight", fontWeight = FontWeight.ExtraBold)
                    Text(text = "Text Black weight", fontWeight = FontWeight.Black)
                }
            },
            template = """
                <Column>
                    <Text font-weight="thin">Text Thin weight</Text>
                    <Text font-weight="extraLight">Text Extra Light weight</Text>
                    <Text font-weight="light">Text Light weight</Text>
                    <Text font-weight="normal">Text Normal weight</Text>
                    <Text font-weight="medium">Text Medium weight</Text>
                    <Text font-weight="semiBold">Text Semi Bold weight</Text>
                    <Text font-weight="bold">Text Bold weight</Text>
                    <Text font-weight="extraBold">Text Extra Bold weight</Text>
                    <Text font-weight="black">Text Black weight</Text>
                </Column>
                """.templateToTest()
        )
    }

    @Test
    fun textWithAlignTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Column {
                    Text(
                        text = "Text Start",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start
                    )
                    Text(
                        text = "Text End",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End
                    )
                    Text(
                        text = "Text Center",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = lorenIpsum,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Justify
                    )
                }
            },
            template = """
                <Column>
                    <Text width="fill" text-align="start">Text Start</Text>
                    <Text width="fill" text-align="end">Text End</Text>
                    <Text width="fill" text-align="center">Text Center</Text>
                    <Text width="fill" text-align="justify">$lorenIpsum</Text>
                </Column>
                """.templateToTest()
        )
    }

    @Test
    fun textOverflowTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Column {
                    Text(text = lorenIpsum, maxLines = 2, overflow = TextOverflow.Clip)
                    Text(text = lorenIpsum, maxLines = 3, overflow = TextOverflow.Ellipsis)
                    Text(text = lorenIpsum, maxLines = 4, overflow = TextOverflow.Visible)
                }
            },
            template = """
                <Column>
                    <Text max-lines="2" overflow="clip">$lorenIpsum</Text>
                    <Text max-lines="3" overflow="ellipsis">$lorenIpsum</Text>
                    <Text max-lines="4" overflow="visible">$lorenIpsum</Text>
                </Column>
                """.templateToTest()
        )
    }

    @Test
    fun textLineHeightTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Column {
                    Text(text = lorenIpsum, maxLines = 3, lineHeight = 16.sp)
                    Text(text = lorenIpsum, maxLines = 3, lineHeight = 20.sp)
                }
            },
            template = """
                <Column>
                    <Text max-lines="3" line-height="16">$lorenIpsum</Text>
                    <Text max-lines="3" line-height="20">$lorenIpsum</Text>
                </Column>
                """.templateToTest()
        )
    }

    private val lorenIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do " +
            "eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim " +
            "veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo " +
            "consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse " +
            "cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non " +
            "proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
}