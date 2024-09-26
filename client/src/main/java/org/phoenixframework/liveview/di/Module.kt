package org.phoenixframework.liveview.di

import android.net.Uri
import org.koin.core.module.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.phoenixframework.liveview.ui.theme.ThemeHolder
import org.phoenixframework.liveview.foundation.domain.LiveViewCoordinator
import org.phoenixframework.liveview.foundation.ui.base.BaseThemeHolder
import org.phoenixframework.liveview.foundation.ui.modifiers.BaseModifiersParser
import org.phoenixframework.liveview.foundation.ui.registry.BaseComposableNodeFactory
import org.phoenixframework.liveview.ui.modifiers.ModifiersParser
import org.phoenixframework.liveview.ui.registry.ComposableNodeFactory
import org.phoenixframework.liveview.ui.view.PhxChangeNotifier
import java.util.UUID

val clientModule = module {
    single<BaseComposableNodeFactory> {
        ComposableNodeFactory(composableRegistry = get())
    }
    single<BaseModifiersParser> {
        ModifiersParser()
    }
    single<BaseThemeHolder> {
        ThemeHolder()
    }
    single<PhxChangeNotifier> {
        PhxChangeNotifier()
    }
    viewModel {
        val httpBaseUrl = it.get<String>(0)
        val route = it.get<String?>(1)

        // The WebSocket URL is the same of the HTTP URL,
        // so we just copy the HTTP URL changing the schema (protocol)
        val uri = Uri.parse(httpBaseUrl)
        val webSocketScheme = if (uri.scheme == "https") "wss" else "ws"
        val wsBaseUrl =
            uri.buildUpon().scheme(webSocketScheme).path("live/websocket").build().toString()

        val httpUrl = if (route == null) httpBaseUrl else {
            val uriBuilder = Uri.parse(httpBaseUrl).buildUpon()
            if (route.startsWith('/')) {
                uriBuilder.path(route)
            } else {
                uriBuilder.appendPath(route)
            }
            uriBuilder.toString()
        }

        LiveViewCoordinator(
            httpBaseUrl = httpUrl,
            wsBaseUrl = wsBaseUrl,
            route = route,
            modifierParser = get(),
            themeHolder = get(),
            documentParser = get {
                parametersOf(
                    UUID.randomUUID().toString(), // Generate a random UUID to set the screen ID
                    get<BaseComposableNodeFactory>(), // composableNodeFactory
                )
            },
            repository = get {
                parametersOf(httpUrl, wsBaseUrl, get(), get())
            }
        )
    }
}