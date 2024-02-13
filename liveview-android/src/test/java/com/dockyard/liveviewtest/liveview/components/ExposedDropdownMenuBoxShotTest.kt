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
import org.phoenixframework.liveview.data.constants.Attrs.attrExpanded
import org.phoenixframework.liveview.data.constants.Attrs.attrMenuAnchor
import org.phoenixframework.liveview.data.constants.Attrs.attrPhxClick
import org.phoenixframework.liveview.data.constants.Attrs.attrPhxValue
import org.phoenixframework.liveview.data.constants.Attrs.attrReadOnly
import org.phoenixframework.liveview.data.constants.Attrs.attrTestTag
import org.phoenixframework.liveview.domain.base.ComposableTypes.box
import org.phoenixframework.liveview.domain.base.ComposableTypes.dropdownMenuItem
import org.phoenixframework.liveview.domain.base.ComposableTypes.exposedDropdownMenuBox
import org.phoenixframework.liveview.domain.base.ComposableTypes.text
import org.phoenixframework.liveview.domain.base.ComposableTypes.textField

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
                <$box>
                  <$exposedDropdownMenuBox $attrTestTag="$testTag" $attrExpanded="false">
                    <$textField $attrPhxValue="Choose an option" $attrReadOnly="true" $attrMenuAnchor/>
                    <$dropdownMenuItem $attrPhxClick="setDDOption" $attrPhxValue="A">
                      <$text>Option A</$text>
                    </$dropdownMenuItem>
                    <$dropdownMenuItem $attrPhxClick="setDDOption" $attrPhxValue="B">
                      <$text>Option B</$text>
                    </$dropdownMenuItem>
                    <$dropdownMenuItem $attrPhxClick="setDDOption" $attrPhxValue="C">
                      <$text>Option C</$text>
                    </$dropdownMenuItem>
                  </$exposedDropdownMenuBox>  
                </$box>
                """
        )
    }
}