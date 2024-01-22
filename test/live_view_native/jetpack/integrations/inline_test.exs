defmodule LiveViewNative.Jetpack.InlineRenderTest do
  use ExUnit.Case, async: false

  import Phoenix.ConnTest
  import Phoenix.LiveViewTest

  @endpoint LiveViewNativeTest.Endpoint

  setup do
    {:ok, conn: Plug.Test.init_test_session(build_conn(), %{})}
  end

  test "can render the jetpack format", %{conn: conn} do
    {:ok, lv, _body} = live(conn, "/inline?_format=jetpack")

    assert lv |> element("text") |> render() =~ "Inline Jetpack Render 100"
  end

  test "can render the jetpack format with watch target", %{conn: conn} do
    {:ok, lv, _body} = live(conn, "/inline?_format=jetpack&_interface[target]=watch")

    assert lv |> element("text") |> render() =~ "Watch Target Inline Jetpack Render 100"
  end
end