package com.dockyard.liveviewtest.liveview.components

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
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test

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
                <Row>
                  <Icon image-vector="filled:Image" />
                  <Icon image-vector="filled:Image" tint="#FFFF0000" />
                </Row>
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
                <Column>             
                    <Row>
                        <Icon image-vector="filled:Add" />
                        <Icon image-vector="filled:Check" />
                        <Icon image-vector="filled:Home" />
                        <Icon image-vector="filled:Image" />
                        <Icon image-vector="filled:Share" />
                    </Row>
                    <Row>
                        <Icon image-vector="outlined:Add" />
                        <Icon image-vector="outlined:Check" />
                        <Icon image-vector="outlined:Home" />
                        <Icon image-vector="outlined:Image" />
                        <Icon image-vector="outlined:Share" />
                    </Row>      
                    <Row>
                        <Icon image-vector="rounded:Add" />
                        <Icon image-vector="rounded:Check" />
                        <Icon image-vector="rounded:Home" />
                        <Icon image-vector="rounded:Image" />
                        <Icon image-vector="rounded:Share" />
                    </Row>    
                    <Row>
                        <Icon image-vector="twoTone:Add" />
                        <Icon image-vector="twoTone:Check" />
                        <Icon image-vector="twoTone:Home" />
                        <Icon image-vector="twoTone:Image" />
                        <Icon image-vector="twoTone:Share" />
                    </Row>   
                    <Row>
                        <Icon image-vector="sharp:Add" />
                        <Icon image-vector="sharp:Check" />
                        <Icon image-vector="sharp:Home" />
                        <Icon image-vector="sharp:Image" />
                        <Icon image-vector="sharp:Share" />
                    </Row>                                                                          
                </Column>
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
                <Column>
                    <Row>
                        <Icon image-vector="autoMirrored.filled:AddToHomeScreen" />
                        <Icon image-vector="autoMirrored.filled:FactCheck" />
                        <Icon image-vector="autoMirrored.filled:Send" />
                        <Icon image-vector="autoMirrored.filled:ArrowBack" />
                        <Icon image-vector="autoMirrored.filled:Chat" />
                    </Row>
                    <Row>
                        <Icon image-vector="autoMirrored.outlined:AddToHomeScreen" />
                        <Icon image-vector="autoMirrored.outlined:FactCheck" />
                        <Icon image-vector="autoMirrored.outlined:Send" />
                        <Icon image-vector="autoMirrored.outlined:ArrowBack" />
                        <Icon image-vector="autoMirrored.outlined:Chat" />
                    </Row>      
                    <Row>
                        <Icon image-vector="autoMirrored.rounded:AddToHomeScreen" />
                        <Icon image-vector="autoMirrored.rounded:FactCheck" />
                        <Icon image-vector="autoMirrored.rounded:Send" />
                        <Icon image-vector="autoMirrored.rounded:ArrowBack" />
                        <Icon image-vector="autoMirrored.rounded:Chat" />
                    </Row>    
                    <Row>
                        <Icon image-vector="autoMirrored.twoTone:AddToHomeScreen" />
                        <Icon image-vector="autoMirrored.twoTone:FactCheck" />
                        <Icon image-vector="autoMirrored.twoTone:Send" />
                        <Icon image-vector="autoMirrored.twoTone:ArrowBack" />
                        <Icon image-vector="autoMirrored.twoTone:Chat" />
                    </Row>   
                    <Row>
                        <Icon image-vector="autoMirrored.sharp:AddToHomeScreen" />
                        <Icon image-vector="autoMirrored.sharp:FactCheck" />
                        <Icon image-vector="autoMirrored.sharp:Send" />
                        <Icon image-vector="autoMirrored.sharp:ArrowBack" />
                        <Icon image-vector="autoMirrored.sharp:Chat" />
                    </Row>                                                                          
                </Column>
                """
        )
    }
}