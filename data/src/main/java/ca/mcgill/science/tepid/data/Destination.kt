package ca.mcgill.science.tepid.data

import com.fasterxml.jackson.annotation.*
import java.util.*
import kotlin.collections.HashMap

/**
 * Created by Allan Wang on 2017-05-03.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Destination @JvmOverloads constructor(var _id: String = "",
                       var _rev: String = "",
                       var type: String = "",
                       var name: String = "",
                       var protocol: String? = null,
                       var username: String? = null,
                       var password: String? = null,
                       var path: String? = null,
                       var domain: String? = null,
                       var ticket: DestinationTicket? = null,
                       var up: Boolean = false,
                       var ppm: Int = 0,
                       @get:JsonAnyGetter
                       @JsonIgnore
                       val additionalProperties: MutableMap<String, Any> = HashMap()) {

    @JsonAnySetter
    fun setAdditionalProperty(name: String, value: Any) {
        this.additionalProperties.put(name, value)
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    companion object DestinationTicket {
        var up: Boolean = false
        var reason: String? = null
        var user: User? = null
        var reported: Date = Date()
    }

}