package ca.mcgill.science.tepid.common

import com.fasterxml.jackson.annotation.*
import java.util.HashMap

/**
 * Created by Allan Wang on 2017-05-03.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("_id", "_rev", "type", "heading", "body")
data class EmailReasons(
        var _id: String = "",
        var _rev: String? = null,
        var type: String? = null,
        var heading: String? = null,
        var body: String? = null,
        @get:JsonAnyGetter
        @JsonIgnore
        val additionalProperties: MutableMap<String, Any> = HashMap()
) {

    @JsonAnySetter
    fun setAdditionalProperty(name: String, value: Any) {
        this.additionalProperties.put(name, value)
    }

}