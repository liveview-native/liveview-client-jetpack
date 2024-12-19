defmodule LiveViewNativeTest.Jetpack.TemplateLive.Jetpack do
  use LiveViewNative.Component,
    format: :jetpack,
    as: :render
end

defmodule LiveViewNativeTest.Jetpack.TemplateLive do
  use Phoenix.LiveView,
    layout: false

  use LiveViewNative.LiveView,
    formats: [:jetpack],
    layouts: [
      jetpack: {LiveViewNativeTest.Jetpack.Layouts.Jetpack, :app}
    ],
    dispatch_to: &Module.concat/2

  def mount(_params, _session, socket) do
    {:ok, assign(socket, :count, 100)}
  end

  def render(assigns), do: ~H""
end
