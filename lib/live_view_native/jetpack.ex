defmodule LiveViewNative.Jetpack do
  @moduledoc false
  use LiveViewNative,
    format: :jetpack,
    component: LiveViewNative.Jetpack.Component,
    module_suffix: :Jetpack,
    template_engine: LiveViewNative.Engine,
    stylesheet_rules_parser: LiveViewNative.Jetpack.RulesParser

  def normalize_os_version(os_version),
    do: normalize_version(os_version)

  def normalize_app_version(app_version),
    do: normalize_version(app_version)

  defp normalize_version(version) do
    version
    |> String.split(".")
    |> Enum.map(fn(number) ->
      {number, _rem} = Integer.parse(number)
      number
    end)
  end
end
