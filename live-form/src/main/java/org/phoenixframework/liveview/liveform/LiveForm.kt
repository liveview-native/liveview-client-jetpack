package org.phoenixframework.liveview.liveform

import org.phoenixframework.liveview.LiveViewJetpack
import org.phoenixframework.liveview.liveform.constants.LiveFormTypes
import org.phoenixframework.liveview.liveform.ui.LiveFormView

object LiveForm {
    fun registerComponents() {
        LiveViewJetpack.registerComponent(LiveFormTypes.form, LiveFormView.Factory)
    }
}