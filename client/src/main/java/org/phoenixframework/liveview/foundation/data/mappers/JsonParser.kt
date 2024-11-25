package org.phoenixframework.liveview.foundation.data.mappers

import com.google.gson.Gson

// This object is being used to parse properties defined as objects.
// For now, these properties are being represented as JSON object.
object JsonParser {
    val gson = Gson()

    inline fun <reified T> parse(json: String): T? {
        return gson.fromJson(json, T::class.java)
    }

    inline fun <reified T> toString(obj: T): String? {
        return try {
            gson.toJson(obj)
        } catch (e: Exception) {
            null
        }
    }
}