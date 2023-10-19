package org.phoenixframework.liveview.data.mappers

import com.google.gson.Gson

// TODO Find a better way to parse properties which requires properties
// This object is being used to parse properties defined as objects.
// For now, these properties are being represented as JSON object.
internal object JsonParser {
    val gson = Gson()

    inline fun <reified T> parse(json: String): T? {
        return gson.fromJson(json, T::class.java)
    }
}