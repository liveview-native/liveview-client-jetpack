package com.dockyard.liveviewtest.android.external

@JvmInline
value class ComponentSubstitution(private val componentID: ComponentID) : Substitution {
    // https://github.com/phoenixframework/phoenix_live_view/blob/05785682e439a3628305e7c47f6d533072604acf/lib/phoenix_live_view/diff.ex#L59-L64
    override fun toIOData(
        diffByComponentID: DiffByComponentID,
        templateByComponentID: TemplateByComponentID?,
        componentMapper: (ComponentID, IOData) -> IOData
    ): Pair<IOData, DiffByComponentID> {
        val accDiffByComponentID = diffByComponentID.resolveComponentsReference(componentID)
        val (ioData, finalDiffByComponentID) = diffByComponentID[componentID]!!.toIOData(
            accDiffByComponentID,
            templateByComponentID,
            componentMapper
        )

        return Pair(componentMapper(componentID, ioData), finalDiffByComponentID)
    }

    override fun toString(): String = "ComponentSubstitution($componentID)"
}
