package com.dockyard.lightpandarenderer.library

// option 1
import androidx.compose.ui.Modifier
//import kotlin.script.experimental.api.*
//import kotlin.script.experimental.host.toScriptSource
//import kotlin.script.experimental.jvm.dependenciesFromCurrentContext
//import kotlin.script.experimental.jvm.jvm
//import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost
//import kotlin.script.experimental.jvmhost.createJvmCompilationConfigurationFromTemplate
//
//class ModifierLibrary {
//    companion object {
//        private val cache = mutableMapOf<String, Modifier?>()
//
//        private val scriptingHost = BasicJvmScriptingHost()
//
//        fun parseModifier(style: String): Modifier? {
//            return cache.getOrPut(style) {
//                val script = """
//                    import androidx.compose.ui.Modifier
//                    import androidx.compose.ui.unit.*
//                    import androidx.compose.foundation.layout.*
//                    import androidx.compose.foundation.background
//                    import androidx.compose.ui.graphics.Color
//
//                    Modifier.$style
//                """.trimIndent()
//
//                val result = scriptingHost.eval(
//                    script.toScriptSource(),
//                    createJvmCompilationConfigurationFromTemplate<Any> {
//                        jvm {
//                            dependenciesFromCurrentContext(wholeClasspath = true)
//                        }
//                    },
//                    null
//                )
//
//                return when (result) {
//                    is ResultWithDiagnostics.Success -> result.value.returnValue.scriptInstance as? Modifier
//                    else -> null
//                }
//            }
//        }
//    }
//}

// option 2

//import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles
//import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
//import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer
//import org.jetbrains.kotlin.com.intellij.psi.PsiManager
//import org.jetbrains.kotlin.com.intellij.testFramework.LightVirtualFile
//import org.jetbrains.kotlin.config.CompilerConfiguration
//import org.jetbrains.kotlin.idea.KotlinFileType
//import org.jetbrains.kotlin.psi.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.background
//import androidx.compose.ui.graphics.Color
//
//class ModifierLibrary {
//    private val environment: KotlinCoreEnvironment
//
//    init {
//        val disposable = Disposer.newDisposable()
//        val configuration = CompilerConfiguration()
//        environment = KotlinCoreEnvironment.createForProduction(
//            disposable,
//            configuration,
//            EnvironmentConfigFiles.JVM_CONFIG_FILES
//        )
//    }
//
//    private val modifiers = mutableMapOf<String, (List<Any?>) -> Modifier>()
//
//    fun register(name: String, modifier: (List<Any?>) -> Modifier) {
//        modifiers[name] = modifier
//    }
//
//    fun parse(style: String): Modifier {
//        // Wrap in a complete expression context
//        val code = "val modifier = Modifier.$style"
//
//        val virtualFile = LightVirtualFile("modifier.kt", KotlinFileType.INSTANCE, code)
//        val psiFile = PsiManager.getInstance(environment.project)
//            .findFile(virtualFile) as? KtFile
//            ?: return Modifier
//
//        // Find the property initializer
//        val property = psiFile.declarations.firstOrNull() as? KtProperty
//        val initializer = property?.initializer as? KtDotQualifiedExpression
//            ?: return Modifier
//
//        return evaluateModifierChain(initializer)
//    }
//
//    private fun evaluateModifierChain(expr: KtExpression): Modifier {
//        var modifier: Modifier = Modifier
//
//        // Collect all chained calls
//        val calls = mutableListOf<KtCallExpression>()
//        collectCalls(expr, calls)
//
//        // Execute each call in order
//        for (call in calls) {
//            modifier = executeCall(modifier, call)
//        }
//
//        return modifier
//    }
//
//    private fun collectCalls(expr: KtExpression, calls: MutableList<KtCallExpression>) {
//        when (expr) {
//            is KtDotQualifiedExpression -> {
//                collectCalls(expr.receiverExpression, calls)
//                (expr.selectorExpression as? KtCallExpression)?.let { calls.add(it) }
//            }
//            is KtCallExpression -> calls.add(expr)
//            is KtNameReferenceExpression -> {
//                // Base "Modifier" reference, skip
//            }
//        }
//    }
//
//    private fun executeCall(modifier: Modifier, call: KtCallExpression): Modifier {
//        val functionName = call.calleeExpression?.text ?: return modifier
//        val args = call.valueArguments.map { parseArgument(it.getArgumentExpression()) }
//
//        return when (functionName) {
//            "padding" -> {
//                when (args.size) {
//                    1 -> modifier.padding(args[0] as? androidx.compose.ui.unit.Dp ?: 0.dp)
//                    4 -> modifier.padding(
//                        start = args[0] as? androidx.compose.ui.unit.Dp ?: 0.dp,
//                        top = args[1] as? androidx.compose.ui.unit.Dp ?: 0.dp,
//                        end = args[2] as? androidx.compose.ui.unit.Dp ?: 0.dp,
//                        bottom = args[3] as? androidx.compose.ui.unit.Dp ?: 0.dp
//                    )
//                    else -> modifier
//                }
//            }
//            "fillMaxWidth" -> modifier.fillMaxWidth(
//                args.firstOrNull() as? Float ?: 1f
//            )
//            "fillMaxHeight" -> modifier.fillMaxHeight(
//                args.firstOrNull() as? Float ?: 1f
//            )
//            "fillMaxSize" -> modifier.fillMaxSize(
//                args.firstOrNull() as? Float ?: 1f
//            )
//            "width" -> modifier.width(args[0] as? androidx.compose.ui.unit.Dp ?: 0.dp)
//            "height" -> modifier.height(args[0] as? androidx.compose.ui.unit.Dp ?: 0.dp)
//            "size" -> {
//                when (args.size) {
//                    1 -> modifier.size(args[0] as? androidx.compose.ui.unit.Dp ?: 0.dp)
//                    2 -> modifier.size(
//                        width = args[0] as? androidx.compose.ui.unit.Dp ?: 0.dp,
//                        height = args[1] as? androidx.compose.ui.unit.Dp ?: 0.dp
//                    )
//                    else -> modifier
//                }
//            }
//            "background" -> modifier.background(args[0] as? Color ?: Color.Transparent)
//            "border" -> modifier.border(
//                width = args[0] as? androidx.compose.ui.unit.Dp ?: 1.dp,
//                color = args[1] as? Color ?: Color.Black
//            )
//            else -> modifier
//        }
//    }
//
//    private fun parseArgument(expr: KtExpression?): Any? {
//        return when (expr) {
//            is KtConstantExpression -> {
//                // Parse literal values
//                when {
//                    expr.text.toIntOrNull() != null -> expr.text.toInt()
//                    expr.text.toFloatOrNull() != null -> expr.text.toFloat()
//                    else -> expr.text
//                }
//            }
//            is KtDotQualifiedExpression -> {
//                // Handle things like "16.dp" or "Color.Red"
//                val receiver = expr.receiverExpression.text
//                val selector = expr.selectorExpression?.text
//
//                when {
//                    selector == "dp" -> receiver.toIntOrNull()?.dp ?: 0.dp
//                    receiver == "Color" -> parseColor(selector)
//                    else -> null
//                }
//            }
//            is KtNameReferenceExpression -> {
//                // Handle enum-like values
//                parseEnumValue(expr.text)
//            }
//            else -> null
//        }
//    }
//
//    private fun parseColor(colorName: String?): Color {
//        return when (colorName) {
//            "Red" -> Color.Red
//            "Blue" -> Color.Blue
//            "Green" -> Color.Green
//            "Black" -> Color.Black
//            "White" -> Color.White
//            "Gray" -> Color.Gray
//            "Yellow" -> Color.Yellow
//            "Cyan" -> Color.Cyan
//            "Magenta" -> Color.Magenta
//            "Transparent" -> Color.Transparent
//            else -> Color.Transparent
//        }
//    }
//
//    private fun parseEnumValue(value: String): Any? {
//        // Handle enum values if needed
//        return null
//    }
//}