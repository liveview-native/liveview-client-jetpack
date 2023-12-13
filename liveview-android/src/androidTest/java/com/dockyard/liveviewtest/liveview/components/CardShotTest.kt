package com.dockyard.liveviewtest.liveview.components

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
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test

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
                <Card>
                  <Row padding="32">
                    <Text>Card Content 1</Text>
                    <Text>Card Content 2</Text>
                  </Row>
                </Card>                
                """.templateToTest()
        )
    }

    @Test
    fun cardWithCustomParamsTest() {
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
                <Card shape="12" colors="{'containerColor': '#FFFF00FF', 'contentColor': 'FFFFFFFF'}">
                  <Row padding="32">
                    <Text>Card Content 1</Text>
                    <Text>Card Content 2</Text>
                  </Row>
                </Card>                
                """.templateToTest()
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
                <ElevatedCard>
                  <Text padding="48" font-size="24">Elevated Card</Text>
                </ElevatedCard>
                """.templateToTest()
        )
    }

    @Test
    fun simpleOutlinedCardTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                OutlinedCard {
                    Text(
                        text = "Oulined Card",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(32.dp)
                    )
                }
            },
            template = """
                <OutlinedCard>
                  <Text padding="32" font-size="16">Oulined Card</Text>
                </OutlinedCard>
                """.templateToTest()
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
                        text = "Oulined Card",
                        fontSize = 24.sp,
                        modifier = Modifier.padding(32.dp)
                    )
                }
            },
            template = """
                <OutlinedCard border-width="2" border-color="#FF0000FF">
                  <Text padding="32" font-size="24">Oulined Card</Text>
                </OutlinedCard>
                """.templateToTest()
        )
    }
}
