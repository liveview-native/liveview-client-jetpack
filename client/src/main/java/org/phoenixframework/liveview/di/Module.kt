package org.phoenixframework.liveview.di

import android.net.Uri
import org.koin.core.module.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.phoenixframework.liveview.foundation.data.mappers.mergeRouteToBaseUrl
import org.phoenixframework.liveview.foundation.data.service.ChannelService
import org.phoenixframework.liveview.foundation.data.service.SocketService
import org.phoenixframework.liveview.foundation.domain.LiveViewCoordinator
import org.phoenixframework.liveview.foundation.ui.base.BaseThemeHolder
import org.phoenixframework.liveview.foundation.ui.modifiers.BaseModifiersParser
import org.phoenixframework.liveview.foundation.ui.registry.BaseComposableNodeFactory
import org.phoenixframework.liveview.ui.modifiers.ModifiersParser
import org.phoenixframework.liveview.ui.registry.ComposableNodeFactory
import org.phoenixframework.liveview.ui.theme.ThemeHolder
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
    viewModel {
        val route = it.get<String?>(0)
        val httpBaseUrl = it.get<String>(1)
        val method = it.get<String?>(2)
        val params = it.get<Map<String, Any?>>(3)
        val redirect = it.get<Boolean>(4)

        // The WebSocket URL is the same of the HTTP URL,
        // so we just copy the HTTP URL changing the schema (protocol)
        val uri = Uri.parse(httpBaseUrl)
        val webSocketScheme = if (uri.scheme?.lowercase() == "https") "wss" else "ws"
        val wsBaseUrl =
            uri.buildUpon().scheme(webSocketScheme).path("live/websocket").build().toString()

        val httpUrl = mergeRouteToBaseUrl(httpBaseUrl, route)

        LiveViewCoordinator(
            httpBaseUrl = httpUrl,
            wsBaseUrl = wsBaseUrl,
            method = method,
            params = params,
            redirect = redirect,
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
                parametersOf(get<SocketService>(), get<ChannelService>())
            }
        )
    }
}