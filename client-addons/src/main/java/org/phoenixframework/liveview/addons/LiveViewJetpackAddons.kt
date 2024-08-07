package org.phoenixframework.liveview.addons

import org.phoenixframework.liveview.LiveViewJetpack
import org.phoenixframework.liveview.addons.constants.AddonsTypes
import org.phoenixframework.liveview.addons.ui.view.AsyncImageView

object LiveViewJetpackAddons {
    fun registerComponentByTag(vararg tags: String) {
        tags.forEach { tag ->
            when (tag) {
                AddonsTypes.asyncImage ->
                    LiveViewJetpack.registerComponent(tag, AsyncImageView.Factory)
            }
        }
    }
}