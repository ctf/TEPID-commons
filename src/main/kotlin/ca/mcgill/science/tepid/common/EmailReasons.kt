package ca.mcgill.science.tepid.common

import com.fasterxml.jackson.annotation.*
import java.util.HashMap

/**
 * Created by Allan Wang on 2017-05-03.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("_id", "_rev", "type", "heading", "body")
data class EmailReasons(
        @JsonProperty("_id")
        var id: String = "",
        @JsonProperty("_rev")
        var rev: String? = null,
        @JsonProperty("type")
        var type: String? = null,
        @JsonProperty("heading")
        var heading: String? = null,
        @JsonProperty("body")
        var body: String? = null,
        @get:JsonAnyGetter
        @JsonIgnore
        val additionalProperties: HashMap<String, Any> = HashMap()
) {

    @JsonAnySetter
    fun setAdditionalProperty(name: String, value: Any) {
        this.additionalProperties.put(name, value)
    }

}