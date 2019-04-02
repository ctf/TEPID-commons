package ca.mcgill.science.tepid.models.data

import ca.mcgill.science.tepid.models.bindings.TepidDb
import ca.mcgill.science.tepid.models.bindings.TepidDbDelegate
import javax.persistence.*

@Entity
data class PrintQueue(
        var loadBalancer: String? = null,
        var defaultOn: String? = null,
        var name: String? = null,
        @Access(AccessType.FIELD)
        @ElementCollection(fetch = FetchType.EAGER)
        var destinations: List<String> = emptyList()
) : TepidDb by TepidDbDelegate() {

    override var type: String? = "queue"

    override fun toString(): String {
        return "PrintQueue [name=$name, destinations=$destinations]"
    }
}