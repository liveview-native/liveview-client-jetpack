defmodule LiveViewNative.Jetpack.Stylesheet do
  @moduledoc """
  Stylehseet extensions for embedding themes

  Jetpack support themes, if you want to opt into this within your Jetpack stylesheet

  ```elixir
  defmodule MyAppWeb.Styles.App.Jetack do
    use LiveViewNative.Stylesheet, :jetpack

    @theme material: %{
      colors: %{
        lightColors: %{
          primary: "#FFB90063"
        },
        darkColors: %{
          "primary": "#FFFFB1C8"
        }
      },
      typography: %{
        displayLarge: %{
          fontFamily: "Orbitron"
        }
      }
    }
  end
  ```

  Multiple themes are supported as well:

  ```elixir
  @theme theme_1: theme_1_map,
  @theme theme_2: theme_2_map
  ```

  Please refer to [Jetpack Compose's own themeing documenation for more information.](https://developer.android.com/develop/ui/compose/designsystems)
  """

  defmacro __using__(_) do
    quote do
      Module.register_attribute(__MODULE__, :theme, accumulate: true)
    end
  end

  defmacro __before_compile__(_env) do
    quote location: :keep do
      def special do
        %{
          "__themes__" => Enum.into(List.flatten(@theme), %{}, &(&1))}
      end
    end
  end
end
