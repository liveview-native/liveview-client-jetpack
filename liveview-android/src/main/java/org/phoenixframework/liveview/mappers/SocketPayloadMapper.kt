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
                    val pMap: Map<String, List<String>>? =
                        comprehensionMap["p"]?.let { it as Map<String, List<String>> }

                    generateHtmlFromDynamics(
                        innerComprehensionValues,
                        outerValues,
                        pMap
                    ).joinToString(separator = "")
                }
                else -> {
                    ""
                }
            }
        }

        return generateFinalDomFromLiveValues(valuesToMerge)
    }

    tailrec fun initialWalkDownSocketTreeToBody(inputMap: Map<String, Any?>): Map<String, Any?> =
        if (inputMap.containsKey("0")) {
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

    private fun generateHtmlFromDynamics(
        innerComprehensionValues: List<List<Any>>,
        outerValues: List<String>?,
        pMap: Map<String, Any>?
    ): List<String> = innerComprehensionValues.map { innerListValue: List<Any> ->
        if (innerListValue.isEmpty()) {
            if (pMap != null) {
                pMap.values.first().let { it as List<String> }.first()
            } else {
                outerValues?.first() ?: ""
            }
        } else {
            val prefix = outerValues?.first() ?: ""
            val postfix = outerValues?.last() ?: ""

            when (val firstInnerValue = innerListValue.first()) {
                is String -> {
                    "${prefix}$firstInnerValue${postfix}"
                }
                is Map<*, *> -> {
                    val innerMap = firstInnerValue as Map<String, Any?>
                    val innerList = innerMap["d"] as List<List<Any>>

                    generateHtmlFromDynamics(
                        innerComprehensionValues = innerList,
                        outerValues = outerValues,
                        pMap = pMap
                    ).joinToString(prefix = prefix, separator = "", postfix = postfix)
                }
                else -> {
                    ""
                }
            }
        }
    }

    private fun generateFinalDomFromLiveValues(liveValues: List<String>): Document {
        val finalDomList = if (liveValues.isNotEmpty()) {
            originalRenderDom.orderedMix(liveValues)
        } else {
            originalRenderDom
        }

        val finalDom = finalDomList.reduce { acc, s -> acc + s }

        return Jsoup.parse(finalDom)
    }

    fun parseDiff(message: Message): Document? =
        message.payload["diff"]?.let { it as Map<String, Any> }?.let(::extractDiff)

    fun extractDiff(rawDiff: Map<String, Any?>): Document {
        val firstLevel = rawDiff["0"] as LinkedTreeMap<String, Any?>
        val secondLevel = firstLevel["0"] as LinkedTreeMap<String, Any?>
        val liveValuesLevel = secondLevel.entries

        liveValuesLevel.forEach { mutableEntry ->
            when (mutableEntry.value) {
                is String -> {
                    liveValuesMap[mutableEntry.key] = mutableEntry.value as String
                }
                is Map<*, *> -> Unit
            }
        }

        val diffListLiveValues = liveValuesMap.values.map { it as String }

        return generateFinalDomFromLiveValues(diffListLiveValues)
    }
}