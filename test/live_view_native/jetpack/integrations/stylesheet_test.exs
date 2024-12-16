defmodule LiveViewNative.Jetpack.StylesheetTest do
  use ExUnit.Case, async: false

  describe "themes" do
    test "material theme correctly encodes and embeds into the stylesheet" do
      styles = File.read!("priv/static/assets/mock_sheet.styles") |> :json.decode()
      assert get_in(styles, ["__themes__", "material", "colors", "lightColors", "primary"]) == "#FFB90063"
    end
  end
end
