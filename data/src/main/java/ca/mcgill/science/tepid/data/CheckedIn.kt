package ca.mcgill.science.tepid.data

import com.fasterxml.jackson.annotation.*

import java.util.HashMap

@JsonInclude(JsonInclude.Include.NON_NULL)
data class CheckedIn @JvmOverloads constructor(
        var _id: String = "",
        var currentCheckIn: Map<String, Array<String>> = HashMap(),
        var lateCheckIns: Map<String, Array<String>> = HashMap(),
        var lateCheckOuts: Map<String, Array<String>> = HashMap(),
        var type: String? = null,
        @get:JsonAnyGetter
        @JsonIgnore
        val additionalProperties: MutableMap<String, Any> = HashMap()
) {
    @JsonAnySetter
    fun setAdditionalProperty(name: String, value: Any) {
        this.additionalProperties.put(name, value)
    }
}
