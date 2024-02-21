package org.phoenixframework.liveview.data.mappers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import org.antlr.v4.runtime.CharStream
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTreeWalker
import org.phoenixframework.liveview.data.constants.Attrs.attrBackground
import org.phoenixframework.liveview.data.constants.Attrs.attrHeight
import org.phoenixframework.liveview.domain.extensions.toColor
import org.phoenixframework.liveview.stylesheet.StylesheetLexer
import org.phoenixframework.liveview.stylesheet.StylesheetParser
import org.phoenixframework.liveview.stylesheet.StylesheetParserBaseListener


fun Modifier.fromStyle(string: String): Modifier {
    val charStream: CharStream = CharStreams.fromString(string)
    val stylesheetLexer = StylesheetLexer(charStream)
    val commonTokenStream = CommonTokenStream(stylesheetLexer)
    val stylesheetParser = StylesheetParser(commonTokenStream)

    val styleContext = stylesheetParser.style()
    val listener: StylesheetParserBaseListener = object : StylesheetParserBaseListener() {}
    ParseTreeWalker.DEFAULT.walk(listener, styleContext)

    var result: Modifier = Modifier
    styleContext.modifier().forEach { modifierContext ->
        handlerModifier(modifierContext).let { modifier ->
            result = result.then(modifier)
        }
    }

    return this.then(result)
}

private fun Modifier.handlerModifier(ctx: StylesheetParser.ModifierContext): Modifier {
    return when (ctx.ID()?.toString()) {
        attrHeight -> {
            val height = ctx.arg().first().NUMBER().toString().toInt()
            this.then(Modifier.height(height.dp))
        }

        "fillMaxWidth" -> {
            this.then(Modifier.fillMaxWidth())
        }

        attrBackground -> {
            var color = Color.Unspecified
            var shape = RectangleShape

            ctx.arg()?.forEach { argCtx ->
                val clazz =
                    argCtx?.modifier()?.ID()?.toString() ?: argCtx?.modifier()?.DOT().toString()
                if (clazz == "Color" || clazz == ".") {
                    val colorStr = argCtx.modifier().arg(1).member()?.ID().toString()
                    color =
                        if (colorStr.startsWith("#")) colorStr.toColor() else "system-$colorStr".toColor()
                }
                if (clazz == "Circle") {
                    shape = CircleShape
                }
                if (clazz == "RoundedCornerShape") {
                    if (argCtx.modifier().arg().size == 1) {
                        shape = RoundedCornerShape(
                            argCtx.modifier().arg(0).NUMBER().toString().toInt()
                        )
                    } else if (argCtx.modifier().arg().size == 4) {
                        shape = RoundedCornerShape(
                            argCtx.modifier().arg(0).NUMBER().toString().toInt().dp,
                            argCtx.modifier().arg(1).NUMBER().toString().toInt().dp,
                            argCtx.modifier().arg(2).NUMBER().toString().toInt().dp,
                            argCtx.modifier().arg(3).NUMBER().toString().toInt().dp,
                        )
                    }
                }
            }
            this.then(Modifier.background(color, shape))
        }

        else -> this
    }
}