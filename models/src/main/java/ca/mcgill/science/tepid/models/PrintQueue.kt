package ca.mcgill.science.tepid.models

import ca.mcgill.science.tepid.models.bindings.TepidDb
import ca.mcgill.science.tepid.models.bindings.TepidDbDelegate
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class PrintQueue(
        var loadBalancer: String? = null,
        var defaultOn: String? = null,
        var name: String? = null,
        var destinations: List<String> = emptyList()
) : TepidDb by TepidDbDelegate() {

    override var type: String? = "queue"

    override fun toString(): String {
        return "PrintQueue [name=$name, destinations=$destinations]"
    }
}