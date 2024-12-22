defmodule LiveViewNativeTest.Jetpack.TestClient do
  @moduledoc false

  defstruct tags: %{
    form: "LiveForm",
    button: "Button",
    upload_input: :undefined,
    changeables: ~w(
      BasicTextField
      Checkbox
      IconToggleButton
      OutlinedTextField
      RadioButton
      RangeSlider
      TextField
      TriStateCheckbox
      Slider
      Switch
    )
  }
end
