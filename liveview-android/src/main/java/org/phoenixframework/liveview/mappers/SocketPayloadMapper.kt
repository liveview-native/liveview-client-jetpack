package org.phoenixframework.liveview.mappers

import com.google.gson.internal.LinkedTreeMap
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.phoenixframework.Message
import org.phoenixframework.liveview.extensions.orderedMix

class SocketPayloadMapper {

    private lateinit var originalRenderDom: List<String>
    private lateinit var liveValuesMap: MutableMap<String, Any?>

    fun mapRawPayloadToDom(payload: Map<String, Any?>): Document {
        val renderedMap: Map<String, Any?> = payload["rendered"] as Map<String, Any?>

        val domDiffList = initialWalkDownSocketTreeToBody(renderedMap)

        originalRenderDom = domDiffList["s"] as List<String>

        liveValuesMap = domDiffList.filter { it.key != "s" } as MutableMap<String, Any?>

        val valuesToMerge: List<String> = liveValuesMap.map { theEntry ->
            when (theEntry.value) {
                is String -> {
                    theEntry.value as String
                }
                is Map<*, *> -> {
                    val comprehensionMap = theEntry.value as Map<String, Any>

                    val outerValues = comprehensionMap["s"] as List<String>
                    val innerComprehensionValues = comprehensionMap["d"] as List<List<Any>>

                    val pMap: Map<String, List<String>>? = if (comprehensionMap.containsKey("p")) {
                        comprehensionMap["p"] as Map<String, List<String>>
                    } else null

                    //recurse dynamics
                    val htmlList: List<String> =
                        generateHtmlFromDynamics(innerComprehensionValues, outerValues, pMap)

                    val stringRepresentation: String = htmlList.reduce { acc, s -> acc + s }
                    stringRepresentation
                }
                else -> {
                    ""
                }
            }
        }

        return generateFinalDomFromLiveValues(valuesToMerge)

    }

    fun initialWalkDownSocketTreeToBody(inputMap: Map<String, Any?>): Map<String, Any?> {

        return if (inputMap.containsKey("0")) {

            val castedInputMap = inputMap["0"] as Map<String, Any?>

            if (castedInputMap.containsKey("s")) {
                castedInputMap
            } else {
                val nextLevelDeepMap = inputMap["0"] as Map<String, Any?>
                initialWalkDownSocketTreeToBody(nextLevelDeepMap)
            }
        } else {
            inputMap
        }
    }

    private fun generateHtmlFromDynamics(
        innerComprehensionValues: List<List<Any>>,
        outerValues: List<String>?,
        pMap: Map<String, Any>?
    ): List<String> {

        return innerComprehensionValues.map { innerListValue: List<Any> ->

            if (innerListValue.isEmpty()) {

                if (pMap != null) {

                    val innerTemplateList = pMap.values.first() as List<String>
                    val innerTemplate = innerTemplateList.first()
                    "$innerTemplate"

                } else {
                    outerValues?.first() ?: ""
                }
            } else {
                when (innerListValue.first()) {
                    is String -> {
                        val element = "${outerValues?.first() ?: ""}${innerListValue.first()}${outerValues?.last() ?: ""}"
                        println("ELEMENT: $element")
                        element
                    }

                    is Map<*, *> -> {
                        println("IS MAP")
                        val innerMap = innerListValue.first() as Map<String, Any?>
                        val innerList = innerMap["d"] as List<List<Any>>

                        val generateHtmlFromDynamicsList: List<String> = generateHtmlFromDynamics(
                            innerComprehensionValues = innerList,
                            outerValues = outerValues,
                            pMap = pMap
                        )

                        println(generateHtmlFromDynamicsList)
                        val innerFinalString: String = generateHtmlFromDynamicsList.reduce { acc, s -> acc + s }
                        val finalString = "${outerValues?.first() ?: ""}" + innerFinalString + "${outerValues?.last() ?: ""}"
                        finalString
                    }

                    else -> {
                        println("IS ELSE")
                        ""
                    }
                }
            }

        }
    }

    private fun generateFinalDomFromLiveValues(liveValues: List<String>): Document {

        if (liveValues.isNotEmpty()) {

            val finalDomList = originalRenderDom.orderedMix(liveValues)

            val finalDom = finalDomList.reduce { acc, s -> acc + s }

            val document: Document = Jsoup.parse(finalDom)
            return document
        } else {
            val finalDom = originalRenderDom.reduce { acc, s -> acc + s }
            val document: Document = Jsoup.parse(finalDom)

            return document
        }
    }

    fun parseDiff(message: Message) : Document? {
        if (message.payload.containsKey("diff")) {
            val rawDiff = message.payload["diff"] as Map<String, Any>

            return extractDiff(rawDiff)
        }

        return null
    }

    fun extractDiff(rawDiff: Map<String, Any?>): Document {

        val firstLevel = rawDiff["0"] as LinkedTreeMap<String, Any?>
        val secondLevel = firstLevel["0"] as LinkedTreeMap<String, Any?>

        val liveValuesLevel = secondLevel.entries

        liveValuesLevel.forEach { mutableEntry ->

            when (mutableEntry.value) {
                is String -> {
                    liveValuesMap[mutableEntry.key] = mutableEntry.value as String
                }
                is Map<*, *> -> {

                }
            }
        }

        val diffListLiveValues = liveValuesMap.values.map { it as String }
        return generateFinalDomFromLiveValues(diffListLiveValues)
    }

}