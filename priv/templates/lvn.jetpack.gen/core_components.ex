defmodule <%= inspect context.web_module %>.CoreComponents.<%= inspect context.module_suffix %> do
  @moduledoc """
  Provides core UI components built for Jetpack.<%= if not @live_form? do %>
  > #### No LiveForm Installed! {: .warning}
  >
  > You will not get access to any of the form related inputs without LiveForm. After it is installed regenerate
  > this file with `mix lvn.jetpack.gen`<% end %>

  This file contains feature parity components to your applications's CoreComponent module.
  The goal is to retain a common API for fast prototyping. Leveraging your existing knowledge
  of the `FormDemoWeb.CoreComponents` functions you should expect identical functionality for similarly named
  components between web and native. That means utilizing your existing `handle_event/3` functions to manage state
  and stay focused on adding new templates for your native applications.

  Icons are referenced by a system name. Read more about the [Xcode Asset Manager](https://developer.apple.com/documentation/xcode/asset-management)
  to learn how to include different assets in your LiveView Native applications. In addition, you can also use [SF Symbols](https://developer.apple.com/sf-symbols/).
  On any MacOS open Spotlight and search `SF Symbols`. The catalog application will provide a reference name that can be used. All SF Symbols
  are incuded with all Jetpack applications.

  Most of this documentation was "borrowed" from the analog Phoenix generated file to ensure this project is expressing the same behavior.
  """

  use LiveViewNative.Component<%= if @live_form? do %>

  import LiveViewNative.LiveForm.Component<%= if @test? do %>
  @external_resource "priv/templates/lvn.jetpack.gen/core_components.ex"<% end %>

  @doc """
  Renders an input with label and error messages.

  A `Phoenix.HTML.FormField` may be passed as argument,
  which is used to retrieve the input name, id, and values.
  Otherwise all attributes may be passed explicitly.

  ## Types

  This function accepts all Jetpack input types, considering that:

    * You may also set `type="Picker"` to render a `<Picker>` tag

    * `type="Toggle"` is used exclusively to render boolean values

  ## Examples

      <Group style="keyboardType(.emailAddress)">
        <.input field={@form[:email]} type="TextField" />
        <.input name="my-input" errors={["oh no!"]} />
      </Group>

  [INSERT LVATTRDOCS]
  """
  @doc type: :component

  attr :id, :any, default: nil
  attr :name, :any
  attr :label, :string, default: nil
  attr :value, :any

  attr :type, :string,
    default: "TextField",
    values: ~w(Checkbox DatePicker MultiDatePicker Picker SecureField Slider TextField hidden SingleChoiceSegmentedButtonRow)

  attr :field, Phoenix.HTML.FormField,
    doc: "a form field struct retrieved from the form, for example: `@form[:email]`"

  attr :errors, :list, default: []
  attr :checked, :boolean, doc: "the checked flag for checkbox inputs"
  attr :prompt, :string, default: nil, doc: "the prompt for select inputs"
  attr :options, :list, doc: "the options to pass to `Phoenix.HTML.Form.options_for_select/2`"
  attr :multiple, :boolean, default: false, doc: "the multiple flag for select inputs"

  attr :min, :any, default: nil
  attr :max, :any, default: nil

  attr :placeholder, :string, default: nil

  attr :readonly, :boolean, default: false

  attr :autocomplete, :string,
    default: "on",
    values: ~w(on off)

  attr :rest, :global,
    include: ~w(disabled step)

  slot :inner_block

  def input(%{field: %Phoenix.HTML.FormField{} = field} = assigns) do
    #dbg assigns
    assigns
    |> assign(field: nil, id: assigns.id || field.id)
    |> assign(:errors, Enum.map(field.errors, &translate_error(&1)))
    |> assign_new(:name, fn -> if assigns.multiple, do: field.name <> "[]", else: field.name end)
    |> assign_new(:value, fn -> field.value end)
    |> assign(
      :rest,
      Map.put(assigns.rest, :style, [
        Map.get(assigns.rest, :style, ""),
        (if assigns.readonly or Map.get(assigns.rest, :disabled, false), do: "disabled(true)", else: ""),
        (if assigns.autocomplete == "off", do: "textInputAutocapitalization(.never) autocorrectionDisabled()", else: "")
      ] |> Enum.join(" "))
    )
    |> input()
  end

  def input(%{type: "hidden"} = assigns) do
    ~LVN"""
    <Spacer id={@id} name={@name} value={@value} {@rest} />
    """
  end

  def input(%{type: "DatePicker"} = assigns) do
    ~LVN"""
    <Column>
      <DatePicker id={@id} name={@name} initialSelectedDateMillis={@value} {@rest} >
        <Text :if={@label} template="title"><%%= @label %></Text>
      </DatePicker>
      <.error :for={msg <- @errors}><%%= msg %></.error>
    </Column>
    """
  end

  def input(%{type: "MultiDatePicker"} = assigns) do
    [start_date, end_date] = Jason.decode!(assigns.value)

    assigns =
      assigns
      |> assign(:start_date, start_date)
      |> assign(:end_date, end_date)

    ~LVN"""
    <Column>
      <DateRangePicker id={@id} name={@name} initialSelectedStartDateMillis={@start_date} initialSelectedEndDateMillis={@end_date}  {@rest} >
        <Text :if={@label} template="title"><%%= @label %></Text>
      </DateRangePicker>
      <.error :for={msg <- @errors}><%%= msg %></.error>
    </Column>
    """
  end

  def input(%{type: "Picker"} = assigns) do
    ~LVN"""
    <Column>
      <ExposedDropdownMenuBox id={@id} name={@name} {@rest} value={@value}>
        <TextField value={List.keyfind(@options, @value, 1, {"", ""}) |> elem(0)} readOnly="true" style="menuAnchor()" >
          <Text :if={@label} template="label"><%%= @label %></Text>
          <Text :if={@placeholder} template="placeholder"><%%= @placeholder %></Text>
        </TextField>
        <ExposedDropdownMenu>
          <DropdownMenuItem :for={{name, value} <- @options} phx-value={value}>
            <Text><%%= name %></Text>
          </DropdownMenuItem>
        </ExposedDropdownMenu>
      </ExposedDropdownMenuBox>
      <.error :for={msg <- @errors}><%%= msg %></.error>
    </Column>
    """
  end

  def input(%{type: "SingleChoiceSegmentedButtonRow"} = assigns) do
    ~LVN"""
    <Column>
      <Text :if={@label} template="label"><%%= @label %></Text>
      <SingleChoiceSegmentedButtonRow id={@id} name={@name} {@rest} value={@value}>
        <SegmentedButton :for={{name, value} <- @options} selected={value == @value} phx-value={value}>
          <Text template="label"><%%= name %></Text>
        </SegmentedButton>
      </SingleChoiceSegmentedButtonRow>
      <.error :for={msg <- @errors}><%%= msg %></.error>
    </Column>
    """
  end


  def input(%{type: "Slider"} = assigns) do
    ~LVN"""
    <Column>
      <Text :if={@label}><%%= @label %></Text>
      <Slider id={@id} name={@name} value={@value} minValue={@min} maxValue={@max} {@rest} />
      <.error :for={msg <- @errors}><%%= msg %></.error>
    </Column>
    """
  end

  def input(%{type: "TextField"} = assigns) do
    ~LVN"""
    <Column>
      <TextField id={@id} name={@name} value={@value} isError={not Enum.empty?(@errors)} {@rest}>
        <Text :if={@label} template="label"><%%= @label %></Text>
        <Text :if={@placeholder} template="placeholder"><%%= @placeholder %></Text>
      </TextField>
      <.error :for={msg <- @errors}><%%= msg %></.error>
    </Column>
    """
  end

  def input(%{type: "SecureField"} = assigns) do
    ~LVN"""
    <Column>
      <TextField id={@id} name={@name} value={@value} prompt={@prompt} isError={not Enum.empty?(@errors)} visualTransformation="password" {@rest}>
        <Text :if={@label} template="label"><%%= @label %></Text>
        <Text :if={@placeholder} template="placeholder"><%%= @placeholder %></Text>
      </TextField>
      <.error :for={msg <- @errors}><%%= msg %></.error>
    </Column>
    """
  end

  def input(%{type: "Checkbox"} = assigns) do
    ~LVN"""
    <Column>
      <Row verticalAlignment="CenterVertically" style="wrapContentWidth()">
        <CheckBox id={@id} name={@name} checked={Map.get(assigns, :checked, Map.get(assigns, :value))} {@rest} />
        <Text><%%= @label %></Text>
      </Row>
      <.error :for={msg <- @errors}><%%= msg %></.error>
    </Column>
    """
  end

  @doc """
  Generates a generic error message.
  """
  @doc type: :component
  slot :inner_block, required: true

  def error(assigns) do
    ~LVN"""
    <Text textStyle="bodyMedium" color="error">
      <%%= render_slot(@inner_block) %>
    </Text>
    """
  end<% end %>

  @doc """
  Renders a header with title.

  [INSERT LVATTRDOCS]
  """
  @doc type: :component

  slot :inner_block, required: true
  slot :subtitle
  slot :actions

  def header(assigns) do
    ~LVN"""
    <TopAppBar>
      <Column template="title">
        <Text>
          <%%= render_slot(@inner_block) %>
        </Text>
        <Text :if={@subtitle != []} template="subtitle" textStyle="bodyMedium">
          <%%= render_slot(@subtitle) %>
        </Text>
      </Column>
      <Row template="action">
        <%%= render_slot(@actions) %>
      </Row>
    </TopAppBar>
    """
  end

  @doc """
  Renders a modal.

  ## Examples

      <.modal show={@show} id="confirm-modal">
        This is a modal.
      </.modal>

  An event name may be passed to the `:on_cancel` to configure
  the closing/cancel event, for example:

      <.modal show={@show} id="confirm" on_cancel="toggle-show">
        This is another modal.
      </.modal>

  """
  attr :id, :string, required: true
  attr :show, :boolean, default: false
  attr :on_cancel, :string, default: nil
  slot :inner_block, required: true

  def modal(assigns) do
    ~LVN"""
    <%%= if @show do %>
      <ModalBottomSheet
        id={@id}
        onDismissRequest={@on_cancel}
      >
        <%%= render_slot(@inner_block) %>
      </ModalBottomSheet>
    <%% end %>
    """
  end

  @doc """
  Renders flash notices.

  ## Examples

      <.flash kind={:info} flash={@flash} />
      <.flash kind={:info} phx-mounted={show("#flash")}>Welcome Back!</.flash>
  """
  attr :id, :string, doc: "the optional id of flash container"
  attr :flash, :map, default: %{}, doc: "the map of flash messages to display"
  attr :title, :string, default: nil
  attr :kind, :atom, values: [:info, :error], doc: "used for styling and flash lookup"
  attr :rest, :global, doc: "the arbitrary attributes to add to the flash container"

  slot :inner_block, doc: "the optional inner block that renders the flash message"

  def flash(assigns) do
    assigns = assign_new(assigns, :id, fn -> "flash-#{assigns.kind}" end)

    ~LVN"""
    <%% msg = render_slot(@inner_block) || Phoenix.Flash.get(@flash, @kind) %>
    <AlertDialog :if={msg != nil} onDismissRequest="lv:clear-flash" {@rest}>
      <Text template="title"><%%= @title %></Text>
      <Text><%%= msg %></Text>
      <Button template="confirm" phx-click="lv:clear-flash" phx-value-key={@kind}>OK</Button>
    </AlertDialog>
    """
  end

  @doc """
  Shows the flash group with standard titles and content.

  ## Examples

      <.flash_group flash={@flash} />
  """
  attr :flash, :map, required: true, doc: "the map of flash messages"
  attr :id, :string, default: "flash-group", doc: "the optional id of flash container"

  def flash_group(assigns) do
    ~LVN"""
    <Box id={@id}>
      <.flash kind={:info} title={"Success!"} flash={@flash} />
      <.flash kind={:error} title={"Error!"} flash={@flash} />
    </Box>
    """
  end<%= if @live_form? do %>

  @doc """
  Renders a simple form.

  ## Examples

      <.simple_form for={@form} phx-change="validate" phx-submit="save">
        <.input field={@form[:email]} label="Email"/>
        <.input field={@form[:username]} label="Username" />
        <:actions>
          <.button type="submit">Save</.button>
        </:actions>
      </.simple_form>

  [INSERT LVATTRDOCS]
  """
  @doc type: :component

  attr :for, :any, required: true, doc: "the datastructure for the form"
  attr :as, :any, default: nil, doc: "the server side parameter to collect all input under"

  attr :rest, :global,
    include: ~w(autocomplete name rel action enctype method novalidate target multipart),
    doc: "the arbitrary attributes to apply to the form tag"

  slot :inner_block, required: true
  slot :actions, doc: "the slot for form actions, such as a submit button"

  def simple_form(assigns) do
    ~LVN"""
    <.form :let={f} for={@for} as={@as} {@rest}>
      <%%= render_slot(@inner_block, f) %>

      <%%= for action <- @actions do %>
        <%%= render_slot(action, f) %>
      <%% end %>
    </.form>
    """
  end

  @doc """
  Renders a button.

  ## Examples

      <.button type="submit">Send!</.button>
      <.button phx-click="go">Send!</.button>
  """
  @doc type: :component

  attr :type, :string, default: nil
  attr :rest, :global

  slot :inner_block, required: true

  def button(%{ type: "submit" } = assigns) do
    ~LVN"""
    <LiveSubmitButton {@rest}>
      <%%= render_slot(@inner_block) %>
    </LiveSubmitButton>
    """
  end

  def button(assigns) do
    ~LVN"""
    <Button {@rest}>
      <%%= render_slot(@inner_block) %>
    </Button>
    """
  end<% end %>

  @doc """
  Renders a data list.

  ## Examples

      <.list>
        <:item title="Title"><%%= @post.title %></:item>
        <:item title="Views"><%%= @post.views %></:item>
      </.list>
  """
  attr :rest, :global

  slot :item, required: true do
    attr :title, :string, required: true
  end

  def list(assigns) do
    ~LVN"""
    <LazyColumn {@rest}>
      <ListItem :for={item <- @item}>
        <Text template="headlineContent"><%%= item.title %></Text>
        <Box style="wrapContentWidth()" template="trailingContent"><%%= render_slot(item) %></Box>
      </ListItem>
    </LazyColumn>
    """
  end

  @doc """
  Renders a system image from the Asset Manager in Xcode
  or from SF Symbols.

  ## Examples

      <.icon name="xmark.diamond" />
  """
  @doc type: :component

  attr :name, :string, required: true
  attr :rest, :global

  def icon(assigns) do
    ~LVN"""
    <Icon imageVector={@name} {@rest} />
    """
  end

  @doc """
  Renders an image from a url

  Will render an [`AsyncImage`](https://developer.apple.com/documentation/jetpack/asyncimage)
  You can customize the lifecycle states of with the slots.
  """

  attr :url, :string, required: true
  attr :rest, :global
  slot :empty, doc: """
    The empty state that will render before has successfully been downloaded.

        <.image url={~p"/assets/images/logo.png"}>
          <:empty>
            <Image systemName="myloading.spinner" />
          </:empty>
        </.image>

    [See Jetpack docs](https://developer.apple.com/documentation/jetpack/asyncimagephase/success(_:))
    """
  slot :success, doc: """
    The success state that will render when the image has successfully been downloaded.

        <.image url={~p"/assets/images/logo.png"}>
          <:success class="main-logo"/>
        </.image>

    [See Jetpack docs](https://developer.apple.com/documentation/jetpack/asyncimagephase/success(_:))
    """
  do
    attr :class, :string
    attr :style, :string
  end
  slot :failure, doc: """
    The failure state that will render when the image fails to downloaded.

        <.image url={~p"/assets/images/logo.png"}>
          <:failure class="image-fail"/>
        </.image>

    [See Jetpack docs](https://developer.apple.com/documentation/jetpack/asyncimagephase/failure(_:))

  """
  do
    attr :class, :string
    attr :style, :string
  end

  def image(assigns) do
    ~LVN"""
    <AsyncImage url={@url} {@rest}>
      <Box template="loading" :if={@empty != []}>
        <%%= render_slot(@empty) %>
      </Box>
      <.image_success slot={@success} />
      <.image_failure slot={@failure} />
    </AsyncImage>
    """
  end

  defp image_success(%{ slot: [%{ inner_block: nil }] } = assigns) do
    ~LVN"""
    <AsyncImage image :for={slot <- @slot} class={Map.get(slot, :class)} {%{ style: Map.get(slot, :style) }} />
    """
  end

  defp image_success(assigns) do
    ~LVN"""
    <Box :if={@slot != []}>
      <%%= render_slot(@slot) %>
    </Box>
    """
  end

  defp image_failure(%{ slot: [%{ inner_block: nil }] } = assigns) do
    ~LVN"""
    <AsyncImage error template="error" :for={slot <- @slot} class={Map.get(slot, :class)} {%{ style: Map.get(slot, :style) }} />
    """
  end

  defp image_failure(assigns) do
    ~LVN"""
    <Box template="error" :if={@slot != []}>
      <%%= render_slot(@slot) %>
    </Box>
    """
  end<%= if @live_form? do %>

  @doc """
  Translates an error message using gettext.
  """<%= if @gettext do %>
  def translate_error({msg, opts}) do
    # When using gettext, we typically pass the strings we want
    # to translate as a static argument:
    #
    #     # Translate the number of files with plural rules
    #     dngettext("errors", "1 file", "%{count} files", count)
    #
    # However the error messages in our forms and APIs are generated
    # dynamically, so we need to translate them by calling Gettext
    # with our gettext backend as first argument. Translations are
    # available in the errors.po file (as we use the "errors" domain).
    if count = opts[:count] do
      Gettext.dngettext(<%= inspect context.web_module %>.Gettext, "errors", msg, msg, count, opts)
    else
      Gettext.dgettext(<%= inspect context.web_module %>.Gettext, "errors", msg, opts)
    end
  end<% else %>
  def translate_error({msg, opts}) do
    # You can make use of gettext to translate error messages by
    # uncommenting and adjusting the following code:

    # if count = opts[:count] do
    #   Gettext.dngettext(<%%= inspect context.web_module %>.Gettext, "errors", msg, msg, count, opts)
    # else
    #   Gettext.dgettext(<%%= inspect context.web_module %>.Gettext, "errors", msg, opts)
    # end

    Enum.reduce(opts, msg, fn {key, value}, acc ->
      String.replace(acc, "%{#{key}}", fn _ -> to_string(value) end)
    end)
  end<% end %>

  @doc """
  Translates the errors for a field from a keyword list of errors.
  """
  def translate_errors(errors, field) when is_list(errors) do
    for {^field, {msg, opts}} <- errors, do: translate_error({msg, opts})
  end<% end %>
end
