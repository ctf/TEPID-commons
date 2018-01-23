package ca.mcgill.science.tepid.models.data

import ca.mcgill.science.tepid.models.bindings.TepidDb
import ca.mcgill.science.tepid.models.bindings.TepidDbDelegate
import ca.mcgill.science.tepid.models.bindings.TepidExtras
import ca.mcgill.science.tepid.models.bindings.TepidExtrasDelegate

data class SignUp(
        var name: String,
        var givenName: String = name,
        var nickname: String? = null,
        var slots: Map<String, Array<String>> = emptyMap()
) : TepidDb by TepidDbDelegate(), TepidExtras by TepidExtrasDelegate()