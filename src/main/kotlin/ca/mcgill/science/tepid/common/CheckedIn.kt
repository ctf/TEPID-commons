package ca.mcgill.science.tepid.common

import com.fasterxml.jackson.annotation.*

import java.util.HashMap

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("_id", "type", "currentCheckIn", "lateCheckIns", "lateCheckOuts")
data class CheckedIn(
        @JsonProperty("_id")
        var id: String = "",
        @JsonProperty("currentCheckIn")
        var currentCheckIn: Map<String, Array<String>> = HashMap(),
        @JsonProperty("lateCheckIns")
        var lateCheckIns: Map<String, Array<String>> = HashMap(),
        @JsonProperty("lateCheckOuts")
        var lateCheckOuts: Map<String, Array<String>> = HashMap(),
        @JsonProperty("type")
        var type: String? = null,
        @get:JsonAnyGetter
        @JsonIgnore
        val additionalProperties: HashMap<String, Any> = HashMap()
) {

    @JsonAnySetter
    fun setAdditionalProperty(name: String, value: Any) {
        this.additionalProperties.put(name, value)
    }

}
