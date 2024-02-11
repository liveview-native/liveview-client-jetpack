# LiveViewNative

> #### Client Spec {: .info}
>
> format: `:jetpack`
>
> module_suffix: `Jetpack`
>
> template_sigil: `~LVN`
>
> component: `LiveViewNative.Jetpack.Component`

The LiveViewNative Jetpack package lets you use Phoenix LiveView to build native Android apps with Jetpack Compose.

## Installation

1. In Android Studio, select *File → Add Packages...*
2. Enter the package URL `https://github.com/liveview-native/liveview-client-jetpack`
3. Select *Add Package*

## Usage
Create a `LiveView` to connect to a Phoenix server running on `http://localhost:4000`.

```kotlin
// add jetpack
```

Now when you start up your app, the LiveView will automatically connect and serve your native app.

## Elixir Library

### Installation

If [available in Hex](https://hex.pm/docs/publish), the package can be installed
by adding `live_view_native_jetpack` to your list of dependencies in `mix.exs`:

```elixir
def deps do
  [
    {:live_view_native_jetpack, "~> 0.3.0"}
  ]
end
```

Then add the `Jepack` plugin to your list of LiveView Native plugins:

```elixir
config :live_view_native, plugins: [
  LiveViewNative.Jetpack
]
```

## Usage

This plugin provides the Jetpack rendering behavior of a Phoenix LiveView. Start by adding this plugin to a LiveView. We do this with `LiveViewNative.LiveView`:

```elixir
defmodule MyAppWeb.HomeLive do
  use MyAppWeb :live_view
  use LiveViewNative.LiveView,
    formats: [:swiftui],
    layouts: [
      swiftui: {MyAppWeb.Layouts.SwiftUI, :app}
    ]

end
```

then just like all format LiveView Native rendering components you will create a new module namespaced under the calling module with the `module_suffix` appended:

```elixir
defmodule MyAppWeb.HomeLive.SwiftUI do
  use LiveViewNative.Component,
    format: :swiftui

  def render(assigns, _interface) do
    ~LVN"""
    <Text>Hello, SwiftUI!</Text>
    """
  end
end
```

Further details on additional options and an explanation of template rendering vs using `render/2` are in the LiveView Native docs.

Documentation can be generated with [ExDoc](https://github.com/elixir-lang/ex_doc)
and published on [HexDocs](https://hexdocs.pm). Once published, the docs can
be found at <https://hexdocs.pm/live_view_native_swiftui>.

## Resources

- Browse the [documentation](https://liveview-native.github.io/liveview-client-swiftui/documentation/liveviewnative/) to read about the API.
- Check out the [ElixirConf '22 chat app](https://github.com/liveview-native/elixirconf_chat) for an example of a complete app.
