package org.phoenixframework.liveview.addons

import org.phoenixframework.liveview.LiveViewJetpack
import org.phoenixframework.liveview.addons.constants.AddonsTypes
import org.phoenixframework.liveview.addons.ui.view.AnnotatedTextView
import org.phoenixframework.liveview.addons.ui.view.AsyncImageView

object LiveViewJetpackAddons {
    fun registerComponentByTag(vararg tags: String) {
        tags.forEach { tag ->
            LiveViewJetpack.run {
                when (tag) {
                    AddonsTypes.asyncImage ->
                        registerComponent(tag, AsyncImageView.Factory)

                    AddonsTypes.annotatedText ->
                        registerComponent(tag, AnnotatedTextView.Factory)
                }
            }
        }
    }
}