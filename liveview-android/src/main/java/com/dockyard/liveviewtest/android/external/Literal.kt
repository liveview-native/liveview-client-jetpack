package com.dockyard.liveviewtest.android.external

import org.apache.commons.text.StringEscapeUtils

data class Literal(val literal: String) : Substitution {
    // https://github.com/phoenixframework/phoenix_live_view/blob/05785682e439a3628305e7c47f6d533072604acf/lib/phoenix_live_view/diff.ex#L66-L68
    override fun toIOData(
        diffByComponentID: DiffByComponentID,
        templateByComponentID: TemplateByComponentID?,
        componentMapper: (ComponentID, IOData) -> IOData
    ): Pair<IOData, DiffByComponentID> = Pair(ioData(literal), diffByComponentID)

    override fun toString(): String = "Literal(\"${StringEscapeUtils.escapeJava(literal)}\")"
}
