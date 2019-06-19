package ca.mcgill.science.tepid.models.data

import ca.mcgill.science.tepid.models.bindings.TepidDb
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import javax.persistence.*

@Entity
data class PrintQueue(
        var loadBalancer: String? = null,
        var defaultOn: String? = null,
        var name: String? = null,
        @Access(AccessType.FIELD)
        @ElementCollection(fetch = FetchType.EAGER)
        @Fetch(value=FetchMode.SELECT)
        var destinations: List<String> = mutableListOf()
) : TepidDb(type="queue") {
    override fun toString(): String {
        return "PrintQueue [name=$name, destinations=$destinations]"
    }
}