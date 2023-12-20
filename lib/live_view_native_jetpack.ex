defmodule LiveViewNativeJetpack do
  use LiveViewNativePlatform

  def platforms,
    do: [
      LiveViewNativeJetpack.Platform
    ]
end