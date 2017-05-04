package ca.mcgill.science.tepid.common

import com.fasterxml.jackson.annotation.*
import com.fasterxml.jackson.annotation.JsonInclude.Include

import java.util.HashMap

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class SignUp(
        var _id: String = "",
        var _rev: String = "",
        var type: String? = null,
        var name: String? = null,
        var givenName: String? = null,
        var nickname: String? = null,
        var slots: Map<String, Array<String>>? = null,
        @get:JsonAnyGetter
        @JsonIgnore
        val additionalProperties: MutableMap<String, Any> = HashMap()
) {
    @JsonAnySetter
    fun setAdditionalProperty(name: String, value: Any) {
        this.additionalProperties.put(name, value)
    }
}
