package com.dockyard.liveviewtest.liveview.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.dockyard.liveviewtest.liveview.util.LiveViewComposableTest
import org.junit.Test

// FIXME seems like popups like Menus cannot be captured
//  https://github.com/pedrovgs/Shot/issues/275
//  Check setting the expanded property to true and verify if the test passes
class ExposedDropdownMenuBoxShotTest : LiveViewComposableTest() {
    @OptIn(ExperimentalMaterial3Api::class)
    @Test
    fun simpleExposedDropdownMenuBoxTest() {
        val testTag = "combobox"
        compareNativeComposableWithTemplate(
            testTag = testTag,
            nativeComposable = {
                Box {
                    ExposedDropdownMenuBox(
                        expanded = false,
                        onExpandedChange = {},
                        modifier = Modifier.testTag(testTag)

                    ) {
                        TextField(
                            value = "Choose an option",
                            onValueChange = {},
                            readOnly = true,
                            modifier = Modifier.menuAnchor(),
                        )
                        ExposedDropdownMenu(
                            expanded = true,
                            onDismissRequest = {},
                        ) {
                            DropdownMenuItem(onClick = { }, text = { Text(text = "Option A") })
                            DropdownMenuItem(onClick = { }, text = { Text(text = "Option B") })
                            DropdownMenuItem(onClick = { }, text = { Text(text = "Option C") })
                        }
                    }
                }
            },
            template = """
                <Box>
                  <ExposedDropdownMenuBox test-tag="$testTag" expanded="false">
                    <TextField phx-value="Choose an option" read-only="true" menu-anchor/>
                    <DropdownMenuItem phx-click="setDDOption" phx-value="A">
                      <Text>Option A</Text>
                    </DropdownMenuItem>
                    <DropdownMenuItem phx-click="setDDOption" phx-value="B">
                      <Text>Option B</Text>
                    </DropdownMenuItem>
                    <DropdownMenuItem phx-click="setDDOption" phx-value="C">
                      <Text>Option C</Text>
                    </DropdownMenuItem>
                  </ExposedDropdownMenuBox>  
                </Box>
                """,
        )
    }
}