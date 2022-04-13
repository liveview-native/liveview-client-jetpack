package org.phoenixframework.liveview

import kotlinx.collections.immutable.persistentMapOf
import org.junit.Test
import kotlin.test.assertEquals

class DiffTest {
    @Test
    fun dropComponentIDs() {
        val rendered = Diff(
            diffByComponentID = diffByComponentID(
                ComponentID(1) to Diff(
                    substitutionByIndex = substitutionByIndex(0 to Literal("foo"))
                ),
                ComponentID(2) to Diff(
                    substitutionByIndex = substitutionByIndex(0 to Literal("bar"))
                ),
                ComponentID(3) to Diff(
                    substitutionByIndex = substitutionByIndex(0 to Literal("baz"))
                )
            )
        )
        val componentIDs = listOf(ComponentID(1), ComponentID(3))

        val result = rendered.dropComponentIDs(componentIDs)

        val expected = Diff(
            diffByComponentID = diffByComponentID(
                ComponentID(2) to Diff(
                    substitutionByIndex = substitutionByIndex(
                        0 to Literal("bar")
                    )
                )
            )
        )

        assertEquals(expected, result)
    }

    @Test
    fun mergeWithTemplateStaticWithSameSourceReturnsSelf() {
        val rendered = Diff(
            static = Template("text")
        )

        val mergedDiff = rendered.merge(rendered)

        assertEquals(rendered, mergedDiff)
    }

    @Test
    fun mergeWithTemplateStaticWithDifferentSourceTemplateStaticReturnsSourceTemplate() {
        val rendered = Diff(
            static = Template("initial")
        )
        val source = Diff(
            static = Template("source")
        )

        val mergedDiff = rendered.merge(source)

        assertEquals(source, mergedDiff)
    }

    @Test
    fun mergeWithTemplateStaticWithDynamicsWithSourceWithSameTemplateStaticWithDifferentDynamicsReturnsSourceDynamics() {
        val static = Template("Prefix", "Suffix")
        val rendered = Diff(
            static = static,
            dynamics = listOf(
                listOf(Literal("initial"))
            )
        )
        val source = Diff(
            static = static,
            dynamics = listOf(
                listOf(Literal("source"))
            )
        )

        val mergedDiff = rendered.merge(source)

        assertEquals(source, mergedDiff)
    }

    @Test
    fun mergeWithWithTemplateStaticWithTemplateByComponentIDWithSameTemplateStaticWithDifferentTemplateByComponentIDReturnsSourceTemplateByComponentID() {
        val static = Template("Prefix", "Suffix")
        val rendered = Diff(
            static = static,
            templateByComponentID = TemplateByComponentID(
                persistentMapOf(
                    ComponentID(1) to Template(" Component 1 Initial")
                )
            )
        )
        val source = Diff(
            static = static,
            templateByComponentID = TemplateByComponentID(
                persistentMapOf(
                    ComponentID(1) to Template(" Component 1 Source")
                )
            )
        )

        val mergedDiff = rendered.merge(source)

        assertEquals(source, mergedDiff)
    }

    @Test
    fun mergeDiffWithoutComponents() {
        val rendered = Diff(
            static = Template("LIVEOVERRIDESTART-", "-", "-LIVEOVERRIDEEND\n"),
            substitutionByIndex = substitutionByIndex(
                0 to Literal("123"),
                1 to Diff(
                    static = Template("The value is: ", ""),
                    substitutionByIndex = substitutionByIndex(
                        0 to Literal("256")
                    )
                )
            )
        )
        val diff = Diff(
            substitutionByIndex = substitutionByIndex(
                0 to Literal("789"),
                1 to Diff(
                    substitutionByIndex = substitutionByIndex(
                        0 to Literal("012")
                    )
                )
            )
        )

        val mergedDiff = rendered.merge(diff)
        val expected = Diff(
            static = Template("LIVEOVERRIDESTART-", "-", "-LIVEOVERRIDEEND\n"),
            substitutionByIndex = substitutionByIndex(
                0 to Literal("789"),
                1 to Diff(
                    static = Template("The value is: ", ""),
                    substitutionByIndex = substitutionByIndex(0 to Literal("012"))
                )
            )
        )

        assertEquals(expected, mergedDiff)
    }

    @Test
    fun mergeDiffWithComponents() {
        val rendered = Diff(
            static = Template("uri[", "]\nroot[", "]:info\nroot[", "]:error\n", "\nchild[", "]\n", "\n"),
            substitutionByIndex = substitutionByIndex(
                0 to Literal("http://www.example.com/flash-root"),
                1 to Literal(""),
                2 to Literal(""),
                3 to ComponentSubstitution(ComponentID(1)),
                4 to Literal("<div></div>"),
                5 to Diff(
                    static = Template(
                        "<div id=\"", "\">\nstateless_component[",
                        "]:info\nstateless_component[", "]:error\n</div>\n"
                    ),
                    substitutionByIndex = substitutionByIndex(
                        0 to Literal(""),
                        1 to Literal(""),
                        2 to Literal("")
                    )
                )
            ),
            diffByComponentID = diffByComponentID(
                ComponentID(1) to Diff(
                    static = Template(
                        "<div id=\"", "\" phx-target=\"",
                        "\" phx-click=\"click\">\n<span phx-click=\"lv:clear-flash\">Clear all</span>\n<span phx-click=\"lv:clear-flash\" phx-value-key=\"info\">component[",
                        "]:info</span>\n<span phx-click=\"lv:clear-flash\" phx-value-key=\"error\">component[",
                        "]:error</span>\n</div>\n"
                    ),
                    substitutionByIndex = substitutionByIndex(
                        0 to Literal("flash-component"),
                        1 to Literal("1"),
                        2 to Literal("ok!"),
                        3 to Literal("")
                    )
                )
            )
        )

        val diff = Diff(
            diffByComponentID = diffByComponentID(
                ComponentID(1) to Diff(
                    substitutionByIndex = substitutionByIndex(
                        2 to Literal("ok!"),
                        3 to Literal("oops!")
                    )
                )
            )
        )

        val mergedDiff = rendered.merge(diff)

        val expected = Diff(
            static = Template("uri[", "]\nroot[", "]:info\nroot[", "]:error\n", "\nchild[", "]\n", "\n"),
            substitutionByIndex = substitutionByIndex(
                0 to Literal("http://www.example.com/flash-root"),
                1 to Literal(""),
                2 to Literal(""),
                3 to ComponentSubstitution(ComponentID(1)),
                4 to Literal("<div></div>"),
                5 to Diff(
                    static = Template(
                        "<div id=\"", "\">\nstateless_component[",
                        "]:info\nstateless_component[", "]:error\n</div>\n"
                    ),
                    substitutionByIndex = substitutionByIndex(
                        0 to Literal(""),
                        1 to Literal(""),
                        2 to Literal("")
                    )
                )
            ),
            diffByComponentID = diffByComponentID(
                ComponentID(1) to Diff(
                    static = Template(
                        "<div id=\"", "\" phx-target=\"",
                        "\" phx-click=\"click\">\n<span phx-click=\"lv:clear-flash\">Clear all</span>\n<span phx-click=\"lv:clear-flash\" phx-value-key=\"info\">component[",
                        "]:info</span>\n<span phx-click=\"lv:clear-flash\" phx-value-key=\"error\">component[",
                        "]:error</span>\n</div>\n"
                    ),
                    substitutionByIndex = substitutionByIndex(
                        0 to Literal("flash-component"),
                        1 to Literal("1"),
                        2 to Literal("ok!"),
                        3 to Literal("oops!")
                    )
                )
            )
        )

        assertEquals(
            expected, mergedDiff
        )
    }

    @Test
    fun renderWithoutDynamics() {
        val diff = Diff(
            substitutionByIndex = substitutionByIndex(
                0 to Literal(""),
                1 to Literal("\n    lv:foo1.jpeg:0%\n    channel:nil\n    \n  "),
                2 to Literal("<input data-phx-active-refs=\"1282,1346\" data-phx-done-refs=\"\" data-phx-preflighted-refs=\"\" data-phx-update=\"ignore\" data-phx-upload-ref=\"phx-FnLSu0zdGSgfdQhC\" id=\"phx-FnLSu0zdGSgfdQhC\" name=\"avatar\" phx-hook=\"Phoenix.LiveFileUpload\" type=\"file\" multiple></input>")
            ),
            static = Template(
                "",
                "\n<form phx-change=\"validate\" phx-submit=\"save\">\n  ",
                "\n  ",
                "\n  <button type=\"submit\">save</button>\n</form>\n"
            )
        )

        val actual = diff.render()

        val expected =
            "<form phx-change=\"validate\" phx-submit=\"save\">\n" +
                    "  \n" +
                    "    lv:foo1.jpeg:0%\n" +
                    "    channel:nil\n" +
                    "    \n" +
                    "  \n" +
                    "  <input data-phx-active-refs=\"1282,1346\" data-phx-done-refs=\"\" data-phx-preflighted-refs=\"\" data-phx-update=\"ignore\" data-phx-upload-ref=\"phx-FnLSu0zdGSgfdQhC\" id=\"phx-FnLSu0zdGSgfdQhC\" name=\"avatar\" phx-hook=\"Phoenix.LiveFileUpload\" type=\"file\" multiple>\n" +
                    "  <button type=\"submit\">save</button>\n" +
                    "</form>"

        assertEquals(expected, DOM.toHTML(actual))
    }

    @Test
    fun renderWithTopLevelDynamicsWithLiterals() {
        val diff = Diff(
            // HTML tags are necessary for `render` as it must produce a string that `DOM.parse` sees as HTML
            static = Template(
                "<div>\nStatic0\n",
                "Static1\n",
                "Static2\n</div>\n"
            ),
            dynamics = listOf(
                listOf(Literal("Dynamic0\n"), Literal("Dynamic1\n"))
            )
        )

        val actual = diff.render()

        val expected = """
           <div>
           Static0
           Dynamic0
           Static1
           Dynamic1
           Static2
           </div>
        """.trimIndent()

        assertEquals(expected, DOM.toHTML(actual))
    }

    @Test
    fun renderWithTopLevelDynamicsWithComponentReference() {
        val diff = Diff(
            // HTML tags are necessary for `render` as it must produce a string that `DOM.parse` sees as HTML
            static = Template(
                "<div>\n  Static0\n  ",
                "\n  Static1\n</div>\n"
            ),
            dynamics = listOf(
                listOf(ComponentSubstitution(ComponentID(1)))
            ),
            diffByComponentID = diffByComponentID(
                ComponentID(1) to Diff(
                    static = Template("<div>\n    Component1Static1\n  </div>")
                )
            )
        )

        val actual = diff.render()

        val expected = """
           <div>
             Static0
             <div data-phx-component="1">
               Component1Static1
             </div>
             Static1
           </div>
        """.trimIndent()

        assertEquals(expected, DOM.toHTML(actual))
    }

    @Test
    fun renderWithSubstitutionByIndexWithDynamicsLiteral() {
        val diff = Diff(
            static = Template(
                "",
                "\n<form phx-change=\"validate\" phx-submit=\"save\">\n  ",
                "\n  ",
                "\n  <button type=\"submit\">save</button>\n</form>\n"
            ),
            substitutionByIndex = substitutionByIndex(
                0 to Literal(""),
                1 to Diff(
                    dynamics = listOf(
                        listOf(Literal("foo1.jpeg"), Literal("0"), Literal("nil"), Literal("")),
                        listOf(Literal("foo2.jpeg"), Literal("0"), Literal("nil"), Literal(""))
                    ),
                    static = Template(
                        "\n    lv:",
                        ":",
                        "%\n    channel:",
                        "\n    ",
                        "\n  "
                    )
                ),
                2 to Literal("<input data-phx-active-refs=\"1282,1346\" data-phx-done-refs=\"\" data-phx-preflighted-refs=\"\" data-phx-update=\"ignore\" data-phx-upload-ref=\"phx-FnLSu0zdGSgfdQhC\" id=\"phx-FnLSu0zdGSgfdQhC\" name=\"avatar\" phx-hook=\"Phoenix.LiveFileUpload\" type=\"file\" multiple></input>")
            )
        )

        val actual = diff.render()

        val expected =
            "<form phx-change=\"validate\" phx-submit=\"save\">\n" +
                    "  \n" +
                    "    lv:foo1.jpeg:0%\n" +
                    "    channel:nil\n" +
                    "    \n" +
                    "  \n" +
                    "    lv:foo2.jpeg:0%\n" +
                    "    channel:nil\n" +
                    "    \n" +
                    "  \n" +
                    "  <input data-phx-active-refs=\"1282,1346\" data-phx-done-refs=\"\" data-phx-preflighted-refs=\"\" data-phx-update=\"ignore\" data-phx-upload-ref=\"phx-FnLSu0zdGSgfdQhC\" id=\"phx-FnLSu0zdGSgfdQhC\" name=\"avatar\" phx-hook=\"Phoenix.LiveFileUpload\" type=\"file\" multiple>\n" +
                    "  <button type=\"submit\">save</button>\n" +
                    "</form>"

        assertEquals(expected, DOM.toHTML(actual))
    }

    @Test
    fun renderWithSubstitutionByIndexWithDynamicComponentReference() {
        val diff = Diff(
            static = Template(
                "<div>\n  ",
                "\n</div>\n"
            ),
            substitutionByIndex = substitutionByIndex(
                0 to Diff(
                    dynamics = listOf(
                        listOf(ComponentSubstitution(ComponentID(1))),
                        listOf(ComponentSubstitution(ComponentID(2)))
                    ),
                    static = Template(
                        "\n    ",
                        "\n  "
                    )
                )
            ),
            diffByComponentID = diffByComponentID(
                ComponentID(1) to Diff(
                    substitutionByIndex = substitutionByIndex(
                        0 to Literal(""),
                        1 to Literal("upload0"),
                        2 to Literal("1"),
                        3 to Literal(""),
                        4 to Literal("<input data-phx-active-refs=\"\" data-phx-done-refs=\"\" data-phx-preflighted-refs=\"\" data-phx-update=\"ignore\" data-phx-upload-ref=\"phx-FnL7qteVzAAQNwdH\" id=\"phx-FnL7qteVzAAQNwdH\" name=\"avatar\" phx-hook=\"Phoenix.LiveFileUpload\" type=\"file\" multiple></input>")
                    ),
                    static = Template(
                        "",
                        "\n<form phx-change=\"validate\" id=\"",
                        "\" phx-submit=\"save\" phx-target=\"", "\">\n  ",
                        "\n  ",
                        "\n  <button type=\"submit\">save</button>\n</form>\n"
                    )
                ),
                ComponentID(2) to Diff(
                    static = Template("loading...\n")
                )
            )
        )

        val actual = diff.render()

        val expected =
            "<div>\n" +
                    "  \n" +
                    "    <form phx-change=\"validate\" id=\"upload0\" phx-submit=\"save\" phx-target=\"1\" data-phx-component=\"1\">\n" +
                    "  \n" +
                    "  <input data-phx-active-refs=\"\" data-phx-done-refs=\"\" data-phx-preflighted-refs=\"\" data-phx-update=\"ignore\" data-phx-upload-ref=\"phx-FnL7qteVzAAQNwdH\" id=\"phx-FnL7qteVzAAQNwdH\" name=\"avatar\" phx-hook=\"Phoenix.LiveFileUpload\" type=\"file\" multiple>\n" +
                    "  <button type=\"submit\">save</button>\n" +
                    "</form>\n" +
                    "  \n" +
                    "    loading...\n" +
                    "\n" +
                    "  \n" +
                    "</div>"

        assertEquals(expected, DOM.toHTML(actual))
    }

    @Test
    fun toStringWithoutStaticWithoutDynamicsWithoutSubstitutionByIndexWithoutDiffByComponentWithoutTemplateByComponentID() {
        assertEquals("Diff()", Diff().toString())
    }

    @Test
    fun toStringWithoutStaticWithDynamicsWithoutSubstitutionByIndexWithDiffByComponentWithTemplateByComponentID() {
        assertEquals(
            """
            Diff(
              dynamic=
                [[ComponentSubstitution(ComponentID(1))], [Literal("Dynamic2")]],
              diffByComponentID=
                diffByComponent(
                  ComponentID(1) to Diff(
                    static=
                      Template("Component1Static0")
                  )
                ),
              templateByComponentID=
                templateByComponentID(
                  ComponentID(1) to Template("Component1Static0")
                )
            )
            """.trimIndent(),
            Diff(
                dynamics = listOf(
                    listOf(ComponentSubstitution(ComponentID(1))),
                    listOf(Literal("Dynamic2")),
                ),
                diffByComponentID = diffByComponentID(
                    ComponentID(1) to Diff(
                        static = Template("Component1Static0")
                    )
                ),
                templateByComponentID = templateByComponentID(
                    ComponentID(1) to Template("Component1Static0")
                )
            ).toString()
        )
    }

    @Test
    fun toStringWithStaticWithoutDynamicsWithSubstitutionByIndexWithDiffByComponentWithTemplateByComponentID() {
        assertEquals(
            """
            Diff(
              static=
                Template("Static0","Static1"),
              substitutionByIndex=
                substitutionByIndex(
                  0 to ComponentSubstitution(ComponentID(1))
                ),
              diffByComponentID=
                diffByComponent(
                  ComponentID(1) to Diff(
                    static=
                      Template("Component1Static0")
                  )
                ),
              templateByComponentID=
                templateByComponentID(
                  ComponentID(1) to Template("Component1Static0")
                )
            )
            """.trimIndent(),
            Diff(
                static = Template("Static0", "Static1"),
                substitutionByIndex = substitutionByIndex(
                    0 to ComponentSubstitution(ComponentID(1))
                ),
                diffByComponentID = diffByComponentID(
                    ComponentID(1) to Diff(
                        static = Template("Component1Static0")
                    )
                ),
                templateByComponentID = templateByComponentID(
                    ComponentID(1) to Template("Component1Static0")
                )
            ).toString()
        )
    }

    @Test
    fun toStringWithoutStaticWithoutDynamicsWithoutSubstitutionByIndexWithDiffByComponentWithTemplateByComponentID() {
        assertEquals(
            """
            Diff(
              diffByComponentID=
                diffByComponent(
                  ComponentID(1) to Diff(
                    static=
                      Template("Component1Static0")
                  )
                ),
              templateByComponentID=
                templateByComponentID(
                  ComponentID(1) to Template("Component1Static0")
                )
            )
            """.trimIndent(),
            Diff(
                diffByComponentID = diffByComponentID(
                    ComponentID(1) to Diff(
                        static = Template("Component1Static0")
                    )
                ),
                templateByComponentID = templateByComponentID(
                    ComponentID(1) to Template("Component1Static0")
                )
            ).toString()
        )
    }

    @Test
    fun toStringWithoutStaticWithoutDynamicsWithoutSubstitutionByIndexWithoutDiffByComponentWithTemplateByComponentID() {
        assertEquals(
            """
            Diff(
              templateByComponentID=
                templateByComponentID(
                  ComponentID(1) to Template("Component1Static0")
                )
            )
            """.trimIndent(),
            Diff(
                templateByComponentID = templateByComponentID(
                    ComponentID(1) to Template("Component1Static0")
                )
            ).toString()
        )
    }

    @Test
    fun toStringWithStaticWithDynamicsWithoutSubstitutionByIndexWithDiffByComponentWithTemplateByComponentID() {
        assertEquals(
            """
            Diff(
              static=
                Template("Static0","Static1"),
              dynamic=
                [[ComponentSubstitution(ComponentID(1))], [Literal("Dynamic2")]],
              diffByComponentID=
                diffByComponent(
                  ComponentID(1) to Diff(
                    static=
                      Template("Component1Static0")
                  )
                ),
              templateByComponentID=
                templateByComponentID(
                  ComponentID(1) to Template("Component1Static0")
                )
            )
            """.trimIndent(),
            Diff(
                static = Template("Static0", "Static1"),
                dynamics = listOf(
                    listOf(ComponentSubstitution(ComponentID(1))),
                    listOf(Literal("Dynamic2")),
                ),
                diffByComponentID = diffByComponentID(
                    ComponentID(1) to Diff(
                        static = Template("Component1Static0")
                    )
                ),
                templateByComponentID = templateByComponentID(
                    ComponentID(1) to Template("Component1Static0")
                )
            ).toString()
        )
    }
}
