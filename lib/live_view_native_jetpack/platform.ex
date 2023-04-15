defmodule LiveViewNativeJetpack.Platform do
  defstruct [
    :app_name,
    custom_modifiers: [],
  ]

  defimpl LiveViewNativePlatform do
    require Logger

    def context(struct) do
      LiveViewNativePlatform.Context.define(:android,
        custom_modifiers: struct.custom_modifiers,
        otp_app: :live_view_native_jetpack
      )
    end

    def start_simulator(_struct, _opts \\ []) do
      raise "TODO: Implement this"
    end
  end
end
