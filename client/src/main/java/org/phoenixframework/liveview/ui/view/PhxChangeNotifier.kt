package org.phoenixframework.liveview.ui.view

import android.util.Log

// TODO replace by a LocalComposition
class PhxChangeNotifier {

    private val listeners = mutableMapOf<String, Listener>()

    // id = "{screen id}_{component id}"
    internal fun notify(id: String, value: Any?) {
        Log.d(TAG, "-----------------------------")
        Log.d(TAG, "notify: id=$id | value=$value")
        try {
            Log.d(TAG, "listener instance: ${listeners[id]}")
            listeners[id]?.onChange(id, value)
        } catch (e: Exception) {
            Log.e(TAG, "Fail to notify component $id")
        }
    }

    fun registerListener(id: String, listener: Listener) {
        Log.d(TAG, "Registering Listener for component id=$id")
        listeners[id] = listener
    }

    fun unregisterListener(screenId: String) {
        listeners.filter { it.key.startsWith("${screenId}_") }.keys.forEach {
            Log.w(TAG, "Unregistering Listener for screen id=$screenId")
            listeners.remove(it)
        }
    }

    fun unregisterListener(listener: Listener) {
        listeners.filter { it.value == listener }.keys.forEach {
            listeners.remove(it)
        }
    }

    interface Listener {
        fun onChange(id: String, value: Any?)
    }

    companion object {
        private const val TAG = "NGVL"//"PhxChangeNotifier"
    }
}