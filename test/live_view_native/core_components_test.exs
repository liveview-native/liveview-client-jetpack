defmodule LiveViewNative.Jetpack.CoreComponentsTest do
  use ExUnit.Case

  import LiveViewNativeTest.CoreComponents.Jetpack

  import LiveViewNative.LiveForm.Component
  import LiveViewNative.Component, only: [sigil_LVN: 2]
  import LiveViewNative.Template.Parser, only: [parse_document!: 1]

  defmacro sigil_X({:<<>>, _, [binary]}, []) when is_binary(binary) do
    Macro.escape(parse_sorted!(binary))
  end

  defmacro sigil_x(term, []) do
    quote do
      unquote(__MODULE__).parse_sorted!(unquote(term))
    end
  end

  def t2h(template) do
    template
    |> Phoenix.LiveViewTest.rendered_to_string()
    |> parse_sorted!()
  end

  @doc """
  Parses LVN templates into Floki format with sorted attributes.
  """
  def parse_sorted!(value) do
    value
    |> parse_document!()
    |> Enum.map(&normalize_attribute_order/1)
  end

  defp normalize_attribute_order({node_type, attributes, content}),
    do: {node_type, Enum.sort(attributes), Enum.map(content, &normalize_attribute_order/1)}

  defp normalize_attribute_order(values) when is_list(values),
    do: Enum.map(values, &normalize_attribute_order/1)

  defp normalize_attribute_order(value), do: value

  describe "input/1" do
    test "hidden" do
      assigns = %{}

      template = ~LVN"""
      <.input type="hidden" value="123" name="test-name" />
      """

      assert t2h(template) ==
        ~X"""
       <Spacer name="test-name" value="123" />
      """
    end

    test "DatePicker" do
      assigns = %{}

      template = ~LVN"""
      <.input type="DatePicker" value="123" name="test-name" label="test-label" />
      """

      assert t2h(template) ==
        ~X"""
        <Column>
          <DatePicker name="test-name" initialSelectedDateMillis="123">
            <Text template="title">test-label</Text>
          </DatePicker>
        </Column>
        """
    end

    test "MultiDatePicker" do
      assigns = %{}

      template = ~LVN"""
      <.input type="MultiDatePicker" value="[123, 456]" name="test-name" label="test-label" />
      """

      assert t2h(template) ==
        ~X"""
        <Column>
          <DateRangePicker initialSelectedEndDateMillis="456" initialSelectedStartDateMillis="123" name="test-name">
            <Text template="title">test-label</Text>
          </DateRangePicker>
        </Column>
        """
    end

    test "Picker" do
      assigns = %{}

      template = ~LVN"""
      <.input type="Picker" value="123" name="test-name" label="test-label" options={[{"a", "b"}]} />
      """

      assert t2h(template) ==
        ~X"""
        <Column>
          <ExposedDropdownMenuBox name="test-name" value="123">
            <TextField readOnly="true" style="menuAnchor()" value="">
              <Text template="label">test-label</Text>
            </TextField>
            <ExposedDropdownMenu>
              <DropdownMenuItem phx-value="b">
                <Text>a</Text>
              </DropdownMenuItem>
            </ExposedDropdownMenu>
          </ExposedDropdownMenuBox>
        </Column>
        """
    end

    test "SingleChoiceSegmentedButtonRow" do
      assigns = %{}

      template = ~LVN"""
      <.input type="SingleChoiceSegmentedButtonRow" value="123" name="test-name" label="test-label" options={[{"a", "b"}]} />
      """

      assert t2h(template) ==
        ~X"""
        <Column>
          <Text template="label">test-label</Text>
          <SingleChoiceSegmentedButtonRow name="test-name" value="123">
            <SegmentedButton phx-value="b" selected="false">
              <Text template="label">a</Text>
            </SegmentedButton>
          </SingleChoiceSegmentedButtonRow>
        </Column>
        """
    end

    test "Slider" do
      assigns = %{}

      template = ~LVN"""
      <.input type="Slider" value="123" name="test-name" label="test-label" min={1} max={200} />
      """

      assert t2h(template) ==
        ~X"""
        <Column>
          <Text>test-label</Text>
          <Slider name="test-name" value="123" minValue="1" maxValue="200"></Slider>
        </Column>
        """
    end

    test "TextField" do
      assigns = %{}

      template = ~LVN"""
      <.input type="TextField" value="123" name="test-name" prompt="test-prompt" label="test-label" />
      """

      assert t2h(template) ==
        ~X"""
        <Column>
          <TextField value="123" name="test-name" isError="false">
            <Text template="label">test-label</Text>
          </TextField>
        </Column>
        """
    end

    test "TextField with placeholder" do
      assigns = %{}

      template = ~LVN"""
      <.input type="TextField" value="123" name="test-name" prompt="test-prompt" placeholder="test-placeholder" label="test-label" />
      """

      assert t2h(template) ==
        ~X"""
        <Column>
          <TextField isError="false" name="test-name" value="123">
            <Text template="label">test-label</Text>
            <Text template="placeholder">test-placeholder</Text>
          </TextField>
        </Column>
        """
    end

    test "SecureField" do
      assigns = %{}

      template = ~LVN"""
      <.input type="SecureField" value="123" name="test-name" prompt="test-prompt" label="test-label" />
      """

      assert t2h(template) ==
        ~X"""
        <Column>
          <TextField isError="false" prompt="test-prompt" name="test-name" value="123" visualTransformation="password">
            <Text template="label">test-label</Text>
          </TextField>
        </Column>
        """
    end

    test "SecureField with placeholder" do
      assigns = %{}

      template = ~LVN"""
      <.input type="SecureField" value="123" name="test-name" prompt="test-prompt" placeholder="test-placeholder" label="test-label" />
      """

      assert t2h(template) ==
        ~X"""
        <Column>
          <TextField isError="false" prompt="test-prompt" name="test-name" value="123" visualTransformation="password">
            <Text template="label">test-label</Text>
            <Text template="placeholder">test-placeholder</Text>
          </TextField>
        </Column>
        """
    end

    test "Checkbox" do
      assigns = %{}

      template = ~LVN"""
      <.input type="Checkbox" checked={true} name="test-name" label="test-label" />
      """

      assert t2h(template) ==
        ~X"""
        <Column>
          <Row style="wrapContentWidth()" verticalAlignment="CenterVertically">
            <CheckBox name="test-name" checked="true"/>
            <Text>test-label</Text>
          </Row>
        </Column>
        """
    end
  end

  describe "error/1" do
    test "renders the error message" do
      assigns = %{}

      template = ~LVN"""
      <.error>
        <Text>Error!</Text>
      </.error>
      """

      assert t2h(template) ==
        ~X"""
        <Text color="error" textStyle="bodyMedium">
          <Text>Error!</Text>
        </Text>
        """

    end

  end

  describe "header/1" do

  end

  describe "modal/1" do

  end

  describe "flash/1" do

  end

  describe "flash_group/1" do

  end

  describe "simple_form/1" do
    test "can render empty form" do
      params = %{}
      form = to_form(params, as: "user")

      assigns = %{form: form}

      template = ~LVN"""
        <.simple_form for={@form}>
        </.simple_form>
        """

      assert t2h(template) ==
        ~X"""
        <LiveForm>
        </LiveForm>
        """
    end

    test "can render form with an input" do
      params = %{"email" => "test@example.com"}
      form = to_form(params, as: "user")

      assigns = %{form: form}

      template = ~LVN"""
        <.simple_form for={@form}>
          <.input field={@form[:email]} label="Email"/>
        </.simple_form>
        """

      assert t2h(template) ==
        ~X"""
        <LiveForm>
          <Column>
            <TextField isError="false" id="user_email" style="  " name="user[email]" value="test@example.com">
              <Text template="label">Email</Text>
            </TextField>
          </Column>
        </LiveForm>
        """
    end
  end

  describe "button/1" do
    test "will render a button" do
      assigns = %{}

      template = ~LVN"""
      <.button>Send!</.button>
      """

      assert t2h(template) ==
        ~X"""
        <Button>
          Send!
        </Button>
        """

    end
  end

  describe "table" do

  end

  describe "list/1" do
    test "will render a list" do
      assigns = %{}

      template = ~LVN"""
      <.list>
        <:item :for={item <- [%{title: "Foo"}, %{title: "Bar"}]} title={item.title}>
          <Text><%= item.title %></Text>
        </:item>
      </.list>
      """

      assert t2h(template) ==
        ~X"""
        <LazyColumn>
          <ListItem>
            <Text template="headlineContent">Foo</Text>
            <Box template="trailingContent" style="wrapContentWidth()">
              <Text>Foo</Text>
            </Box>
          </ListItem>
          <ListItem>
            <Text template="headlineContent">Bar</Text>
            <Box template="trailingContent" style="wrapContentWidth()">
              <Text>Bar</Text>
            </Box>
          </ListItem>
        </LazyColumn>
        """
    end
  end

  describe "icon/1" do
    test "renders an image tag as a system icon" do
      assigns = %{}

      template = ~LVN"""
        <.icon name="star"/>
        """

      assert t2h(template) ==
        ~X"""
        <Icon imageVector="star"/>
        """
    end
  end

  describe "image/1" do

  end

  describe "translate_error/1" do

  end

  describe "translate_errors/1" do

  end
end
