defmodule LiveViewNative.Jetpack do
  @moduledoc false
  use LiveViewNative,
    format: :jetpack,
    component: LiveViewNative.Jetpack.Component,
    module_suffix: :Jetpack,
    template_engine: LiveViewNative.Engine,
    stylesheet_rules_parser: LiveViewNative.Jetpack.RulesParser
end
