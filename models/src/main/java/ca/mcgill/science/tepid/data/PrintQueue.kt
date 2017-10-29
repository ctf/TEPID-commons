package ca.mcgill.science.tepid.data

import ca.mcgill.science.tepid.data.bindings.TepidDb
import ca.mcgill.science.tepid.data.bindings.TepidDbDelegate
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class PrintQueueJson(
        var loadBalancer: String? = null,
        var defaultOn: String? = null,
        var name: String? = null,
        var destinations: List<String>? = null
) : TepidDb by TepidDbDelegate() {

    override var type: String? = "queue"

    override fun toString(): String {
        return "PrintQueue [name=$name, destinations=$destinations]"
    }
}

data class PrintQueue(
        val loadBalancer: String?, val defaultOn: String?, val name: String?, val destinations: List<String>?
)
