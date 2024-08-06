package org.phoenixframework.liveview

import org.koin.core.KoinApplication
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.core.parameter.parametersOf
import org.phoenixframework.liveview.di.clientModule
import org.phoenixframework.liveview.foundation.di.foundationModule
import org.phoenixframework.liveview.foundation.domain.LiveViewCoordinator
import org.phoenixframework.liveview.foundation.ui.base.BaseThemeHolder
import org.phoenixframework.liveview.foundation.ui.modifiers.BaseModifiersParser
import org.phoenixframework.liveview.foundation.ui.registry.BaseComposableNodeFactory
import org.phoenixframework.liveview.ui.modifiers.ModifiersParser
import org.phoenixframework.liveview.ui.registry.ComposableNodeFactory

object LiveViewJetpack {
    private val koinApplication: KoinApplication = startKoin {
        modules(foundationModule, clientModule)
    }

    fun themeHolder(): BaseThemeHolder {
        return koinApplication.koin.get()
    }

    fun newLiveViewCoordinator(httpBaseUrl: String, route: String): LiveViewCoordinator {
        return koinApplication.koin.get {
            parametersOf(httpBaseUrl, route)
        }
    }

    fun getModifiersParser(): BaseModifiersParser {
        return koinApplication.koin.get()
    }

    fun getComposableNodeFactory(): BaseComposableNodeFactory {
        return koinApplication.koin.get()
    }

    fun getThemeHolder(): BaseThemeHolder {
        return koinApplication.koin.get()
    }

    fun stop() {
        stopKoin()
    }
}