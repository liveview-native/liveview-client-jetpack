import Config

config :live_view_native_stylesheet,
  annotations: false

config :live_view_native, plugins: [
  LiveViewNative.Jetpack
]

config :phoenix_template, format_encoders: [
  jetpack: Phoenix.HTML.Engine
]

config :phoenix, template_engines: [
  neex: LiveViewNative.Engine
]

config :mime, :types, %{
  "text/jetpack" => ["jetpack"]
}

config :live_view_native_test_endpoint,
  formats: [:jetpack],
  otp_app: :live_view_native_jetpack,
  routes: [
    %{path: "/inline", module: LiveViewNativeTest.Jetpack.InlineLive},
    %{path: "/template", module: LiveViewNativeTest.Jetpack.TemplateLive}
  ]

config :phoenix, :plug_init_mode, :runtime

config :live_view_native_stylesheet,
  content: [
    jetpack: [
      "test/**/*.*"

    ]
  ],
  output: "priv/static/assets"
