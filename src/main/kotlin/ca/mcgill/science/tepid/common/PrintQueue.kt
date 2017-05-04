package ca.mcgill.science.tepid.common

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class PrintQueue(
        val type: String = "queue",
        var _id: String = "",
        var _rev: String = "",
        var loadBalancer: String? = null,
        var defaultOn: String? = null,
        var name: String? = null,
        var destinations: List<String>? = null
) {
    override fun toString(): String {
        return "PrintQueue [name=$name, destinations=$destinations]"
    }
}
