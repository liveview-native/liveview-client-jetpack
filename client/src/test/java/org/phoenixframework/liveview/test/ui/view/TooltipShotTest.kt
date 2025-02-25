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
import org.phoenixframework.liveview.constants.Attrs.attrColors
import org.phoenixframework.liveview.constants.Attrs.attrInitialIsVisible
import org.phoenixframework.liveview.constants.Attrs.attrIsPersistent
import org.phoenixframework.liveview.constants.Attrs.attrShape
import org.phoenixframework.liveview.constants.Attrs.attrStyle
import org.phoenixframework.liveview.constants.Attrs.attrTemplate
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrActionContentColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrContainerColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrContentColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrTitleContentColor
import org.phoenixframework.liveview.constants.ComposableTypes.box
import org.phoenixframework.liveview.constants.ComposableTypes.button
import org.phoenixframework.liveview.constants.ComposableTypes.plainTooltip
import org.phoenixframework.liveview.constants.ComposableTypes.text
import org.phoenixframework.liveview.constants.ComposableTypes.tooltipBox
import org.phoenixframework.liveview.constants.ModifierNames.modifierBackground
import org.phoenixframework.liveview.constants.ModifierNames.modifierSize
import org.phoenixframework.liveview.constants.ModifierTypes.typeColor
import org.phoenixframework.liveview.constants.ModifierTypes.typeDp
import org.phoenixframework.liveview.constants.SystemColorValues.Green
import org.phoenixframework.liveview.constants.SystemColorValues.Red
import org.phoenixframework.liveview.constants.SystemColorValues.White
import org.phoenixframework.liveview.constants.SystemColorValues.Yellow
import org.phoenixframework.liveview.constants.Templates.templateAction
import org.phoenixframework.liveview.constants.Templates.templateContent
import org.phoenixframework.liveview.constants.Templates.templateText
import org.phoenixframework.liveview.constants.Templates.templateTitle
import org.phoenixframework.liveview.constants.Templates.templateTooltip
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


}