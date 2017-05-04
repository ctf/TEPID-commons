package ca.mcgill.science.tepid.common

import com.fasterxml.jackson.annotation.*
import com.fasterxml.jackson.annotation.JsonInclude.Include

import java.util.HashMap

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class DbObject(
        var id: String? = null,
        var rev: String? = null,
        @get:JsonAnyGetter
        @JsonIgnore
        val additionalProperties: HashMap<String, Any> = HashMap<String, Any>()
) {

    @JsonAnySetter
    fun setAdditionalProperty(name: String, value: Any) {
        this.additionalProperties.put(name, value)
    }

}
