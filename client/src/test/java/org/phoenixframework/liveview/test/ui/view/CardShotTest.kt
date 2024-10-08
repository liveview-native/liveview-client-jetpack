package org.phoenixframework.liveview.test.ui.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.junit.Test
import org.phoenixframework.liveview.constants.Attrs.attrBorder
import org.phoenixframework.liveview.constants.Attrs.attrColor
import org.phoenixframework.liveview.constants.Attrs.attrColors
import org.phoenixframework.liveview.constants.Attrs.attrFontSize
import org.phoenixframework.liveview.constants.Attrs.attrShape
import org.phoenixframework.liveview.constants.Attrs.attrStyle
import org.phoenixframework.liveview.constants.Attrs.attrWidth
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrContainerColor
import org.phoenixframework.liveview.constants.ColorAttrs.colorAttrContentColor
import org.phoenixframework.liveview.constants.ComposableTypes.card
import org.phoenixframework.liveview.constants.ComposableTypes.elevatedCard
import org.phoenixframework.liveview.constants.ComposableTypes.outlinedCard
import org.phoenixframework.liveview.constants.ComposableTypes.row
import org.phoenixframework.liveview.constants.ComposableTypes.text
import org.phoenixframework.liveview.constants.ModifierNames.modifierPadding
import org.phoenixframework.liveview.constants.ModifierTypes.typeDp
import org.phoenixframework.liveview.test.base.LiveViewComposableTest

class CardShotTest : LiveViewComposableTest() {

    @Test
    fun simpleCardTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Card {
                    Row(Modifier.padding(32.dp)) {
                        Text(text = "Card Content 1")
                        Text(text = "Card Content 2")
                    }
                }
            },
            template = """
                <$card>
                  <$row $attrStyle="$modifierPadding($typeDp(32))">
                    <$text>Card Content 1</$text>
                    <$text>Card Content 2</$text>
                  </$row>
                </$card>                
                """
        )
    }

    @Test
    fun cardWithCustomParamsTest() {
        val colorsForTemplate = """
            {
            '$colorAttrContainerColor': '#FFFF00FF', 
            '$colorAttrContentColor': 'FFFFFFFF'
            }
            """.toJsonForTemplate()
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Magenta,
                        contentColor = Color.White,
                    ),
                ) {
                    Row(Modifier.padding(32.dp)) {
                        Text(text = "Card Content 1")
                        Text(text = "Card Content 2")
                    }
                }
            },
            template = """
                <$card 
                  $attrShape="12" 
                  $attrColors="$colorsForTemplate">
                  <$row $attrStyle="$modifierPadding($typeDp(32))">
                    <$text>Card Content 1</$text>
                    <$text>Card Content 2</$text>
                  </$row>
                </$card>                
                """
        )
    }

    @Test
    fun simpleElevatedCardTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                ElevatedCard {
                    Text(
                        text = "Elevated Card",
                        fontSize = 24.sp,
                        modifier = Modifier.padding(48.dp)
                    )
                }
            },
            template = """
                <$elevatedCard>
                  <$text $attrStyle="$modifierPadding($typeDp(48))" $attrFontSize="24">Elevated Card</$text>
                </$elevatedCard>
                """
        )
    }

    @Test
    fun simpleOutlinedCardTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                OutlinedCard {
                    Text(
                        text = "Outlined Card",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(32.dp)
                    )
                }
            },
            template = """
                <$outlinedCard>
                  <$text $attrStyle="$modifierPadding($typeDp(32))" $attrFontSize="16">Outlined Card</$text>
                </$outlinedCard>
                """
        )
    }

    @Test
    fun outlinedCardWithBorderTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                OutlinedCard(
                    border = BorderStroke(2.dp, Color.Blue)
                ) {
                    Text(
                        text = "Outlined Card",
                        fontSize = 24.sp,
                        modifier = Modifier.padding(32.dp)
                    )
                }
            },
            template = """
                <$outlinedCard $attrBorder="{'$attrWidth': '2', '$attrColor': '#FF0000FF'}">
                  <$text $attrStyle="$modifierPadding($typeDp(32))" $attrFontSize="24">Outlined Card</$text>
                </$outlinedCard>
                """
        )
    }
}
