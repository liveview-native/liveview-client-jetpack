package com.dockyard.liveviewtest.liveview.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.AddToHomeScreen
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.filled.FactCheck
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.automirrored.outlined.AddToHomeScreen
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.Chat
import androidx.compose.material.icons.automirrored.outlined.FactCheck
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.automirrored.rounded.AddToHomeScreen
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.Chat
import androidx.compose.material.icons.automirrored.rounded.FactCheck
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material.icons.automirrored.sharp.AddToHomeScreen
import androidx.compose.material.icons.automirrored.sharp.ArrowBack
import androidx.compose.material.icons.automirrored.sharp.Chat
import androidx.compose.material.icons.automirrored.sharp.FactCheck
import androidx.compose.material.icons.automirrored.sharp.Send
import androidx.compose.material.icons.automirrored.twotone.AddToHomeScreen
import androidx.compose.material.icons.automirrored.twotone.ArrowBack
import androidx.compose.material.icons.automirrored.twotone.Chat
import androidx.compose.material.icons.automirrored.twotone.FactCheck
import androidx.compose.material.icons.automirrored.twotone.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Image
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material.icons.sharp.Check
import androidx.compose.material.icons.sharp.Home
import androidx.compose.material.icons.sharp.Image
import androidx.compose.material.icons.sharp.Share
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material.icons.twotone.Check
import androidx.compose.material.icons.twotone.Home
import androidx.compose.material.icons.twotone.Image
import androidx.compose.material.icons.twotone.Share
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.Color
import com.dockyard.liveviewtest.liveview.test.util.LiveViewComposableTest
import org.junit.Test
import org.phoenixframework.liveview.data.constants.Attrs.attrImageVector
import org.phoenixframework.liveview.data.constants.Attrs.attrTint
import org.phoenixframework.liveview.data.constants.IconPrefixValues.autoMirroredFilled
import org.phoenixframework.liveview.data.constants.IconPrefixValues.autoMirroredOutlined
import org.phoenixframework.liveview.data.constants.IconPrefixValues.autoMirroredRounded
import org.phoenixframework.liveview.data.constants.IconPrefixValues.autoMirroredSharp
import org.phoenixframework.liveview.data.constants.IconPrefixValues.autoMirroredTwoTone
import org.phoenixframework.liveview.data.constants.IconPrefixValues.filled
import org.phoenixframework.liveview.data.constants.IconPrefixValues.outlined
import org.phoenixframework.liveview.data.constants.IconPrefixValues.rounded
import org.phoenixframework.liveview.data.constants.IconPrefixValues.sharp
import org.phoenixframework.liveview.data.constants.IconPrefixValues.twoTone
import org.phoenixframework.liveview.data.constants.ComposableTypes.column
import org.phoenixframework.liveview.data.constants.ComposableTypes.icon
import org.phoenixframework.liveview.data.constants.ComposableTypes.row

class IconShotTest : LiveViewComposableTest() {

    @Test
    fun simpleIconTest() {
        compareNativeComposableWithTemplate(
            nativeComposable = {
                Row {
                    Icon(imageVector = Icons.Filled.Image, contentDescription = "")
                    Icon(
                        imageVector = Icons.Filled.Image,
                        tint = Color.Red,
                        contentDescription = ""
                    )
                }
            },
            template = """
                <$row>
                  <$icon $attrImageVector="$filled:Image" />
                  <$icon $attrImageVector="$filled:Image" $attrTint="#FFFF0000" />
                </$row>
                """
        )
    }

    @Test
    fun iconWithDifferentStylesTest() {
        compareNativeComposableWithTemplate(
            delayBeforeScreenshot = 1000,
            nativeComposable = {
                Column {
                    Row {
                        Icon(imageVector = Icons.Filled.Add, contentDescription = "")
                        Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        Icon(imageVector = Icons.Filled.Home, contentDescription = "")
                        Icon(imageVector = Icons.Filled.Image, contentDescription = "")
                        Icon(imageVector = Icons.Filled.Share, contentDescription = "")
                    }
                    Row {
                        Icon(imageVector = Icons.Outlined.Add, contentDescription = "")
                        Icon(imageVector = Icons.Outlined.Check, contentDescription = "")
                        Icon(imageVector = Icons.Outlined.Home, contentDescription = "")
                        Icon(imageVector = Icons.Outlined.Image, contentDescription = "")
                        Icon(imageVector = Icons.Outlined.Share, contentDescription = "")
                    }
                    Row {
                        Icon(imageVector = Icons.Rounded.Add, contentDescription = "")
                        Icon(imageVector = Icons.Rounded.Check, contentDescription = "")
                        Icon(imageVector = Icons.Rounded.Home, contentDescription = "")
                        Icon(imageVector = Icons.Rounded.Image, contentDescription = "")
                        Icon(imageVector = Icons.Rounded.Share, contentDescription = "")
                    }
                    Row {
                        Icon(imageVector = Icons.TwoTone.Add, contentDescription = "")
                        Icon(imageVector = Icons.TwoTone.Check, contentDescription = "")
                        Icon(imageVector = Icons.TwoTone.Home, contentDescription = "")
                        Icon(imageVector = Icons.TwoTone.Image, contentDescription = "")
                        Icon(imageVector = Icons.TwoTone.Share, contentDescription = "")
                    }
                    Row {
                        Icon(imageVector = Icons.Sharp.Add, contentDescription = "")
                        Icon(imageVector = Icons.Sharp.Check, contentDescription = "")
                        Icon(imageVector = Icons.Sharp.Home, contentDescription = "")
                        Icon(imageVector = Icons.Sharp.Image, contentDescription = "")
                        Icon(imageVector = Icons.Sharp.Share, contentDescription = "")
                    }
                }

            },
            template = """
                <$column>             
                  <$row>
                    <$icon $attrImageVector="$filled:Add" />
                    <$icon $attrImageVector="$filled:Check" />
                    <$icon $attrImageVector="$filled:Home" />
                    <$icon $attrImageVector="$filled:Image" />
                    <$icon $attrImageVector="$filled:Share" />
                  </$row>
                  <$row>
                    <$icon $attrImageVector="$outlined:Add" />
                    <$icon $attrImageVector="$outlined:Check" />
                    <$icon $attrImageVector="$outlined:Home" />
                    <$icon $attrImageVector="$outlined:Image" />
                    <$icon $attrImageVector="$outlined:Share" />
                  </$row>      
                  <$row>
                    <$icon $attrImageVector="$rounded:Add" />
                    <$icon $attrImageVector="$rounded:Check" />
                    <$icon $attrImageVector="$rounded:Home" />
                    <$icon $attrImageVector="$rounded:Image" />
                    <$icon $attrImageVector="$rounded:Share" />
                  </$row>    
                  <$row>
                    <$icon $attrImageVector="$twoTone:Add" />
                    <$icon $attrImageVector="$twoTone:Check" />
                    <$icon $attrImageVector="$twoTone:Home" />
                    <$icon $attrImageVector="$twoTone:Image" />
                    <$icon $attrImageVector="$twoTone:Share" />
                  </$row>   
                  <$row>
                    <$icon $attrImageVector="$sharp:Add" />
                    <$icon $attrImageVector="$sharp:Check" />
                    <$icon $attrImageVector="$sharp:Home" />
                    <$icon $attrImageVector="$sharp:Image" />
                    <$icon $attrImageVector="$sharp:Share" />
                  </$row>                                                                          
                </$column>
                """
        )
    }

    @Test
    fun iconWithDifferentAutoMirroredStylesTest() {
        compareNativeComposableWithTemplate(
            delayBeforeScreenshot = 1000,
            nativeComposable = {
                Column {
                    Row {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.AddToHomeScreen,
                            contentDescription = ""
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.FactCheck,
                            contentDescription = ""
                        )
                        Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = "")
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = ""
                        )
                        Icon(imageVector = Icons.AutoMirrored.Filled.Chat, contentDescription = "")
                    }
                    Row {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.AddToHomeScreen,
                            contentDescription = ""
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.FactCheck,
                            contentDescription = ""
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.Send,
                            contentDescription = ""
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = ""
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.Chat,
                            contentDescription = ""
                        )
                    }
                    Row {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.AddToHomeScreen,
                            contentDescription = ""
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.FactCheck,
                            contentDescription = ""
                        )
                        Icon(imageVector = Icons.AutoMirrored.Rounded.Send, contentDescription = "")
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = ""
                        )
                        Icon(imageVector = Icons.AutoMirrored.Rounded.Chat, contentDescription = "")
                    }
                    Row {
                        Icon(
                            imageVector = Icons.AutoMirrored.TwoTone.AddToHomeScreen,
                            contentDescription = ""
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.TwoTone.FactCheck,
                            contentDescription = ""
                        )
                        Icon(imageVector = Icons.AutoMirrored.TwoTone.Send, contentDescription = "")
                        Icon(
                            imageVector = Icons.AutoMirrored.TwoTone.ArrowBack,
                            contentDescription = ""
                        )
                        Icon(imageVector = Icons.AutoMirrored.TwoTone.Chat, contentDescription = "")
                    }
                    Row {
                        Icon(
                            imageVector = Icons.AutoMirrored.Sharp.AddToHomeScreen,
                            contentDescription = ""
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.Sharp.FactCheck,
                            contentDescription = ""
                        )
                        Icon(imageVector = Icons.AutoMirrored.Sharp.Send, contentDescription = "")
                        Icon(
                            imageVector = Icons.AutoMirrored.Sharp.ArrowBack,
                            contentDescription = ""
                        )
                        Icon(imageVector = Icons.AutoMirrored.Sharp.Chat, contentDescription = "")
                    }
                }

            },
            template = """
                <$column>
                  <$row>
                    <$icon $attrImageVector="$autoMirroredFilled:AddToHomeScreen" />
                    <$icon $attrImageVector="$autoMirroredFilled:FactCheck" />
                    <$icon $attrImageVector="$autoMirroredFilled:Send" />
                    <$icon $attrImageVector="$autoMirroredFilled:ArrowBack" />
                    <$icon $attrImageVector="$autoMirroredFilled:Chat" />
                  </$row>
                  <$row>
                    <$icon $attrImageVector="$autoMirroredOutlined:AddToHomeScreen" />
                    <$icon $attrImageVector="$autoMirroredOutlined:FactCheck" />
                    <$icon $attrImageVector="$autoMirroredOutlined:Send" />
                    <$icon $attrImageVector="$autoMirroredOutlined:ArrowBack" />
                    <$icon $attrImageVector="$autoMirroredOutlined:Chat" />
                  </$row>      
                  <$row>
                    <$icon $attrImageVector="$autoMirroredRounded:AddToHomeScreen" />
                    <$icon $attrImageVector="$autoMirroredRounded:FactCheck" />
                    <$icon $attrImageVector="$autoMirroredRounded:Send" />
                    <$icon $attrImageVector="$autoMirroredRounded:ArrowBack" />
                    <$icon $attrImageVector="$autoMirroredRounded:Chat" />
                  </$row>    
                  <$row>
                    <$icon $attrImageVector="$autoMirroredTwoTone:AddToHomeScreen" />
                    <$icon $attrImageVector="$autoMirroredTwoTone:FactCheck" />
                    <$icon $attrImageVector="$autoMirroredTwoTone:Send" />
                    <$icon $attrImageVector="$autoMirroredTwoTone:ArrowBack" />
                    <$icon $attrImageVector="$autoMirroredTwoTone:Chat" />
                  </$row>   
                  <$row>
                    <$icon $attrImageVector="$autoMirroredSharp:AddToHomeScreen" />
                    <$icon $attrImageVector="$autoMirroredSharp:FactCheck" />
                    <$icon $attrImageVector="$autoMirroredSharp:Send" />
                    <$icon $attrImageVector="$autoMirroredSharp:ArrowBack" />
                    <$icon $attrImageVector="$autoMirroredSharp:Chat" />
                  </$row>                                                                          
                </$column>
                """
        )
    }
}