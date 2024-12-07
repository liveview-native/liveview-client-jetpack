defmodule LiveViewNative.Jetpack.TemplateRenderTest do
  use ExUnit.Case, async: false

  import Phoenix.ConnTest
  import LiveViewNativeTest

  @endpoint LiveViewNativeTest.Endpoint

  setup do
    {:ok, conn: Plug.Test.init_test_session(build_conn(), %{})}
  end

  test "can render the jetpack format", %{conn: conn} do
    {:ok, lv, _body} = live(conn, "/template", _format: :jetpack)

    assert lv |> element("Text") |> render() =~ "Template Jetpack Render 100"
  end

  test "can render the jetpack format with watch target", %{conn: conn} do
    {:ok, lv, _body} = live(conn, "/template", _format: :jetpack, _interface: %{"target" => "watch"})

    assert lv |> element("Text") |> render() =~ "Watch Target Template Jetpack Render 100"
  end
end
