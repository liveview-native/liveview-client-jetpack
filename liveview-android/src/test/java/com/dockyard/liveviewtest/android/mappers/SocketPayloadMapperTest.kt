package com.dockyard.liveviewtest.android.mappers

import org.jsoup.Jsoup
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

internal class SocketPayloadMapperTest {
    private lateinit var sut: SocketPayloadMapper

    @Before
    fun setup() {
        sut = SocketPayloadMapper()
    }

    @Test
    fun `test simple input`() {
        val inputPayload = mapOf(
            "rendered" to mapOf(
                "0" to mapOf(
                    "0" to mapOf(
                        "s" to listOf("<text>Hello</text>")
                    )
                )
            )
        )

        val outputDom = sut.mapRawPayloadToDom(inputPayload)
        val expected = Jsoup.parse("<text>Hello</text>")

        assert(expected.html() == outputDom.html())
    }

    @Test
    fun `test column input`() {
        val inputPayload = mapOf(
            "rendered" to mapOf(
                "0" to mapOf(
                    "0" to mapOf(
                        "s" to listOf(
                            "<column width=\"fill\" height=\"fill\">\n\n<text>Hello1</text>\n<text>Hello2</text>\n<text>Hello3</text>\n\n</column>"
                        )
                    )
                )
            )
        )

        val outputDom = sut.mapRawPayloadToDom(inputPayload)

        val expected = Jsoup.parse(
            "<column width=\"fill\" height=\"fill\">\n\n<text>Hello1</text>\n<text>Hello2</text>\n<text>Hello3</text>\n\n</column>"
        )

        assert(expected.html() == outputDom.html())
    }

    @Test
    fun `test single assigns parameter`() {
        val inputPayload = mapOf(
            "rendered" to mapOf(
                "0" to mapOf(
                    "0" to mapOf(
                        "0" to "1248",
                        "s" to listOf(
                            "<column>\n\n   <text>", "</text>\n\n</column>"
                        )
                    )
                )
            )
        )

        val outputDom = sut.mapRawPayloadToDom(inputPayload)
        val expected = Jsoup.parse("<column>\n\n<text>1248</text>\n\n</column>")
        val expectedHtml = expected.body().html()
        val actualHtml = outputDom.body().html()

        assertEquals("assigns params", expectedHtml, actualHtml)
    }

    @Test
    fun `test multiple assigns parameters`() {
        val inputPayload = mapOf(
            "rendered" to mapOf(
                "0" to mapOf(
                    "0" to mapOf(
                        "0" to "1248",
                        "1" to "4000",
                        "s" to listOf(
                            "<column><text>", "</text><text>", "</text>", "</column>"
                        )
                    )
                )
            )
        )

        val outputDom = sut.mapRawPayloadToDom(inputPayload)

        val expected = Jsoup.parse(
            "<column>" +
                    "<text>1248</text>" +
                    "<text>4000</text>" +
                    "</column>"
        )

        val expectedHtml = expected.body().html().replace("\n", "")
        val actualHtml = outputDom.body().html().replace("\n", "")

        assertEquals("assigns params", expectedHtml, actualHtml)
    }

    @Test
    fun `test list comprehension with empty _item iterator`() {
        val inputPayload = mapOf(
            "rendered" to mapOf(
                "0" to mapOf(
                    "0" to mapOf(
                        "0" to mapOf(
                            //3 empty items
                            "d" to listOf(
                                emptyList<String>(),
                                emptyList<String>(),
                                emptyList<String>()
                            ),
                            "s" to listOf(
                                "<text>Hello</text>"
                            )
                        ),
                        "s" to listOf(
                            "<column>", "</column>"
                        )
                    )
                )
            )
        )

        val outputDom = sut.mapRawPayloadToDom(inputPayload)

        val expected = Jsoup.parse(
            "<column>" +
                    "<text>Hello</text>" +
                    "<text>Hello</text>" +
                    "<text>Hello</text>" +
                    "</column>"
        )

        val expectedHtml = expected.body().html().replace("\n", "")
        val actualHtml = outputDom.body().html().replace("\n", "")

        assertEquals("assigns params", expectedHtml, actualHtml)
    }


    @Test
    fun `test list comprehension with item iteration`() {
        val inputPayload = mapOf(
            "rendered" to mapOf(
                "0" to mapOf(
                    "0" to mapOf(
                        "0" to mapOf(
                            //3 empty items
                            "d" to listOf(
                                listOf("1"),
                                listOf("2"),
                                listOf("3"),
                            ),
                            "s" to listOf(
                                "<text>", "</text>"
                            )
                        ),
                        "s" to listOf(
                            "<column>", "</column>"
                        )
                    )
                )
            )
        )

        val outputDom = sut.mapRawPayloadToDom(inputPayload)

        val expected = Jsoup.parse(
            "<column>" +
                    "<text>1</text>" +
                    "<text>2</text>" +
                    "<text>3</text>" +
                    "</column>"
        )

        val expectedHtml = expected.body().html().replace("\n", "")
        val actualHtml = outputDom.body().html().replace("\n", "")

        assertEquals("assigns params", expectedHtml, actualHtml)
    }

    @Test
    fun `test nested list comprehension with empty _item iteration`() {
        val inputPayload = mapOf(
            "rendered" to mapOf(
                "0" to mapOf(
                    "0" to mapOf(
                        "0" to mapOf(
                            //3 empty items
                            "d" to listOf(
                                listOf(
                                    mapOf(
                                        "d" to listOf(
                                            emptyList<String>(),
                                            emptyList<String>(),
                                            emptyList<String>(),
                                        ),
                                        "s" to 0.0
                                    )
                                ),
                                listOf(
                                    mapOf(
                                        "d" to listOf(
                                            emptyList<String>(),
                                            emptyList<String>(),
                                            emptyList<String>(),
                                        ),
                                        "s" to 0.0
                                    )
                                ),
                                listOf(
                                    mapOf(
                                        "d" to listOf(
                                            emptyList<String>(),
                                            emptyList<String>(),
                                            emptyList<String>(),
                                        ),
                                        "s" to 0.0
                                    )
                                ),
                            ),
                            "p" to mapOf(
                                "0" to listOf(
                                    "<text>A</text>"
                                )
                            ),
                            "s" to listOf(
                                "<row>", "</row>"
                            )
                        ),
                        "s" to listOf(
                            "<column>", "</column>"
                        )
                    )
                )
            )
        )

        val outputDom = sut.mapRawPayloadToDom(inputPayload)

        val expected = Jsoup.parse(
            "<column>" +
                    "<row>" +
                    "<text>A</text><text>A</text><text>A</text>" +
                    "</row>" +
                    "<row>" +
                    "<text>A</text><text>A</text><text>A</text>" +
                    "</row>" +
                    "<row>" +
                    "<text>A</text><text>A</text><text>A</text>" +
                    "</row>" +
                    "</column>"
        )

        val expectedHtml = expected.body().html().replace("\n", "")
        val actualHtml = outputDom.body().html().replace("\n", "")

        assertEquals("Nested Comprehension", expectedHtml, actualHtml)
    }
}