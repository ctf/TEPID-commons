package ca.mcgill.science.tepid.data

import ca.mcgill.science.tepid.data.internal.TepidDb
import ca.mcgill.science.tepid.data.internal.TepidDbDelegate
import com.fasterxml.jackson.annotation.*
import com.fasterxml.jackson.annotation.JsonInclude.Include

import java.util.HashMap

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class DbObject (
        @get:JsonAnyGetter
        @JsonIgnore
        val additionalProperties: MutableMap<String, Any> = HashMap<String, Any>()
): TepidDb by TepidDbDelegate() {

    @JsonAnySetter
    fun setAdditionalProperty(name: String, value: Any) {
        this.additionalProperties.put(name, value)
    }

}
