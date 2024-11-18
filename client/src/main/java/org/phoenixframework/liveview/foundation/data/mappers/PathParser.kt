package org.phoenixframework.liveview.foundation.data.mappers

import android.net.Uri

internal fun generateRelativePath(currentUrl: String, newUrl: String): String {
    val currentParts = currentUrl.split("/").toMutableList()
    val newParts = newUrl.split("/").toMutableList()

    // Remove empty parts caused by leading or trailing slashes
    currentParts.removeAll { it.isEmpty() }
    newParts.removeAll { it.isEmpty() }

    // Handle the case where newUrl is an absolute path
    if (newUrl.startsWith("/")) {
        return newUrl
    }

    // Handle the case where newUrl starts with "../"
    while (newParts.firstOrNull() == "..") {
        newParts.removeFirstOrNull()
        currentParts.removeLastOrNull()
    }

    val relativeParts = mutableListOf<String>()

    // Add remaining parts of newUrl
    relativeParts.addAll(newParts)

    return currentParts.joinToString(separator = "/", prefix = "/", postfix = "/") +
            relativeParts.joinToString(separator = "/")
}

internal fun mergeRouteToBaseUrl(httpBaseUrl: String, route: String?): String {
    return if (route == null) httpBaseUrl else {
        var routeToMerge = route
        val queryStringIndex = route.indexOf('?')
        // Check if the route has query parameters
        if (queryStringIndex > -1) {
            routeToMerge = route.substring(0, queryStringIndex)
        }

        val uriBuilder = Uri.parse(httpBaseUrl).buildUpon()
        if (routeToMerge.startsWith('/')) {
            uriBuilder.path(routeToMerge)
        } else {
            uriBuilder.appendPath(routeToMerge)
        }

        // If the route has query parameters...
        if (queryStringIndex > -1 && queryStringIndex < route.lastIndex) {
            val paramsAsString = route.substring(queryStringIndex + 1)
            val paramsAsStringList = paramsAsString.split('&')
            paramsAsStringList.forEach { paramAsString ->
                val keyValue = paramAsString.split('=')
                if (keyValue.size == 2) {
                    uriBuilder.appendQueryParameter(keyValue[0], keyValue[1])
                }
            }
        }
        uriBuilder.build().toString()
    }
}