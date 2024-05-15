defmodule LiveViewNative.Jetpack.MixProject do
  use Mix.Project

  @version "0.3.0-alpha.3"
  @source_url "https://github.com/liveview-native/liveview-client-jetpack"

  def project do
    [
      app: :live_view_native_jetpack,
      version: @version,
      elixir: "~> 1.15",
      description: description(),
      start_permanent: Mix.env() == :prod,
      deps: deps(),
      package: package(),
      elixirc_paths: elixirc_paths(Mix.env()),
      docs: docs()
    ]
  end

  def application do
    [
      extra_applications: [:logger]
    ]
  end

  defp elixirc_paths(:test), do: ["lib", "test/support"]
  defp elixirc_paths(_), do: ["lib"]

  defp deps do
    [
      {:ex_doc, ">= 0.0.0", only: :dev, runtime: false},
      {:floki, ">= 0.30.0", only: :test},
      {:live_view_native, github: "liveview-native/live_view_native", branch: "main", override: true},
      {:live_view_native_stylesheet, github: "liveview-native/live_view_native_stylesheet", branch: "main"},
      {:live_view_native_test, github: "liveview-native/live_view_native_test", tag: "v0.3.0", only: :test},
      {:jason, "~> 1.2"},
      {:nimble_parsec, "~> 1.3"}
    ]
  end

  defp docs do
    [
      extras: ["README.md"],
      main: "readme",
      source_url: @source_url,
      source_ref: "v#{@version}"
    ]
  end

  defp description, do: "LiveView Native Jetpack Compose client"

  defp package do
    %{
      maintainers: ["Brian Cardarella"],
      licenses: ["MIT"],
      links: %{
        "GitHub" => @source_url,
        "Built by DockYard, Expert Elixir & Phoenix Consultants" => "https://dockyard.com/phoenix-consulting"
      }
    }
  end
end
