package org.phoenixframework.liveview.foundation.di

import org.koin.dsl.module
import org.phoenixframework.liveview.foundation.data.mappers.DocumentParser
import org.phoenixframework.liveview.foundation.data.repository.Repository
import org.phoenixframework.liveview.foundation.data.service.ChannelService
import org.phoenixframework.liveview.foundation.data.service.SocketService
import org.phoenixframework.liveview.foundation.ui.registry.ComposableRegistry

val foundationModule = module {
    factory<DocumentParser> { paramScreenId ->
        DocumentParser(
            screenId = paramScreenId.get(),
            composableNodeFactory = get()
        )
    }
    single<SocketService> {
        SocketService()
    }
    factory<ChannelService> {
        ChannelService(socketService = get())
    }
    factory<Repository> {
        Repository(
            socketService = get<SocketService>(),
            channelService = get<ChannelService>()
        )
    }
    single {
        ComposableRegistry()
    }
}