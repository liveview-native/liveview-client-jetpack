defmodule LiveViewNativeJetpack.MixProject do
  use Mix.Project

  @version "0.3.0-alpha.1"

  def project do
    [
      app: :live_view_native_jetpack,
      version: @version,
      elixir: "~> 1.13",
      description: "LiveView Native platform for Jetpack Compose",
      elixirc_paths: elixirc_paths(Mix.env()),
      package: package(),
      start_permanent: Mix.env() == :prod,
      deps: deps()
    ]
  end

  # Run "mix help compile.app" to learn about applications.
  def application do
    [
      extra_applications: [:logger]
    ]
  end

  # Run "mix help deps" to learn about dependencies.
  defp deps do
    [
      {:ex_doc, ">= 0.0.0", only: :dev, runtime: false},
      {:floki, ">= 0.30.0", only: :test},
      # {:live_view_native, path: "../live_view_native", override: true},
      {:live_view_native, github: "liveview-native/live_view_native", override: true},
      # {:live_view_native_stylesheet, path: "../live_view_native_stylesheet", override: true},
      {:live_view_native_stylesheet, github: "liveview-native/live_view_native_stylesheet"},
      # {:live_view_native_test, path: "../live_view_native_test", only: :test, override: true},
      {:live_view_native_test, github: "liveview-native/live_view_native_test", only: :test},
      {:jason, "~> 1.2"},
      {:nimble_parsec, "~> 1.3"},
    ]
  end

  @source_url "https://github.com/liveview-native/liveview-client-jetpack"

  defp elixirc_paths(:test), do: ["lib", "test/support"]
  defp elixirc_paths(_), do: ["lib"]

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
