defmodule LiveViewNativeTest.Jetpack.Layouts.Jetpack do
  use LiveViewNative.Component,
    format: :jetpack

  import Phoenix.Controller, only: [get_csrf_token: 0]
  import LiveViewNative.Stylesheet.Component

  embed_templates "layouts_jetpack/*"
  embed_stylesheet MockSheet
end