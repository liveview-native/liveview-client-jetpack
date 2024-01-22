defmodule LiveViewNativeTest.Jetpack.InlineLive.Jetpack do
  use LiveViewNative.Component,
    format: :jetpack,
    as: :render

  def render(assigns, %{"target" => "watch"}) do
    ~LVN"""
    <Text>Watch Target Inline Jetpack Render <%= @count %></Text>
    """
  end

  def render(assigns, _interface) do
    ~LVN"""
    <Text>Inline Jetpack Render <%= @count %></Text>
    """
  end
end

defmodule LiveViewNativeTest.Jetpack.InlineLive do
  use Phoenix.LiveView,
    layout: false

  use LiveViewNative.LiveView,
    formats: [:jetpack],
    layouts: [
      jetpack: {LiveViewNativeTest.Jetpack.Layouts.Jetpack, :app}
    ]

  def mount(_params, _session, socket) do
    {:ok, assign(socket, :count, 100)}
  end

  def render(assigns), do: ~H""
end