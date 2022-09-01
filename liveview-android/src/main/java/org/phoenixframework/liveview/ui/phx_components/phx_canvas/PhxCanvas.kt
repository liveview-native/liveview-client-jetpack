package org.phoenixframework.liveview.ui.phx_components.phx_canvas

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.core.graphics.toColorInt
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

fun generateCanvasInstructionByTag(
    children: Elements,
): DrawScope.() -> Unit = {
    val canvasWidth = size.width
    val canvasHeight = size.height

    children.forEach { theElement ->
        val color = if (theElement.attr("color").isNullOrEmpty()) {
            Color.White
        } else {
            Color(theElement.attr("color").toColorInt())
        }

        when (theElement.tagName()) {
            "draw-circle" -> {
                val centerElement = theElement.getElementsByTag("center*").first()
                val centerOffset = PhxOffset(
                    element = centerElement?.children()?.first(),
                    canvasHeight = canvasHeight,
                    canvasWidth = canvasWidth
                )

                val radius = theElement.attr("radius").toFloatOrNull() ?: 0.toFloat()

                drawCircle(
                    color = color,
                    center = centerOffset,
                    radius = radius
                )
            }

            "draw-line" -> {
                val start = theElement.getElementsByTag("start*").first()
                val startOffset = PhxOffset(
                    element = start?.children()?.first(),
                    canvasWidth = canvasWidth,
                    canvasHeight = canvasHeight
                )

                val end = theElement.getElementsByTag("end*").first()
                val endOffset = PhxOffset(
                    element = end?.children()?.first(),
                    canvasWidth = canvasWidth,
                    canvasHeight = canvasHeight
                )

                drawLine(
                    color = color,
                    start = startOffset,
                    end = endOffset
                )
            }
        }
    }
}

fun PhxOffset(
    element: Element?,
    canvasWidth: Float,
    canvasHeight: Float
): Offset = if (element?.hasAttr("center") == true) {
    Offset(
        x = canvasWidth / 2,
        y = canvasHeight / 2
    )
} else {
    Offset(
        x = element?.attr("x")?.toFloatOrNull() ?: 0.toFloat(),
        y = element?.attr("y")?.toFloatOrNull() ?: 0.toFloat()
    )
}


@Composable
fun PhxCanvas(
    element: Element,
    modifier: Modifier,
) {
    Canvas(
        modifier = modifier,
        onDraw = generateCanvasInstructionByTag(element.children()),
    )
}

