package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test
import org.phoenixframework.liveview.data.constants.Attrs.attrBorder
import org.phoenixframework.liveview.data.constants.Attrs.attrColor
import org.phoenixframework.liveview.data.constants.Attrs.attrContentColor
import org.phoenixframework.liveview.data.constants.Attrs.attrShadowElevation
import org.phoenixframework.liveview.data.constants.Attrs.attrShape
import org.phoenixframework.liveview.data.constants.Attrs.attrStyle
import org.phoenixframework.liveview.data.constants.Attrs.attrTonalElevation
import org.phoenixframework.liveview.data.constants.Attrs.attrWidth
import org.phoenixframework.liveview.data.constants.ModifierNames.modifierPadding
import org.phoenixframework.liveview.data.constants.ModifierTypes.typeDp
import org.phoenixframework.liveview.data.constants.SystemColorValues.Blue
import org.phoenixframework.liveview.data.constants.SystemColorValues.Green
import org.phoenixframework.liveview.data.constants.SystemColorValues.Yellow
import org.phoenixframework.liveview.domain.base.ComposableTypes.column
import org.phoenixframework.liveview.domain.base.ComposableTypes.surface
import org.phoenixframework.liveview.domain.base.ComposableTypes.text

class SurfaceShotTest : LiveViewComposableTest() {
    @Test
    fun simplesSurfaceTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Column {
                    Surface {
                        Text(text = "Surface 1", modifier = Modifier.padding(16.dp))
                    }
                    Surface(color = Color.Blue) {
                        Text(text = "Surface 2", modifier = Modifier.padding(16.dp))
                    }
                    Surface(color = Color.Blue, contentColor = Color.Yellow) {
                        Text(text = "Surface 3", modifier = Modifier.padding(16.dp))
                    }
                    Surface(
                        color = Color.Blue,
                        contentColor = Color.Yellow,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(text = "Surface 4", modifier = Modifier.padding(16.dp))
                    }
                    Surface(
                        color = Color.Blue,
                        contentColor = Color.Yellow,
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(2.dp, Color.Green)
                    ) {
                        Text(text = "Surface 5", modifier = Modifier.padding(16.dp))
                    }
                    Surface(
                        color = Color.Blue,
                        contentColor = Color.Yellow,
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(2.dp, Color.Green),
                        tonalElevation = 4.dp
                    ) {
                        Text(text = "Surface 6", modifier = Modifier.padding(16.dp))
                    }
                    Surface(
                        color = Color.Blue,
                        contentColor = Color.Yellow,
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(2.dp, Color.Green),
                        tonalElevation = 4.dp,
                        shadowElevation = 8.dp,
                    ) {
                        Text(text = "Surface 7", modifier = Modifier.padding(16.dp))
                    }
                }
            },
            template = """
                <$column>
                  <$surface>
                    <$text $attrStyle="$modifierPadding($typeDp(16))">Surface 1</$text>
                  </$surface>
                  <$surface $attrColor="$Blue">
                    <$text $attrStyle="$modifierPadding($typeDp(16))">Surface 2</$text>
                  </$surface>
                  <$surface $attrColor="$Blue" $attrContentColor="$Yellow">
                    <$text $attrStyle="$modifierPadding($typeDp(16))">Surface 3</$text>
                  </$surface>
                  <$surface $attrColor="$Blue" $attrContentColor="$Yellow" $attrShape="12">
                    <$text $attrStyle="$modifierPadding($typeDp(16))">Surface 4</$text>
                  </$surface>    
                  <$surface $attrColor="$Blue" $attrContentColor="$Yellow" $attrShape="12" 
                    $attrBorder="{'$attrWidth': '2', '$attrColor': '$Green'}">
                    <$text $attrStyle="$modifierPadding($typeDp(16))">Surface 5</$text>
                  </$surface>     
                  <$surface $attrColor="$Blue" $attrContentColor="$Yellow" $attrShape="12" 
                    $attrBorder="{'$attrWidth': '2', '$attrColor': '$Green'}" $attrTonalElevation="4">
                    <$text $attrStyle="$modifierPadding($typeDp(16))">Surface 6</$text>
                  </$surface> 
                  <$surface $attrColor="$Blue" $attrContentColor="$Yellow" $attrShape="12" 
                    $attrBorder="{'$attrWidth': '2', '$attrColor': '$Green'}" $attrTonalElevation="4"
                    $attrShadowElevation="8">
                    <$text $attrStyle="$modifierPadding($typeDp(16))">Surface 7</$text>
                  </$surface>                                                                                                
                </$column>
                """
        )
    }
}