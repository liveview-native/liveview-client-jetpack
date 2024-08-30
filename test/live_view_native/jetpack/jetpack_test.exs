defmodule LiveViewNative.JetpackTest do
  use ExUnit.Case

  alias LiveViewNative.Jetpack

  describe "version normalization" do
    test "normalize_os_version" do
      assert [1, 2, 3] = Jetpack.normalize_os_version("1.2.3")
    end

    test "normalize_app_version" do
      assert [1, 2, 3] = Jetpack.normalize_app_version("1.2.3")
    end
  end
end
