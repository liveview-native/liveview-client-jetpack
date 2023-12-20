# LiveViewNative

The LiveViewNative Jetpack package lets you use Phoenix LiveView to build native Android apps with Jetpack Compose.

## Installation in a LiveView app

To use LiveView Native, you must have an existing Phoenix project. 
If you don't have one, follow the [Up and Running](https://hexdocs.pm/phoenix/up_and_running.html) 
section of the official Phoenix guide to create one.

Add the dependencies in `mix.exs` file.
```elixir
defp deps do
  [
    # Other dependencies
    {:live_view_native, "~> 0.1.2"},
    {:live_view_native_jetpack, path: "../../liveview-client-jetpack"}
  ]
end
```

Then configura the platform support for LiveView Native in `config.exs`.
```elixir
# Define platform support for LiveView Native
config :live_view_native,
  plugins: [
    LiveViewNativeJetpack
  ]
```

Finally, implement a `render` function using a `JETPACK` sigil.

```elixir
defmodule HelloWeb.HelloLive do
  use Phoenix.LiveView
  use LiveViewNative.LiveView

  @impl true
  def render(%{platform_id: :jetpack} = assigns) do
    ~JETPACK"""
    <Text>Hello from LiveView Native Jetpack!</Text>
    """
  end
end
```
