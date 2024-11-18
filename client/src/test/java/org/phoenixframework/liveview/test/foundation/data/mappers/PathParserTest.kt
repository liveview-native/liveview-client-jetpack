package org.phoenixframework.liveview.test.foundation.data.mappers

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.phoenixframework.liveview.foundation.data.mappers.generateRelativePath
import org.phoenixframework.liveview.foundation.data.mappers.mergeRouteToBaseUrl

@RunWith(AndroidJUnit4::class)
class PathParserTest {
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

    @Test
    fun mergeRouteToBaseUrlTest() {
        val baseUrl = "http://www.foo.bar"
        assertEquals(baseUrl, mergeRouteToBaseUrl(baseUrl, null))
        assertEquals("$baseUrl/login", mergeRouteToBaseUrl(baseUrl, "/login"))
        assertEquals("$baseUrl/login", mergeRouteToBaseUrl(baseUrl, "login"))
        assertEquals(
            "$baseUrl/login/user",
            mergeRouteToBaseUrl("$baseUrl/login", "user")
        )
        assertEquals(
            "$baseUrl/login?action=settings",
            mergeRouteToBaseUrl(baseUrl, "/login?action=settings")
        )
        assertEquals(
            "$baseUrl/login?action=settings",
            mergeRouteToBaseUrl(baseUrl, "login?action=settings")
        )
        assertEquals(
            "$baseUrl/login?action=settings&_format=jetpack",
            mergeRouteToBaseUrl(baseUrl, "login?action=settings&_format=jetpack")
        )
    }
}