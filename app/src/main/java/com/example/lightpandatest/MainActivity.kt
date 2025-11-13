package com.example.lightpandatest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.lightpandatest.ui.theme.LightpandaTestTheme

import com.dockyard.lightpandarenderer.LightpandaRenderer
import com.dockyard.lightpandarenderer.library.ElementLibrary
import com.dockyard.lightpandarenderer.library.foundation.registerFoundation
import com.dockyard.lightpandarenderer.library.material.registerMaterial3

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ElementLibrary.registerFoundation()
        ElementLibrary.registerMaterial3()

        enableEdgeToEdge()
        setContent {
            LightpandaTestTheme {
                LightpandaRenderer("http://192.168.1.196:4000")
            }
        }
    }
}