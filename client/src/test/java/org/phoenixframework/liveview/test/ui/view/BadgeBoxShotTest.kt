package org.phoenixframework.liveview.test.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.junit.Test
import org.phoenixframework.liveview.constants.AlignmentValues.center
import org.phoenixframework.liveview.constants.Attrs.attrContainerColor
import org.phoenixframework.liveview.constants.Attrs.attrContentAlignment
import org.phoenixframework.liveview.constants.Attrs.attrContentColor
import org.phoenixframework.liveview.constants.Attrs.attrImageVector
import org.phoenixframework.liveview.constants.Attrs.attrStyle
import org.phoenixframework.liveview.constants.Attrs.attrTemplate
import org.phoenixframework.liveview.constants.ComposableTypes.badgedBox
import org.phoenixframework.liveview.constants.ComposableTypes.box
import org.phoenixframework.liveview.constants.ComposableTypes.icon
import org.phoenixframework.liveview.constants.ComposableTypes.text
import org.phoenixframework.liveview.constants.IconPrefixValues.filled
import org.phoenixframework.liveview.constants.ModifierNames.modifierSize
import org.phoenixframework.liveview.constants.ModifierTypes.typeDp
import org.phoenixframework.liveview.constants.Templates.templateBadge
import org.phoenixframework.liveview.test.base.LiveViewComposableTest

class BadgeBoxShotTest : LiveViewComposableTest() {

    @Test
    fun simpleBadgeBoxTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.size(100.dp)) {
                    BadgedBox(
                        badge = {
                            Badge(
                                containerColor = Color.Blue,
                                contentColor = Color.Red,
                            ) {
                                Text(text = "+99")
                            }
                        },
                    ) {
                        Icon(imageVector = Icons.Filled.Add, contentDescription = "")
                    }
                }
            },
            template = """
                <$box $attrStyle="$modifierSize($typeDp(100))" $attrContentAlignment="$center">
                  <$badgedBox $attrContainerColor="#FF0000FF" $attrContentColor="#FFFF0000">
                    <$text $attrTemplate="$templateBadge">+99</$text>
                    <$icon $attrImageVector="$filled:Add" />
                  </$badgedBox>                
                </$box>  
                """
        )
    }
}