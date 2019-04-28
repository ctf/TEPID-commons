package ca.mcgill.science.tepid.models.data

import ca.mcgill.science.tepid.models.bindings.TepidDb

data class SignUp(
        var name: String,
        var givenName: String = name,
        var nickname: String? = null,
        var slots: Map<String, Array<String>> = emptyMap()
) : TepidDb(type="signup")