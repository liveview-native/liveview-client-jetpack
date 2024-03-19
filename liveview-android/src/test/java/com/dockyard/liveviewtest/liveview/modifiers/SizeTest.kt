package com.dockyard.liveviewtest.liveview.modifiers

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotSame
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.phoenixframework.liveview.data.mappers.modifiers.ModifiersParser
import org.phoenixframework.liveview.data.mappers.modifiers.ModifiersParser.fromStyle

@RunWith(AndroidJUnit4::class)
class SizeTest {
    @Before
    fun clearStyleCacheTable() {
        ModifiersParser.clearCacheTable()
    }

    @Test
    fun heightInDp() {
        val style = """
            %{"heightInDp" => [
                {:height, [], [100]},
            ]}
            """
        val result = Modifier.fromStyle(style, null)
        val modifier = Modifier.then(Modifier.height(100.dp))
        assertEquals(result, modifier)
    }

    @Test
    fun heightInDpWithInvalidValueMustFail() {
        val style = """
            %{"heightInDpWithInvalidValueMustFail" => [
                {:height, [], ["100"]},
            ]}
            """
        val modifier = Modifier.fromStyle(style, null)
        assertEquals(modifier, Modifier)
    }

    @Test
    fun heightInDpNamed() {
        val style = """
            %{"heightInDpNamed" => [
                {:height, [], [[height: 100]]},
            ]}
            """
        val result = Modifier.fromStyle(style, null)
        val modifier = Modifier.then(Modifier.height(100.dp))
        assertEquals(result, modifier)
    }

    @Test
    fun heightInDpNamedWithInvalidValueMustFail() {
        val style = """
            %{"heightInDpNamedWithInvalidValueMustFail" => [
                {:height, [], [[height: "100"]]},
            ]}
            """
        val result = Modifier.fromStyle(style, null)
        val modifier = Modifier.then(Modifier.height(100.dp))
        assertNotSame(result, modifier)
    }

    @Test
    fun heightIntrinsicTest() {
        val style = """
            %{"heightIntrinsicTest" => [
                {:height, [], [{:., :IntrinsicSize, :Min}]},
            ]}
            """
        val result = Modifier.fromStyle(style, null)
        val modifier = Modifier.then(Modifier.height(IntrinsicSize.Min))
        assertNotSame(result, modifier)
    }

    @Test
    fun aspectRatioTest() {
        val style = """
            %{"aspectRatioTest" => [
                {:aspectRatio, [], [1.33]},
            ]}
            """
        val result = Modifier.fromStyle(style, null)
        val modifier = Modifier.aspectRatio(1.33f)
        assertEquals(result, modifier)
    }

    @Test
    fun aspectRatioWithMatchConstraintsTest() {
        val style = """
            %{"aspectRatioWithMatchConstraintsTest" => [
                {:aspectRatio, [], [1.33, true]},
            ]}
            """
        val result = Modifier.fromStyle(style, null)
        val modifier = Modifier.aspectRatio(1.33f, true)
        assertEquals(result, modifier)
    }

    @Test
    fun aspectRatioWithNamedParamsTest() {
        val style = """
            %{"aspectRatioWithNamedParamsTest" => [
                {:aspectRatio, [], [[ratio: 1.33, matchConstraints: true]]},
            ]}
            """
        val result = Modifier.fromStyle(style, null)
        val modifier = Modifier.aspectRatio(1.33f, true)
        assertEquals(result, modifier)
    }

    @Test
    fun fillMaxHeightTest() {
        val style = """
            %{"fillMaxHeightTest" => [
                {:fillMaxHeight, [], []},
            ]}
            """
        val result = Modifier.fromStyle(style, null)
        val modifier = Modifier.fillMaxHeight()
        assertEquals(result, modifier)
    }

    @Test
    fun fillMaxHeightWithFractionTest() {
        val style = """
            %{"fillMaxHeightWithFractionTest" => [
                {:fillMaxHeight, [], [0.5]},
            ]}
            """
        val result = Modifier.fromStyle(style, null)
        val modifier = Modifier.fillMaxHeight(0.5f)
        assertEquals(result, modifier)
    }

    @Test
    fun fillMaxWidthTest() {
        val style = """
            %{"fillMaxWidthTest" => [
                {:fillMaxWidth, [], []},
            ]}
            """
        val result = Modifier.fromStyle(style, null)
        val modifier = Modifier.fillMaxWidth()
        assertEquals(result, modifier)
    }

    @Test
    fun fillMaxWidthWithFractionTest() {
        val style = """
            %{"fillMaxWidthWithFractionTest" => [
                {:fillMaxWidth, [], [0.5]},
            ]}
            """
        val result = Modifier.fromStyle(style, null)
        val modifier = Modifier.fillMaxWidth(0.5f)
        assertEquals(result, modifier)
    }

    @Test
    fun widthInDp() {
        val style = """
            %{"widthInDp" => [
                {:width, [], [100]},
            ]}
            """
        val result = Modifier.fromStyle(style, null)
        val modifier = Modifier.then(Modifier.width(100.dp))
        assertEquals(result, modifier)
    }

    @Test
    fun widthInDpWithInvalidValueMustFail() {
        val style = """
            %{"widthInDpWithInvalidValueMustFail" => [
                {:width, [], ["100"]},
            ]}
            """
        val modifier = Modifier.fromStyle(style, null)
        assertEquals(modifier, Modifier)
    }

    @Test
    fun widthInDpNamed() {
        val style = """
            %{"widthInDpNamed" => [
                {:width, [], [[width: 100]]},
            ]}
            """
        val result = Modifier.fromStyle(style, null)
        val modifier = Modifier.then(Modifier.width(100.dp))
        assertEquals(result, modifier)
    }

    @Test
    fun widthInDpNamedWithInvalidValueMustFail() {
        val style = """
            %{"widthInDpNamedWithInvalidValueMustFail" => [
                {:width, [], [[width: "100"]]},
            ]}
            """
        val result = Modifier.fromStyle(style, null)
        val modifier = Modifier.then(Modifier.height(100.dp))
        assertNotSame(result, modifier)
    }

    @Test
    fun widthIntrinsicTest() {
        val style = """
            %{"widthIntrinsicTest" => [
                {:width, [], [{:., :IntrinsicSize, :Min}]},
            ]}
            """
        val result = Modifier.fromStyle(style, null)
        val modifier = Modifier.then(Modifier.width(IntrinsicSize.Min))
        assertNotSame(result, modifier)
    }

    @Test
    fun sizeTest() {
        val style = """
            %{"sizeTest" => [
                {:size, [], [50]},
            ]}
            """
        val result = Modifier.fromStyle(style, null)
        val modifier = Modifier.then(Modifier.size(50.dp))
        assertNotSame(result, modifier)
    }

    @Test
    fun sizeWHTest() {
        val style = """
            %{"sizeWHTest" => [
                {:size, [], [50, 100]},
            ]}
            """
        val result = Modifier.fromStyle(style, null)
        val modifier = Modifier.then(Modifier.size(50.dp, 100.dp))
        assertNotSame(result, modifier)
    }

    @Test
    fun sizeNamedTest() {
        val style = """
            %{"sizeNamedTest" => [
                {:size, [], [[height: 25, width: 50]]},
            ]}
            """
        val result = Modifier.fromStyle(style, null)
        val modifier = Modifier.then(Modifier.size(height = 25.dp, width = 50.dp))
        assertNotSame(result, modifier)
    }
}