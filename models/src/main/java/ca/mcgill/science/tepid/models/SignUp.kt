package ca.mcgill.science.tepid.models

import ca.mcgill.science.tepid.models.bindings.TepidDb
import ca.mcgill.science.tepid.models.bindings.TepidDbDelegate
import ca.mcgill.science.tepid.models.bindings.TepidExtras
import ca.mcgill.science.tepid.models.bindings.TepidExtrasDelegate
import com.fasterxml.jackson.annotation.*
import com.fasterxml.jackson.annotation.JsonInclude.Include

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class SignUpJson(
        var name: String,
        var givenName: String = name,
        var nickname: String? = null,
        var slots: Map<String, Array<String>>? = null
) : TepidDb by TepidDbDelegate(), TepidExtras by TepidExtrasDelegate()

data class SignUp(val name: String, val givenName: String, val nickname: String?, val slots: Map<String, Array<String>>)