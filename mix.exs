defmodule LiveViewNativeJetpack.MixProject do
  use Mix.Project

  def project do
    [
      app: :live_view_native_jetpack,
      version: "0.0.1",
      elixir: "~> 1.13",
      description: "LiveView Native platform for Jetpack Compose",
      package: package(),
      start_permanent: Mix.env() == :prod,
      deps: deps()
    ]
  end

  # Run "mix help compile.app" to learn about applications.
  def application do
    [
      extra_applications: [:logger, :live_view_native_platform]
    ]
  end

  # Run "mix help deps" to learn about dependencies.
  defp deps do
    [
      {:jason, "~> 1.2"},
      {:ex_doc, ">= 0.0.0", only: :dev, runtime: false},
      {:live_view_native_platform, "~> 0.0.7"}
    ]
  end

  @source_url "https://github.com/liveview-native/liveview-client-jetpack"

  # Hex package configuration
  defp package do
    %{
      maintainers: ["DockYard"],
      licenses: ["MIT"],
      links: %{
        "GitHub" => @source_url
      },
      source_url: @source_url
    }
  end
end