package org.phoenixframework.liveview.constants

object AlignmentLineValues {
    const val firstBaseline = "FirstBaseline"
    const val lastBaseline = "LastBaseline"
}

object AlignmentValues {
    const val topStart = "TopStart"
    const val topCenter = "TopCenter"
    const val topEnd = "TopEnd"
    const val centerStart = "CenterStart"
    const val center = "Center"
    const val centerEnd = "CenterEnd"
    const val bottomStart = "BottomStart"
    const val bottomCenter = "BottomCenter"
    const val bottomEnd = "BottomEnd"
}

object BaselineShiftValues {
    const val subscript = "Subscript"
    const val superscript = "Superscript"
    const val none = "None"
}

object BrushFunctions {
    const val horizontalGradient = "horizontalGradient"
    const val verticalGradient = "verticalGradient"
    const val linearGradient = "linearGradient"
    const val radialGradient = "radialGradient"
    const val sweepGradient = "sweepGradient"
}

object ContentScaleValues {
    const val crop = "Crop"
    const val fillBounds = "FillBounds"
    const val fillHeight = "FillHeight"
    const val fillWidth = "FillWidth"
    const val fit = "Fit"
    const val inside = "Inside"
    const val none = "None"
}

object DatePickerDisplayModeValues {
    const val input = "Input"
    const val picker = "Picker"
}

object DrawStyleValues {
    const val fill = "Fill"
}

object EnterExitTransitionFunctions {
    const val expandHorizontally = "expandHorizontally"
    const val expandIn = "expandIn"
    const val expandVertically = "expandVertically"
    const val fadeIn = "fadeIn"
    const val fadeOut = "fadeOut"
    const val scaleIn = "scaleIn"
    const val scaleOut = "scaleOut"
    const val slideOut = "slideOut"
    const val slideOutHorizontally = "slideOutHorizontally"
    const val slideOutVertically = "slideOutVertically"
    const val shrinkHorizontally = "shrinkHorizontally"
    const val shrinkOut = "shrinkOut"
    const val shrinkVertically = "shrinkVertically"
    const val slideIn = "slideIn"
    const val slideInVertically = "slideInVertically"
    const val slideInHorizontally = "slideInHorizontally"

    // Argument names used for the functions above
    const val argAnimationSpec = "animationSpec"
    const val argClip = "clip"
    const val argExpandFrom = "expandFrom"
    const val argHeight = "height"
    const val argInitialAlpha = "initialAlpha"
    const val argInitialHeight = "initialHeight"
    const val argInitialOffset = "initialOffset"
    const val argInitialOffsetX = "initialOffsetX"
    const val argInitialOffsetY = "initialOffsetY"
    const val argInitialScale = "initialScale"
    const val argInitialSize = "initialSize"
    const val argInitialWidth = "initialWidth"
    const val argShrinkTowards = "shrinkTowards"
    const val argTargetAlpha = "targetAlpha"
    const val argTargetHeight = "targetHeight"
    const val argTargetOffset = "targetOffset"
    const val argTargetOffsetX = "targetOffsetX"
    const val argTargetOffsetY = "targetOffsetY"
    const val argTargetScale = "targetScale"
    const val argTargetSize = "targetSize"
    const val argTargetWidth = "targetWidth"
    const val argTransformOrigin = "transformOrigin"
    const val argWidth = "width"
    const val argX = "x"
    const val argY = "y"
}

object EasingValues {
    const val ease = "Ease"
    const val easeOut = "EaseOut"
    const val easeIn = "EaseIn"
    const val easeInOut = "EaseInOut"
    const val easeInSine = "EaseInSine"
    const val easeOutSine = "EaseOutSine"
    const val easeInOutSine = "EaseInOutSine"
    const val easeInCubic = "EaseInCubic"
    const val easeOutCubic = "EaseOutCubic"
    const val easeInOutCubic = "EaseInOutCubic"
    const val easeInQuint = "EaseInQuint"
    const val easeOutQuint = "EaseOutQuint"
    const val easeInOutQuint = "EaseInOutQuint"
    const val easeInCirc = "EaseInCirc"
    const val easeOutCirc = "EaseOutCirc"
    const val easeInOutCirc = "EaseInOutCirc"
    const val easeInQuad = "EaseInQuad"
    const val easeOutQuad = "EaseOutQuad"
    const val easeInOutQuad = "EaseInOutQuad"
    const val easeInQuart = "EaseInQuart"
    const val easeOutQuart = "EaseOutQuart"
    const val easeInOutQuart = "EaseInOutQuart"
    const val easeInExpo = "EaseInExpo"
    const val easeOutExpo = "EaseOutExpo"
    const val easeInOutExpo = "EaseInOutExpo"
    const val easeInBack = "EaseInBack"
    const val easeOutBack = "EaseOutBack"
    const val easeInOutBack = "EaseInOutBack"
    const val easeInElastic = "EaseInElastic"
    const val easeOutElastic = "EaseOutElastic"
    const val easeInOutElastic = "EaseInOutElastic"
    const val easeOutBounce = "EaseOutBounce"
    const val easeInBounce = "EaseInBounce"
    const val easeInOutBounce = "EaseInOutBounce"
}

object FabPositionValues {
    const val start = "Start"
    const val end = "End"
    const val center = "Center"
    const val endOverlay = "EndOverlay"
}

object FiniteAnimationSpecFunctions {
    const val tween = "tween"
    const val spring = "spring"
    const val keyframes = "keyframes"
    const val keyframesWithSpline = "keyframesWithSpline"
    const val repeatable = "repeatable"
    const val snap = "snap"

    const val argAnimation = "animation"
    const val argDampingRatio = "dampingRatio"
    const val argDelayMillis = "delayMillis"
    const val argDurationMillis = "durationMillis"
    const val argEasing = "easing"
    const val argIterations = "iterations"
    const val argRepeatMode = "repeatMode"
    const val argInitialStartOffset = "initialStartOffset"
    const val argStiffness = "stiffness"
    const val argValue = "value"
}

object FontStyleValues {
    const val italic = "Italic"
    const val normal = "Normal"
}

object FontSynthesisValues {
    const val all = "All"
    const val none = "None"
    const val style = "Style"
    const val weight = "Weight"
}

object FontWeightValues {
    const val thin = "thin"
    const val W100 = "W100"
    const val extraLight = "extraLight"
    const val W200 = "W200"
    const val light = "light"
    const val W300 = "W300"
    const val normal = "normal"
    const val W400 = "W400"
    const val medium = "medium"
    const val W500 = "W500"
    const val semiBold = "semiBold"
    const val W600 = "W600"
    const val bold = "bold"
    const val W700 = "W700"
    const val extraBold = "extraBold"
    const val W800 = "W800"
    const val black = "black"
    const val W900 = "W900"
}

object HorizontalAlignmentValues {
    const val start = "Start"
    const val centerHorizontally = "CenterHorizontally"
    const val end = "End"
}

object HorizontalArrangementValues {
    const val spaceEvenly = "SpaceEvenly"
    const val spaceAround = "SpaceAround"
    const val spaceBetween = "SpaceBetween"
    const val start = "Start"
    const val end = "End"
    const val center = "Center"
}

object HyphensValues {
    const val none = "None"
    const val auto = "Auto"
    const val unspecified = "Unspecified"
}

object IconPrefixValues {
    private const val autoMirrored = "AutoMirrored"
    const val filled = "Filled"
    const val rounded = "Rounded"
    const val outlined = "Outlined"
    const val sharp = "Sharp"
    const val twoTone = "TwoTone"
    const val autoMirroredFilled = "$autoMirrored.$filled"
    const val autoMirroredRounded = "$autoMirrored.$rounded"
    const val autoMirroredOutlined = "$autoMirrored.$outlined"
    const val autoMirroredSharp = "$autoMirrored.$sharp"
    const val autoMirroredTwoTone = "$autoMirrored.$twoTone"
}

object ImeActionValues {
    const val none = "None"
    const val go = "Go"
    const val search = "Search"
    const val send = "Send"
    const val previous = "Previous"
    const val next = "Next"
    const val done = "Done"
}

object KeyboardCapitalizationValues {
    const val characters = "Characters"
    const val words = "Words"
    const val sentences = "Sentences"
}

object KeyboardTypeValues {
    const val ascii = "Ascii"
    const val number = "Number"
    const val phone = "Phone"
    const val uri = "Uri"
    const val email = "Email"
    const val password = "Password"
    const val numberPassword = "NumberPassword"
    const val decimal = "Decimal"
    const val text = "Text"
}

object LazyGridColumnTypeValues {
    const val fixed = "Fixed"
    const val adaptive = "Adaptive"
    const val fixedSize = "FixedSize"
}

object LineHeightStyleAlignmentValues {
    const val center = "Center"
    const val top = "Top"
    const val bottom = "Bottom"
    const val proportional = "Proportional"
}

object LineHeightStyleTrimValues {
    const val both = "Both"
    const val lastLineBottom = "LastLineBottom"
    const val firstLineTop = "FirstLineTop"
    const val none = "None"
}

object LineBreakValues {
    const val simple = "Simple"
    const val paragraph = "Paragraph"
    const val heading = "Heading"
}

object OffsetValues {
    const val zero = "Zero"
    const val infinite = "Infinite"
    const val unspecified = "Unspecified"
}

object RepeatModeValues {
    const val restart = "Restart"
    const val reverse = "Reverse"
}

object RoleValues {
    const val button = "Button"
    const val checkbox = "Checkbox"
    const val switch = "Switch"
    const val radioButton = "RadioButton"
    const val tab = "Tab"
    const val image = "Image"
    const val dropdownList = "DropdownList"
}

object ScrollBehaviorValues {
    const val exitUntilCollapsed = "exitUntilCollapsed"
    const val enterAlways = "enterAlways"
    const val pinnedScroll = "pinnedScroll"
}

object SecureFlagPolicyValues {
    const val secureOn = "SecureOn"
    const val secureOff = "SecureOff"
    const val inherit = "Inherit"
}

object ShapeValues {
    const val circle = "CircleShape"
    const val rectangle = "RectangleShape"
    const val roundedCorner = "RoundedCornerShape"
}

object SheetValues {
    const val expanded = "Expanded"
    const val partiallyExpanded = "PartiallyExpanded"
    const val hidden = "Hidden"
}

object SnackbarDurationValues {
    const val indefinite = "Indefinite"
    const val long = "Long"
    const val short = "Short"
}

object StartOffsetTypeValues {
    const val delay = "Delay"
    const val fastForward = "FastForward"
}

object StrokeCapValues {
    const val round = "Round"
    const val square = "Square"
    const val butt = "Butt"
}

object StrokeJoinValues {
    const val miter = "Miter"
    const val round = "Round"
    const val bevel = "Bevel"
}

object SwipeToDismissBoxValues {
    const val startToEnd = "StartToEnd"
    const val endToStart = "EndToStart"
    const val settled = "Settled"
}

object SystemColorValues {
    const val Black = "Black"
    const val Blue = "Blue"
    const val Cyan = "Cyan"
    const val DarkGray = "DarkGray"
    const val Gray = "Gray"
    const val Green = "Green"
    const val LightGray = "LightGray"
    const val Magenta = "Magenta"
    const val Red = "Red"
    const val Transparent = "Transparent"
    const val White = "White"
    const val Yellow = "Yellow"
}

object TextAlignValues {
    const val left = "Left"
    const val right = "Right"
    const val center = "Center"
    const val justify = "Justify"
    const val start = "Start"
    const val end = "End"
}

object TextDecorationValues {
    const val underline = "Underline"
    const val lineThrough = "LineThrough"
    const val none = "None"
}

object TextDirectionValues {
    const val ltr = "Ltr"
    const val rtl = "Rtl"
    const val content = "Content"
    const val contentOrRtl = "ContentOrRtl"
    const val contentOrLtr = "ContentOrLtr"
}

object TextMotionValues {
    const val static = "Static"
    const val animated = "Animated"
}

object TextOverflowValues {
    const val clip = "Clip"
    const val ellipsis = "Ellipsis"
    const val visible = "Visible"
}

object ThemeTextStyleValues {
    const val displayLarge = "displayLarge"
    const val displayMedium = "displayMedium"
    const val displaySmall = "displaySmall"
    const val headlineLarge = "headlineLarge"
    const val headlineMedium = "headlineMedium"
    const val headlineSmall = "headlineSmall"
    const val titleLarge = "titleLarge"
    const val titleMedium = "titleMedium"
    const val titleSmall = "titleSmall"
    const val bodyLarge = "bodyLarge"
    const val bodyMedium = "bodyMedium"
    const val bodySmall = "bodySmall"
    const val labelLarge = "labelLarge"
    const val labelMedium = "labelMedium"
    const val labelSmall = "labelSmall"
}

object ThemeColorsValues {
    const val primary = "primary"
    const val onPrimary = "onPrimary"
    const val primaryContainer = "primaryContainer"
    const val onPrimaryContainer = "onPrimaryContainer"
    const val inversePrimary = "inversePrimary"
    const val secondary = "secondary"
    const val onSecondary = "onSecondary"
    const val secondaryContainer = "secondaryContainer"
    const val onSecondaryContainer = "onSecondaryContainer"
    const val tertiary = "tertiary"
    const val onTertiary = "onTertiary"
    const val tertiaryContainer = "tertiaryContainer"
    const val onTertiaryContainer = "onTertiaryContainer"
    const val background = "background"
    const val onBackground = "onBackground"
    const val surface = "surface"
    const val onSurface = "onSurface"
    const val surfaceVariant = "surfaceVariant"
    const val onSurfaceVariant = "onSurfaceVariant"
    const val surfaceTint = "surfaceTint"
    const val inverseSurface = "inverseSurface"
    const val inverseOnSurface = "inverseOnSurface"
    const val error = "error"
    const val onError = "onError"
    const val errorContainer = "errorContainer"
    const val onErrorContainer = "onErrorContainer"
    const val outline = "outline"
    const val outlineVariant = "outlineVariant"
    const val scrim = "scrim"
    const val surfaceBright = "surfaceBright"
    const val surfaceContainer = "surfaceContainer"
    const val surfaceContainerHigh = "surfaceContainerHigh"
    const val surfaceContainerHighest = "surfaceContainerHighest"
    const val surfaceContainerLow = "surfaceContainerLow"
    const val surfaceContainerLowest = "surfaceContainerLowest"
    const val surfaceDim = "surfaceDim"
}

object ThemeShapesValues {
    const val extraSmall = "extraSmall"
    const val small = "small"
    const val medium = "medium"
    const val large = "large"
    const val extraLarge = "extraLarge"
}

object TileModeValues {
    const val clamp = "Clamp"
    const val decal = "Decal"
    const val mirror = "Mirror"
    const val repeated = "Repeated"
}

object TimePickerLayoutTypeValues {
    const val horizontal = "Horizontal"
    const val vertical = "Vertical"
}

object TransformOriginValues {
    const val center = "Center"
}

object VerticalAlignmentValues {
    const val top = "Top"
    const val centerVertically = "CenterVertically"
    const val bottom = "Bottom"
}

object VerticalArrangementValues {
    const val top = "Top"
    const val spaceEvenly = "SpaceEvenly"
    const val spaceAround = "SpaceAround"
    const val spaceBetween = "SpaceBetween"
    const val bottom = "Bottom"
    const val center = "Center"
}

object VisualTransformationValues {
    const val password = "password"
}