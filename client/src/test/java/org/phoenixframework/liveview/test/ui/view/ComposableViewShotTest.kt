package org.phoenixframework.liveview.test.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.phoenixframework.liveview.test.base.LiveViewComposableTest
import org.junit.Test
import org.phoenixframework.liveview.data.constants.Attrs.attrStyle
import org.phoenixframework.liveview.data.constants.ComposableTypes.box
import org.phoenixframework.liveview.data.constants.ComposableTypes.column
import org.phoenixframework.liveview.data.constants.ComposableTypes.flowRow
import org.phoenixframework.liveview.data.constants.ComposableTypes.row
import org.phoenixframework.liveview.data.constants.ModifierArgs.argBottom
import org.phoenixframework.liveview.data.constants.ModifierArgs.argEnd
import org.phoenixframework.liveview.data.constants.ModifierArgs.argHorizontal
import org.phoenixframework.liveview.data.constants.ModifierArgs.argStart
import org.phoenixframework.liveview.data.constants.ModifierArgs.argTop
import org.phoenixframework.liveview.data.constants.ModifierArgs.argVertical
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierAspectRatio
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierBackground
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierFillMaxHeight
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierFillMaxWidth
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierHeight
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierPadding
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierSize
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierWidth
import org.phoenixframework.liveview.data.constants.ModifierTypes.typeColor
import org.phoenixframework.liveview.data.constants.ModifierTypes.typeDp
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
import org.phoenixframework.liveview.data.constants.ThemeColorsValues

class ComposableViewShotTest : LiveViewComposableTest() {

    @Test
    fun aspectRatioTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Column {
                    Box(
                        modifier = Modifier
                            .width(200.dp)
                            .aspectRatio(1.33f)
                            .background(Color.Red)
                    )
                    Box(
                        modifier = Modifier
                            .height(200.dp)
                            .aspectRatio(1.77f)
                            .background(Color.Blue)
                    )
                }
            },
            template = """
                <$column>
                    <$box $attrStyle="$modifierWidth($typeDp(200));$modifierAspectRatio(1.33);$modifierBackground($typeColor.$Red)" />
                    <$box $attrStyle="$modifierHeight($typeDp(200));$modifierAspectRatio(1.77);$modifierBackground($typeColor.$Blue)" />
                </$column>
                """
        )
    }

    @Test
    fun paddingTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Column {
                    Row(Modifier.padding(bottom = 24.dp)) {
                        Box(
                            Modifier
                                .padding(vertical = 16.dp)
                                .size(50.dp)
                                .background(Color.Cyan)
                        )
                        Box(
                            Modifier
                                .padding(start = 16.dp, end = 16.dp)
                                .size(50.dp)
                                .background(Color.Yellow)
                        )
                        Box(
                            Modifier
                                .padding(horizontal = 24.dp)
                                .size(50.dp)
                                .background(Color.Gray)
                        )
                        Box(
                            Modifier
                                .padding(top = 8.dp)
                                .size(50.dp)
                                .background(Color.Magenta)
                        )
                    }
                    Box(
                        Modifier
                            .size(50.dp)
                            .background(Color.DarkGray)
                    )
                }
            },
            template = """
                <$column>
                    <$row $attrStyle="$modifierPadding($argBottom = $typeDp(24))">
                        <$box $attrStyle="$modifierPadding($argVertical = $typeDp(16));$modifierSize($typeDp(50));$modifierBackground($typeColor.$Cyan)" />
                        <$box $attrStyle="$modifierPadding($argStart = $typeDp(16), $argEnd = $typeDp(16));$modifierSize($typeDp(50));$modifierBackground($typeColor.$Yellow)" />
                        <$box $attrStyle="$modifierPadding($argHorizontal = $typeDp(24));$modifierSize($typeDp(50));$modifierBackground($typeColor.$Gray)" />
                        <$box $attrStyle="$modifierPadding($argTop = $typeDp(8));$modifierSize($typeDp(50));$modifierBackground($typeColor.$Magenta)" />
                    </$row>
                    <$box $attrStyle="$modifierSize($typeDp(50));$modifierBackground($typeColor.$DarkGray)" />
                </$column>
                """
        )
    }

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
                    <$box $attrStyle="$modifierSize($typeDp(40));$modifierBackground($typeColor.$Red)" />
                    <$box $attrStyle="$modifierSize($typeDp(40));$modifierBackground($typeColor.$Green)" />
                    <$box $attrStyle="$modifierSize($typeDp(40));$modifierBackground($typeColor.$Blue)" />
                    <$box $attrStyle="$modifierSize($typeDp(40));$modifierBackground($typeColor.$Black)" />
                    <$box $attrStyle="$modifierSize($typeDp(40));$modifierBackground($typeColor.$White)" />
                    <$box $attrStyle="$modifierSize($typeDp(40));$modifierBackground($typeColor.$Gray)" />
                    <$box $attrStyle="$modifierSize($typeDp(40));$modifierBackground($typeColor.$LightGray)" />
                    <$box $attrStyle="$modifierSize($typeDp(40));$modifierBackground($typeColor.$DarkGray)" />
                    <$box $attrStyle="$modifierSize($typeDp(40));$modifierBackground($typeColor.$Yellow)" />
                    <$box $attrStyle="$modifierSize($typeDp(40));$modifierBackground($typeColor.$Magenta)" />
                    <$box $attrStyle="$modifierSize($typeDp(40));$modifierBackground($typeColor.$Cyan)" />
                    <$box $attrStyle="$modifierSize($typeDp(40));$modifierBackground($typeColor.$Transparent)" />
                    <$box $attrStyle="$modifierSize($typeDp(40));$modifierBackground($typeColor.Unspecified" />
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
                    <$box $attrStyle="$modifierSize($typeDp(40));$modifierBackground($typeColor.$Red)"/>
                    <$box $attrStyle="$modifierSize($typeDp(40));$modifierBackground($typeColor.$Green)"/>
                    <$box $attrStyle="$modifierSize($typeDp(40));$modifierBackground($typeColor.$Blue)"/>
                    <$box $attrStyle="$modifierSize($typeDp(40));$modifierBackground($typeColor.$Black)"/>
                    <$box $attrStyle="$modifierSize($typeDp(40));$modifierBackground($typeColor.$White)"/>
                    <$box $attrStyle="$modifierSize($typeDp(40));$modifierBackground($typeColor.$Gray)"/>
                    <$box $attrStyle="$modifierSize($typeDp(40));$modifierBackground($typeColor.$LightGray)"/>
                    <$box $attrStyle="$modifierSize($typeDp(40));$modifierBackground($typeColor.$DarkGray)"/>
                    <$box $attrStyle="$modifierSize($typeDp(40));$modifierBackground($typeColor.$Yellow)"/>
                    <$box $attrStyle="$modifierSize($typeDp(40));$modifierBackground($typeColor.$Magenta)"/>
                    <$box $attrStyle="$modifierSize($typeDp(40));$modifierBackground($typeColor.$Cyan)"/>
                    <$box $attrStyle="$modifierSize($typeDp(40));$modifierBackground($typeColor.Unspecified)"/>
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
                <$row $attrStyle="$modifierFillMaxHeight(0.50);$modifierFillMaxWidth()">
                    <$box $attrStyle="$modifierFillMaxWidth(0.10);$modifierFillMaxHeight(0.70);$modifierBackground($typeColor.$Red)"/>
                    <$box $attrStyle="$modifierFillMaxWidth(0.15);$modifierFillMaxHeight(0.50);$modifierBackground($typeColor.$Green)"/>
                    <$box $attrStyle="$modifierFillMaxWidth(0.25);$modifierFillMaxHeight(0.40);$modifierBackground($typeColor.$Blue)"/>
                    <$box $attrStyle="$modifierFillMaxWidth(0.35);$modifierFillMaxHeight(0.20);$modifierBackground($typeColor.$Cyan)"/>
                    <$box $attrStyle="$modifierFillMaxWidth(0.15);$modifierFillMaxHeight(0.10);$modifierBackground($typeColor.$Yellow)"/>
                </$row>
                """
        )
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Test
    fun themeColorTests() {
        val stringColors = listOf(
            ThemeColorsValues.primary,
            ThemeColorsValues.onPrimary,
            ThemeColorsValues.primaryContainer,
            ThemeColorsValues.onPrimaryContainer,
            ThemeColorsValues.inversePrimary,
            ThemeColorsValues.secondary,
            ThemeColorsValues.onSecondary,
            ThemeColorsValues.secondaryContainer,
            ThemeColorsValues.onSecondaryContainer,
            ThemeColorsValues.tertiary,
            ThemeColorsValues.onTertiary,
            ThemeColorsValues.tertiaryContainer,
            ThemeColorsValues.onTertiaryContainer,
            ThemeColorsValues.background,
            ThemeColorsValues.onBackground,
            ThemeColorsValues.surface,
            ThemeColorsValues.onSurface,
            ThemeColorsValues.surfaceVariant,
            ThemeColorsValues.onSurfaceVariant,
            ThemeColorsValues.surfaceTint,
            ThemeColorsValues.inverseSurface,
            ThemeColorsValues.inverseOnSurface,
            ThemeColorsValues.error,
            ThemeColorsValues.onError,
            ThemeColorsValues.errorContainer,
            ThemeColorsValues.onErrorContainer,
            ThemeColorsValues.outline,
            ThemeColorsValues.outlineVariant,
            ThemeColorsValues.scrim,
            ThemeColorsValues.surfaceBright,
            ThemeColorsValues.surfaceContainer,
            ThemeColorsValues.surfaceContainerHigh,
            ThemeColorsValues.surfaceContainerHighest,
            ThemeColorsValues.surfaceContainerLow,
            ThemeColorsValues.surfaceContainerLowest,
            ThemeColorsValues.surfaceDim,
        )
        val boxes =
            stringColors.joinToString("") {
                "<$box $attrStyle=\"$modifierSize($typeDp(50));$modifierBackground($typeColor.$it)\"/>"
            }
        compareNativeComposableWithTemplate(
            nativeComposable = {
                val colors = listOf(
                    MaterialTheme.colorScheme.primary,
                    MaterialTheme.colorScheme.onPrimary,
                    MaterialTheme.colorScheme.primaryContainer,
                    MaterialTheme.colorScheme.onPrimaryContainer,
                    MaterialTheme.colorScheme.inversePrimary,
                    MaterialTheme.colorScheme.secondary,
                    MaterialTheme.colorScheme.onSecondary,
                    MaterialTheme.colorScheme.secondaryContainer,
                    MaterialTheme.colorScheme.onSecondaryContainer,
                    MaterialTheme.colorScheme.tertiary,
                    MaterialTheme.colorScheme.onTertiary,
                    MaterialTheme.colorScheme.tertiaryContainer,
                    MaterialTheme.colorScheme.onTertiaryContainer,
                    MaterialTheme.colorScheme.background,
                    MaterialTheme.colorScheme.onBackground,
                    MaterialTheme.colorScheme.surface,
                    MaterialTheme.colorScheme.onSurface,
                    MaterialTheme.colorScheme.surfaceVariant,
                    MaterialTheme.colorScheme.onSurfaceVariant,
                    MaterialTheme.colorScheme.surfaceTint,
                    MaterialTheme.colorScheme.inverseSurface,
                    MaterialTheme.colorScheme.inverseOnSurface,
                    MaterialTheme.colorScheme.error,
                    MaterialTheme.colorScheme.onError,
                    MaterialTheme.colorScheme.errorContainer,
                    MaterialTheme.colorScheme.onErrorContainer,
                    MaterialTheme.colorScheme.outline,
                    MaterialTheme.colorScheme.outlineVariant,
                    MaterialTheme.colorScheme.scrim,
                    MaterialTheme.colorScheme.surfaceBright,
                    MaterialTheme.colorScheme.surfaceContainer,
                    MaterialTheme.colorScheme.surfaceContainerHigh,
                    MaterialTheme.colorScheme.surfaceContainerHighest,
                    MaterialTheme.colorScheme.surfaceContainerLow,
                    MaterialTheme.colorScheme.surfaceContainerLowest,
                    MaterialTheme.colorScheme.surfaceDim,

                    )
                FlowRow {
                    colors.forEach {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .background(it)
                        )
                    }
                }
            }, template = """
                <$flowRow>$boxes</$flowRow>
                """
        )
    }
}