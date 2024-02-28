package org.phoenixframework.liveview.domain.base

typealias PushEvent = (type: String, event: String, value: Any?, target: Int?) -> Unit