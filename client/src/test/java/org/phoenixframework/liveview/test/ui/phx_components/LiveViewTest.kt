package org.phoenixframework.liveview.test.ui.phx_components

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.phoenixframework.liveview.ui.phx_components.generateRelativePath

@RunWith(AndroidJUnit4::class)
class LiveViewTest {

    @Test
    fun generateRelativePathTest() {
        var result = generateRelativePath("/", "/home")
        assertEquals("/home", result)

        result = generateRelativePath("/home", "/users")
        assertEquals("/users", result)

        result = generateRelativePath("/users", "/users/details")
        assertEquals("/users/details", result)

        result = generateRelativePath("/users/details", "../orders")
        assertEquals("/users/orders", result)

        result = generateRelativePath("/users/orders/order_1", "../../settings")
        assertEquals("/users/settings", result)

        result = generateRelativePath("/users/settings", "/")
        assertEquals("/", result)

        result = generateRelativePath("/users/settings", "foo/bar")
        assertEquals("/users/settings/foo/bar", result)
    }
}