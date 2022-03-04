package org.phoenixframework.liveview

import org.jsoup.nodes.Element
import kotlin.test.*

class DOMTest {
    @Test
    fun parseSingleRoot() {
        val parsed = DOM.parse("<div>Foo</div>")

        assertEquals(1, parsed.size)

        val div = parsed[0]
        assertEquals("Foo", div.text())
        assertEquals("div", div.tagName())
    }

    @Test
    fun parseMultipleRoots() {
        val parsed = DOM.parse("<div>Bar</div><Text>Biz</Text>")

        assertEquals(2, parsed.size)

        val bar = parsed[0]
        assertEquals("Bar", bar.text())
        assertEquals("div", bar.tagName())

        val biz = parsed[1]
        assertEquals("Biz", biz.text())
        assertEquals("text", biz.tagName())
    }

    @Test
    fun all() {
        val sampleHtml = """
        <div class="foo">Foo 1</div>
        <div class="bar">Bar 1</div>
        <div class="foo">Foo 2</div>
        """.trimIndent()

        val parsed = DOM.parse(sampleHtml)
        val foos = DOM.all(parsed, ".foo")

        assertEquals(2, foos.size)

        val foo1 = foos[0]
        assertEquals("Foo 1", foo1.text())
    }

    @Test
    fun maybeOneWithoutType() {
        val sample = """
        <div class="foo">Foo 1</div>
        <div class="bar">Bar 1</div>
        <div class="foo">Foo 2</div>
        """.trimIndent()

        val htmlTree = DOM.parse(sample)

        val barResult = DOM.maybeOne(htmlTree, ".bar")
        assertTrue(barResult.isSuccess)

        val barElement = barResult.getOrThrow()
        assertEquals("Bar 1", barElement.text())

        val fooResult = DOM.maybeOne(htmlTree, ".foo")
        assertTrue(fooResult.isFailure)
        assertEquals(
            """
                expected selector .foo to return a single element, but got 2 within:
                
                <div class="foo">Foo 1</div>
                <div class="bar">Bar 1</div>
                <div class="foo">Foo 2</div>
            """.trimIndent(),
            fooResult.exceptionOrNull()?.message
        )

        val bizResult = DOM.maybeOne(htmlTree, ".biz")
        assertTrue(bizResult.isFailure)
        assertEquals(
            """
                expected selector .biz to return a single element, but got none within:
                
                <div class="foo">Foo 1</div>
                <div class="bar">Bar 1</div>
                <div class="foo">Foo 2</div>
            """.trimIndent(),
            bizResult.exceptionOrNull()?.message
        )
    }

    @Test
    fun maybeOneWithType() {
        val sample = """
        <div data-phx-main="true" data-phx-session="SFMyNTY.g2gDaAJhBHQAAAAHZAACaWRtAAAAFHBoeC1GbS1icW1JZ1hIZ0NmNFFDZAAKcGFyZW50X3BpZGQAA25pbGQACHJvb3RfcGlkZAADbmlsZAAJcm9vdF92aWV3ZAAtRWxpeGlyLlBob2VuaXguTGl2ZVZpZXdUZXN0LldpdGhDb21wb25lbnRMaXZlZAAGcm91dGVyZAAiRWxpeGlyLlBob2VuaXguTGl2ZVZpZXdUZXN0LlJvdXRlcmQAB3Nlc3Npb250AAAAAGQABHZpZXdkAC1FbGl4aXIuUGhvZW5peC5MaXZlVmlld1Rlc3QuV2l0aENvbXBvbmVudExpdmVuBgDO0MBpeAFiAAFRgA.lWdk-lH-6vNUKyDdsUDYYQE5j6Mtuuc1cC16am1k3Ak" data-phx-static="SFMyNTY.g2gDaAJhBHQAAAADZAAKYXNzaWduX25ld2pkAAVmbGFzaHQAAAAAZAACaWRtAAAAFHBoeC1GbS1icW1JZ1hIZ0NmNFFDbgYAztDAaXgBYgABUYA.EqK2S1Hpxe298NfuVbTBjBzSACIzmlRK1x0CHWjRDQs" data-phx-view="LiveViewTest.WithComponentLive" id="phx-Fm-bqmIgXHgCf4QC">Redirect: none\n\n" +
" <div data-phx-component="1" id="chris" phx-target="#chris" phx-click="transform">\n" +
" chris says hi\n" +
" \n</div>
          <div data-phx-component="2" id="jose" phx-target="#jose" phx-click="transform">\n" +
" jose says hi\n" +
" \n</div>
        </div>"
        """

        val htmlTree = DOM.parse(sample)

        val result = DOM.maybeOne(htmlTree, "#chris", "phx-target")
        assertEquals("chris", result.getOrNull()?.attr("id"))
    }

    @Test
    fun allAttributes() {
        val sample = """
        <div data-foo="abc"></div>
        <div data-bar="def">
            <div data-foo="ghi"></div>
        </div>
        <div data-foo="jkl"></div>
        """

        val htmlTree = DOM.parse(sample)

        val result = DOM.allAttributes(htmlTree, "data-foo")
        assertEquals(result, listOf("abc", "ghi", "jkl"))
    }

    @Test
    fun allValues() {
        val sample = "<div foo=\"foo\" bar=\"bar\" phx-value-baz=\"baz\" value=\"123\"></div>"
        val parsed = DOM.parse(sample)

        val actual = DOM.allValues(parsed[0])
        assertEquals(mapOf("baz" to "baz", "value" to "123"), actual)
    }

    @Test
    fun tag() {
        val sample = """
        <div class="foo">Foo</div>
        <text class="bar">Bar</text>
        """

        val parsed = DOM.parse(sample)

        assertEquals("div", DOM.tag(parsed[0]))
        assertEquals("text", DOM.tag(parsed[1]))
    }

    @Test
    fun attribute() {
        val sample = """
        <div data-phx-session="123456" data-phx-main="true"></div>
        """

        val htmlTree = DOM.parse(sample)
        val element = htmlTree[0]

        assertEquals("123456", DOM.attribute(element, "data-phx-session"))
        assertEquals("true", DOM.attribute(element, "data-phx-main"))

        // if a string representation of the element is used
        assertNull(DOM.attribute(sample, "data-phx-session"))
    }

    @Test
    fun toHTMLWithNestedTags() {
        val sample = "<div><div class=\"foo\">Foo</div>\n\n<div class=\"bar\">Bar</div></div>"
        val parsed = DOM.parse(sample)

        val html = DOM.toHTML(parsed)
        assertEquals(sample, html)
    }

    @Test
    fun toHTMLWithoutNestedTags() {
        val sample = "<div class=\"foo\">Foo\n\n</div>"
        val parsed = DOM.parse(sample)

        val html = DOM.toHTML(parsed)
        assertEquals(sample, html)
    }

    @Test
    fun toText() {
        val sample = """
        <div class="foo">Foo
            <div class="biz">Biz</div>
        </div>
        <div class="bar">Bar</div>
        """
        val parsed = DOM.parse(sample)

        val text = DOM.toText(parsed)
        assertEquals("Foo Biz Bar", text)
    }

    @Test
    fun byID() {
        val sample = """
        <div class="foo" id="foo">Foo
            <div class="biz" id="biz">Biz</div>
        </div>
        <div class="foo" id="foo">Foo</div>
        """
        val parsed = DOM.parse(sample)

        val biz = DOM.byID(parsed, "biz")
        assertEquals("Biz", biz.text())

        assertFailsWith<DOM.IDNotFound> {
            DOM.byID(parsed, "foo")
        }

        assertFailsWith<DOM.IDNotFound> {
            DOM.byID(parsed, "bar")
        }
    }

    @Test
    fun childNodesWithParsed() {
        val sample = """
        <div class="foo">Foo
            <div class="biz">Biz</div>
            <div class="fizz">Fizz</div>
        </div>
        <div class="bar">Bar</div>
        """
        val parsed = DOM.parse(sample)

        val foo = parsed[0]
        val fooChildren = DOM.childNodes(foo)
        assertEquals("Biz", fooChildren[0].text())
        assertEquals("Fizz", fooChildren[1].text())

        val bar = parsed[1]
        val barChildren = DOM.childNodes(bar)
        assertEquals(0, barChildren.size)
    }

    @Test
    fun childNodesWithString() {
        val sample = """
        <div class="foo">Foo
            <div class="biz">Biz</div>
            <div class="fizz">Fizz</div>
        </div>
        <div class="bar">Bar</div>
        """

        // if a string representation of the elements
        val children = DOM.childNodes(sample)
        assertEquals(0, children.size)
    }

    @Test
    fun attrs() {
        val sample = """
        <div class="foo" data-phx-session="asdf">Foo</div>
        <div>Bar</div>
        """
        val parsed = DOM.parse(sample)

        val foo = parsed[0]
        val fooAttributes = DOM.attrs(foo)

        assertEquals(2, fooAttributes.size())
        assertEquals("foo", fooAttributes.get("class"))
        assertEquals("asdf", fooAttributes.get("data-phx-session"))

        val bar = parsed[1]
        val barAttributes = DOM.attrs(bar)

        assertEquals(0, barAttributes.size())
    }

    @Test
    fun innerHTML() {
        val sample = """
        <div class="foo" id="foo">Foo
            <div class="biz">Biz</div>
            <div class="fizz">Fizz</div>
        </div>
        <div class="bar">Bar</div>
        """

        val parsed = DOM.parse(sample)

        val innerHTML = DOM.innerHTML(parsed, "foo")

        assertEquals("Biz", innerHTML[0].text())
        assertEquals("Fizz", innerHTML[1].text())
    }

    @Test
    fun componentID() {
        val sample = """
        <div data-phx-component="123"></div>
        """
        val parsed = DOM.parse(sample)

        val componentID = DOM.componentID(parsed[0])
        assertEquals("123", componentID)
    }

    @Test
    fun findStaticViews() {
        val sample = """
        <div id="foo" data-phx-static="abc">Foo
            <div id="biz" data-phx-static="def">Biz</div>
            <div id="fizz">Fizz</div>
        </div>
        <div id="bar" data-phx-static="ghi">Bar</div>
        """
        val parsed = DOM.parse(sample)

        val staticViews = DOM.findStaticViews(parsed)

        assertEquals("abc", staticViews["foo"])
        assertEquals("def", staticViews["biz"])
        assertEquals("ghi", staticViews["bar"])
        assertNull(staticViews["fizz"])
    }

    @Test
    fun findLiveViewsWithTooBigSession() {
        val tooBigSession = "t".repeat(4432)

        val sample = """
        <h1>top</h1>
        <div data-phx-view="789"
          data-phx-session="SESSION1"
          id="phx-123"></div>
        <div data-phx-parent-id="456"
            data-phx-view="789"
            data-phx-session="SESSION2"
            data-phx-static="STATIC2"
            id="phx-456"></div>
        <div data-phx-session="$tooBigSession"
          data-phx-view="789"
          id="phx-458"></div>
        <h1>bottom</h1>
        """
        val parsed = DOM.parse(sample)

        val liveViews = DOM.findLiveViews(parsed)

        assertEquals(DOM.LiveView("phx-123", "SESSION1", null), liveViews[0])
        assertEquals(DOM.LiveView("phx-456", "SESSION2", "STATIC2"), liveViews[1])
        assertEquals(DOM.LiveView("phx-458", tooBigSession, null), liveViews[2])
    }

    @Test
    fun findLiveViewWithoutLiveViews() {
        val sample = """
        <div id="foo" data-phx-static="abc">Foo
            <div id="biz" data-phx-static="def">Biz</div>
            <div id="fizz">Fizz</div>
        </div>
        <div id="bar" data-phx-static="ghi">Bar</div>
        """
        val parsed = DOM.parse(sample)

        val liveViews = DOM.findLiveViews(parsed)
        assertEquals(0, liveViews.size)
    }

    @Test
    fun findLiveViewWithLiveViews() {
        val sample = """
        <h1>top</h1>
        <div data-phx-view="789"
          data-phx-session="SESSION1"
          id="phx-123"></div>
        <div data-phx-parent-id="456"
            data-phx-view="789"
            data-phx-session="SESSION2"
            data-phx-static="STATIC2"
            id="phx-456"></div>
        <div data-phx-session="SESSIONMAIN"
          data-phx-view="789"
          data-phx-main="true"
          id="phx-458"></div>
        <h1>bottom</h1>
        """

        val parsed = DOM.parse(sample)
        val liveViews = DOM.findLiveViews(parsed)

        assertEquals(DOM.LiveView("phx-458", "SESSIONMAIN", null), liveViews[0])
        assertEquals(DOM.LiveView("phx-123", "SESSION1", null), liveViews[1])
        assertEquals(DOM.LiveView("phx-456", "SESSION2", "STATIC2"), liveViews[2])
    }

    @Test
    fun deepMerge() {
        val target = mapOf(
            "0" to "foo",
            "1" to "",
            "3" to mapOf(
                "1" to mapOf(
                    "0" to "bar"
                ),
                "2" to mapOf(
                    "0" to "biz"
                )
            )
        )

        val source = mapOf(
            "1" to "oof",
            "2" to "rab",
            "3" to mapOf(
                "1" to mapOf(
                    "0" to "fib"
                ),
                "2" to "bif"
            ),
            "4" to mapOf(
                "0" to "zib"
            )
        )

        val expected = mapOf(
            "0" to "foo",
            "1" to "oof",
            "2" to "rab",
            "3" to mapOf(
                "1" to mapOf(
                    "0" to "fib"
                ),
                "2" to "bif"
            ),
            "4" to mapOf(
                "0" to "zib"
            )
        )

        val deepMerged = DOM.deepMerge(target, source)
        assertEquals(expected, deepMerged)
    }

    @Test
    fun filter() {
        val sample = """
        <div data-phx="123">Foo
            <div data-phx="456">Bar</div>
            <div>Baz</div>
        </div>
        <div>Baz</div>
        <div data-phx="789">Biz</div>
        """
        val parsed = DOM.parse(sample)

        // Elements
        val filteredElements = DOM.filter(parsed) { element ->
            DOM.attribute(element, "data-phx") != null
        }

        assertEquals(3, filteredElements.size)
        assertEquals("Foo", filteredElements[0].ownText())
        assertEquals("Bar", filteredElements[1].ownText())
        assertEquals("Biz", filteredElements[2].ownText())

        // Element

        val filteredElement = DOM.filter(parsed[0]) { element ->
            DOM.attribute(element, "data-phx") != null
        }

        assertEquals(2, filteredElement.size)
        assertEquals("Foo", filteredElement[0].ownText())
        assertEquals("Bar", filteredElement[1].ownText())
    }

    @Test
    fun reverseFilter() {
        val sample = """
        <div data-phx="123">Foo
            <div data-phx="456">Bar</div>
            <div>Baz</div>
        </div>
        <div>Baz</div>
        <div data-phx="789">Biz</div>
        """
        val parsed = DOM.parse(sample)

        // Elements
        val reverseFilteredElements = DOM.reverseFilter(parsed) { element ->
            DOM.attribute(element, "data-phx") != null
        }

        assertEquals(3, reverseFilteredElements.size)
        assertEquals("Biz", reverseFilteredElements[0].ownText())
        assertEquals("Bar", reverseFilteredElements[1].ownText())
        assertEquals("Foo", reverseFilteredElements[2].ownText())

        // Element

        val reverseFilteredElement = DOM.reverseFilter(parsed[0]) { element ->
            DOM.attribute(element, "data-phx") != null
        }

        assertEquals(2, reverseFilteredElement.size)
        assertEquals("Bar", reverseFilteredElement[0].ownText())
        assertEquals("Foo", reverseFilteredElement[1].ownText())
    }


    @Test
    fun componentIDs() {
        val sample = """
        <div data-phx-main="true" data-phx-session="SFMyNTY.g2gDaAJhBHQAAAAHZAACaWRtAAAAFHBoeC1GbkR3TXhTR1BHQTlid1JQZAAKcGFyZW50X3BpZGQAA25pbGQACHJvb3RfcGlkZAADbmlsZAAJcm9vdF92aWV3ZAAlRWxpeGlyLlBob2VuaXguTGl2ZVZpZXdUZXN0LkZsYXNoTGl2ZWQABnJvdXRlcmQAIkVsaXhpci5QaG9lbml4LkxpdmVWaWV3VGVzdC5Sb3V0ZXJkAAdzZXNzaW9udAAAAABkAAR2aWV3ZAAlRWxpeGlyLlBob2VuaXguTGl2ZVZpZXdUZXN0LkZsYXNoTGl2ZW4GABQHEoB4AWIAAVGA.BDGnjwPAeKzS5jNf5GtWoYx2nRMYWt_Lv_rglRhSUjQ" data-phx-static="SFMyNTY.g2gDaAJhBHQAAAADZAAKYXNzaWduX25ld2pkAAVmbGFzaHQAAAAAZAACaWRtAAAAFHBoeC1GbkR3TXhTR1BHQTlid1JQbgYAFAcSgHgBYgABUYA.V7O5lgauyk9vb1ZQbPieh3jpCjZ1CRG05dZAR-ldWKg" data-phx-view="LiveViewTest.FlashLive" id="phx-FnDwMxSGPGA9bwRP">uri[http://www.example.com/flash-root]
        root[]:info
        root[]:error
        <div data-phx-component="1" id="flash-component" phx-target="1" phx-click="click"><span phx-click="lv:clear-flash">Clear all</span><span phx-click="lv:clear-flash" phx-value-key="info">component[]:info</span><span phx-click="lv:clear-flash" phx-value-key="error">component[]:error</span></div>
        child[<div data-phx-parent-id="phx-FnDwMxSGPGA9bwRP" data-phx-session="SFMyNTY.g2gDaAJhBHQAAAAHZAACaWRtAAAAC2ZsYXNoLWNoaWxkZAAKcGFyZW50X3BpZFhkAA1ub25vZGVAbm9ob3N0AAAB1gAAAAAAAAAAZAAIcm9vdF9waWRYZAANbm9ub2RlQG5vaG9zdAAAAdYAAAAAAAAAAGQACXJvb3Rfdmlld2QAJUVsaXhpci5QaG9lbml4LkxpdmVWaWV3VGVzdC5GbGFzaExpdmVkAAZyb3V0ZXJkACJFbGl4aXIuUGhvZW5peC5MaXZlVmlld1Rlc3QuUm91dGVyZAAHc2Vzc2lvbnQAAAAAZAAEdmlld2QAKkVsaXhpci5QaG9lbml4LkxpdmVWaWV3VGVzdC5GbGFzaENoaWxkTGl2ZW4GACMHEoB4AWIAAVGA.DEzDbUo6P7cBPByCHO5mo2wK-bxa2Ru_GX6_5ZUGkug" data-phx-static="" data-phx-view="LiveViewTest.FlashChildLive" id="flash-child"></div>]
        <div id="">
        stateless_component[]:info
        stateless_component[]:error
        </div></div>
        """

        val parsed = DOM.parse(sample)

        val componentIDs = DOM.componentIDs("phx-FnDwMxSGPGA9bwRP", parsed)
        assertEquals(setOf(ComponentID(1)), componentIDs)
    }

    @Test
    fun patchIDUpdatesDeeplyNestedHTML() {
        val html =
            "<div data-phx-session=\"SESSIONMAIN\"\n" +
                    "               data-phx-view=\"789\"\n" +
                    "               data-phx-main=\"true\"\n" +
                    "               id=\"phx-458\">\n<div id=\"foo\">Hello</div>\n<div id=\"list\">\n" +
                    "  <div id=\"1\">a</div>\n" +
                    "  <div id=\"2\">a</div>\n" +
                    "  <div id=\"3\">a</div>\n</div>\n</div>\n"
        val innerHtml =
            "<div id=\"foo\">Hello World</div>\n<div id=\"list\">\n" +
                    "  <div id=\"2\" class=\"foo\">a</div>\n" +
                    "  <div id=\"3\">\n" +
                    "    <div id=\"5\">inner</div>\n" +
                    "  </div>\n" +
                    "  <div id=\"4\">a</div>\n</div>\n"

        val (newHtml, _) = DOM.patchID("phx-458", DOM.parse(html), DOM.parse(innerHtml))

        val actual = DOM.toHTML(newHtml)

        assertTrue(!actual.contains("<div id=\"1\">a</div>"))
        assertTrue(actual.contains("<div id=\"2\" class=\"foo\">a</div>"))
        assertTrue(
            actual.contains(
                "<div id=\"3\">\n" +
                        "    <div id=\"5\">inner</div>\n" +
                        "  </div>"
            )
        )
        assertTrue(actual.contains("<div id=\"4\">a</div>"))
    }

    @Test
    fun patchIDInsertsNewElementsWhenPhxUpdateAppend() {
        val html =
            "<div data-phx-session=\"SESSIONMAIN\"\n" +
                    "               data-phx-view=\"789\"\n" +
                    "               data-phx-main=\"true\"\n" +
                    "               id=\"phx-458\">\n<div id=\"list\" phx-update=\"append\">\n" +
                    "  <div id=\"1\">a</div>\n" +
                    "  <div id=\"2\">a</div>\n" +
                    "  <div id=\"3\">a</div>\n</div>\n</div>\n"
        val innerHtml =
            "<div id=\"list\" phx-update=\"append\">\n" +
                    "  <div id=\"4\" class=\"foo\">a</div>\n</div>\n"

        val (newHtml, _) = DOM.patchID("phx-458", DOM.parse(html), DOM.parse(innerHtml))

        val actual = DOM.toHTML(newHtml)

        assertTrue(actual.contains("<div id=\"1\">a</div>"))
        assertTrue(actual.contains("<div id=\"2\">a</div>"))
        assertTrue(actual.contains("<div id=\"3\">a</div><div id=\"4\" class=\"foo\">a</div>"))
    }

    @Test
    fun patchIDInsertsNewElementsWhenPhxUpdatePrepend() {
        val html =
            "<div data-phx-session=\"SESSIONMAIN\"\n" +
                    "               data-phx-view=\"789\"\n" +
                    "               data-phx-main=\"true\"\n" +
                    "               id=\"phx-458\">\n<div id=\"list\" phx-update=\"append\">\n" +
                    "  <div id=\"1\">a</div>\n" +
                    "  <div id=\"2\">a</div>\n" +
                    "  <div id=\"3\">a</div>\n</div>\n</div>\n"
        val innerHtml = "<div id=\"list\" phx-update=\"prepend\">\n" +
                "  <div id=\"4\">a</div>\n</div>\n"

        val (newHtml, _) = DOM.patchID("phx-458", DOM.parse(html), DOM.parse(innerHtml))

        val actual = DOM.toHTML(newHtml)

        assertTrue(actual.contains("<div id=\"4\">a</div><div id=\"1\">a</div>"))
        assertTrue(actual.contains("<div id=\"2\">a</div>"))
        assertTrue(actual.contains("<div id=\"3\">a</div>"))
    }

    @Test
    fun patchIDUpdatesExistingElementsWhenPhxUpdateAppend() {
        val html =
            "<div data-phx-session=\"SESSIONMAIN\"\n" +
                    "               data-phx-view=\"789\"\n" +
                    "               data-phx-main=\"true\"\n" +
                    "               id=\"phx-458\">\n<div id=\"list\" phx-update=\"append\">\n" +
                    "  <div id=\"1\">a</div>\n" +
                    "  <div id=\"2\">a</div>\n" +
                    "  <div id=\"3\">a</div>\n</div>\n</div>\n"
        val innerHtml =
            "<div id=\"list\" phx-update=\"append\">\n" +
                    "  <div id=\"1\" class=\"foo\">b</div>\n" +
                    "  <div id=\"2\">b</div>\n</div>\n"

        val (newHtml, _) = DOM.patchID("phx-458", DOM.parse(html), DOM.parse(innerHtml))

        val actual = DOM.toHTML(newHtml)

        assertTrue(actual.contains("<div id=\"1\" class=\"foo\">b</div>"))
        assertTrue(actual.contains("<div id=\"2\">b</div>"))
        assertTrue(actual.contains("<div id=\"3\">a</div>"))
    }
}
