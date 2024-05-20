defmodule LiveViewNative.Jetpack.TemplateRenderTest do
  use ExUnit.Case, async: false

  import Phoenix.ConnTest
  import Phoenix.LiveViewTest
  import LiveViewNativeTest

  @endpoint LiveViewNativeTest.Endpoint

  setup do
    {:ok, conn: Plug.Test.init_test_session(build_conn(), %{})}
  end

  test "can render the jetpack format", %{conn: conn} do
    {:ok, lv, _body} = native(conn, "/template", :jetpack)

    assert lv |> element("text") |> render() =~ "Template Jetpack Render 100"
  end

  test "can render the jetpack format with watch target", %{conn: conn} do
    {:ok, lv, _body} = native(conn, "/template", :jetpack, %{"target" => "watch"})

    assert lv |> element("text") |> render() =~ "Watch Target Template Jetpack Render 100"
  end
end
