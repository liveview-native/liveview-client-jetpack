defmodule LiveViewNative.Jetpack.InlineRenderTest do
  use ExUnit.Case, async: false

  import Phoenix.ConnTest
  import Phoenix.LiveViewTest
  import LiveViewNativeTest

  @endpoint LiveViewNativeTest.Endpoint

  setup do
    {:ok, conn: Plug.Test.init_test_session(build_conn(), %{})}
  end

  test "can render the jetpack format", %{conn: conn} do
    {:ok, lv, _body} = native(conn, "/inline", :jetpack)

    assert lv |> element("text") |> render() =~ "Inline Jetpack Render 100"
  end

  test "can render the jetpack format with watch target", %{conn: conn} do
    {:ok, lv, _body} = native(conn, "/inline", :jetpack, %{"target" => "watch"})

    assert lv |> element("text") |> render() =~ "Watch Target Inline Jetpack Render 100"
  end
end
