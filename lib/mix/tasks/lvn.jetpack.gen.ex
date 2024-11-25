defmodule Mix.Tasks.Lvn.Jetpack.Gen do
  use Mix.Task

  alias Mix.LiveViewNative.Context

  @shortdoc "Generates the Jetpack Project for LiveView Native"

  @moduledoc """
  #{@shortdoc}

     $ mix lvn.jetpack.gen

  ## Options

  * `--no-live-form` - don't include `LiveViewNative.LiveForm` in the generated templates
  * `--no-copy` - don't copy files into your Phoenix project
  """

  def run(args) do
    if Mix.Project.umbrella?() do
      Mix.raise(
        "mix lvn.jetpack.gen must be invoked from within your *_web application root directory"
      )
    end

    args = List.insert_at(args, 0, "jetpack")

    context = Context.build(args, __MODULE__)

    files = files_to_be_generated(context)

    copy_new_files(context, files)

    :ok
  end

  def switches, do: [
    context_app: :string,
    web: :string,
    live_form: :boolean,
    copy: :boolean
  ]

  def validate_args!([format]), do: [format]
  def validate_args!(_args) do
    Mix.raise("""
    mix lvn.jetpack.gen does not take any arguments, only the following switches:

    --context-app
    --web
    --no-live-form
    --no-copy
    """)
  end

  def files_to_be_generated(context) do
    web_prefix = Mix.Phoenix.web_path(context.context_app)

    components_path = Path.join(web_prefix, "components")

    [{:eex, "core_components.ex", Path.join(components_path, "core_components.jetpack.ex")}]
  end

  defp copy_new_files(%Context{} = context, files) do
    apps = Mix.Project.deps_apps()

    live_form_opt? = Keyword.get(context.opts, :live_form, true)
    live_form_app? = Enum.member?(apps, :live_view_native_live_form) || Mix.env() == :test # yeah, I know but it's a generator

    binding = [
      context: context,
      assigns: %{
        app_namespace: inspect(context.base_module),
        gettext: true,
        test?: false,
        live_form?: live_form_opt? && live_form_app?
      }
    ]

    apps = Context.apps(context.format)

    Mix.Phoenix.copy_from(apps, "priv/templates/lvn.jetpack.gen", binding, files)

    context
  end
end
