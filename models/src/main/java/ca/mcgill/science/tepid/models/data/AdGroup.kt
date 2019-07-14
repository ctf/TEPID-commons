package ca.mcgill.science.tepid.models.data

import ca.mcgill.science.tepid.models.bindings.TepidDb
import javax.persistence.Entity

@Entity
data class AdGroup(
    val name: String
) : TepidDb(type="group") {

}