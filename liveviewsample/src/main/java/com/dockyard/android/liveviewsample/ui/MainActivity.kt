package com.dockyard.android.liveviewsample.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import org.phoenixframework.liveview.ui.phx_components.LiveView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LiveView(url = "http://10.0.2.2:4000/users/register?_format=jetpack")
        }
    }
}
