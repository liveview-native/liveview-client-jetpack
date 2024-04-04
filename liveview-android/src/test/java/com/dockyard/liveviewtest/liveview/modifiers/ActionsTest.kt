package com.dockyard.liveviewtest.liveview.modifiers

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ActionsTest: ModifierBaseTest() {

    @Test
    fun clickableTest() {
        assertModifierFromStyle(
            """
            %{"clickableTest" => [
              {:clickable, [], [[
                enabled: true, 
                onClickLabel: "string", 
                role: {:., [], [:Role, :Button]}, 
                onClick: {:__event__, [], ["my-click-event", []]}
              ]]}
            ]}  
            """.trimStyle(),
            Modifier.clickable(
                enabled = true,
                onClickLabel = "string",
                role = Role.Button,
                onClick = {

                }
            )
        )
    }
}