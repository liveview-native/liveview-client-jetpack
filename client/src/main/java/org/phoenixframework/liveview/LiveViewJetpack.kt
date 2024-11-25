package org.phoenixframework.liveview

import org.koin.core.KoinApplication
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.parameter.parametersOf
import org.phoenixframework.liveview.di.clientModule
import org.phoenixframework.liveview.foundation.data.constants.HttpMethod.GET
import org.phoenixframework.liveview.foundation.di.foundationModule
import org.phoenixframework.liveview.foundation.domain.LiveViewCoordinator
import org.phoenixframework.liveview.foundation.ui.base.BaseThemeHolder
import org.phoenixframework.liveview.foundation.ui.base.ComposableViewFactory
import org.phoenixframework.liveview.foundation.ui.modifiers.BaseModifiersParser
import org.phoenixframework.liveview.foundation.ui.registry.BaseComposableNodeFactory

object LiveViewJetpack {
    private val koinApplication: KoinApplication = startKoin {
        modules(foundationModule, clientModule)
    }

    internal fun themeHolder(): BaseThemeHolder {
        return koinApplication.koin.get()
    }

    fun newLiveViewCoordinator(
        httpBaseUrl: String,
        route: String,
        method: String = GET,
        params: Map<String, Any?> = emptyMap(),
        redirect: Boolean = false
    ): LiveViewCoordinator {
        return koinApplication.koin.get {
            parametersOf(route, httpBaseUrl, method, params, redirect)
        }
    }

    fun getModifiersParser(): BaseModifiersParser {
        return koinApplication.koin.get()
    }

    internal fun getComposableNodeFactory(): BaseComposableNodeFactory {
        return koinApplication.koin.get()
    }

    fun getThemeHolder(): BaseThemeHolder {
        return koinApplication.koin.get()
    }

    fun registerComponent(tag: String, factory: ComposableViewFactory<*>) {
        koinApplication.koin.get<BaseComposableNodeFactory>().registerComponent(tag, factory)
    }

    fun unregisterComponent(tag: String) {
        koinApplication.koin.get<BaseComposableNodeFactory>().unregisterComponent(tag)
    }
}