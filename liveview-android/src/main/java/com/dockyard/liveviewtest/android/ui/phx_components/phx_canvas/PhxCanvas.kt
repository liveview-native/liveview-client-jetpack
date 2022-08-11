package com.dockyard.liveviewtest.android.ui.phx_components.phx_canvas

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.core.graphics.toColorInt
import org.jsoup.nodes.Element
import org.jsoup.select.Elements


//fun walkChildrenAndBuildCanvasInstructions(children: Elements?): DrawScope.() -> Unit {
//    children?.forEach { theElement ->
//        mapElementToCanvasInstruction(element = theElement)
//    }
//}


fun generateCanvasInstructionByTag(children: Elements): DrawScope.() -> Unit {

    return {
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

                    val center = when (theElement.attr("center")) {

                        "origin" -> {
                            Offset(x = 0.toFloat(), y = 0.toFloat())
                        }

                        else -> {
                            val coordinates = theElement.attr("center")

                            if (coordinates.isNullOrEmpty()) {
                                Offset(x = canvasWidth / 2, y = canvasHeight / 2)
                            } else {

                                val coordList = coordinates.split(",")
                                val x = coordList.first().removePrefix("(").toFloat()
                                val y = coordList.last().removeSuffix(")").toFloat()


                                Offset(x = x, y = y)
                            }
                        }
                    }

                    val radius = theElement.attr("radius").toFloatOrNull() ?: 0.toFloat()

                    drawCircle(
                        color = color,
                        center = center,
                        radius = radius
                    )
                }

                "draw-line" -> {
                    drawLine(
                        color = color,
                        start = Offset(x = 0.toFloat(), y = 0.toFloat()),
                        end = Offset(x = canvasWidth, y = canvasHeight)
                    )
                }
            }
        }

    }
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

