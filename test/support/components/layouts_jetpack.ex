defmodule LiveViewNativeTest.Jetpack.Layouts.Jetpack do
  use LiveViewNative.Component,
    format: :jetpack

  import LiveViewNative.Component, only: [csrf_token: 1]

  embed_templates "layouts_jetpack/*"
end
