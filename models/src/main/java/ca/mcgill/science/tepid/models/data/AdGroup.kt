package ca.mcgill.science.tepid.models.data

import javax.persistence.Embeddable
import javax.persistence.Id

@Embeddable
data class AdGroup(
        val name: String
) {
}