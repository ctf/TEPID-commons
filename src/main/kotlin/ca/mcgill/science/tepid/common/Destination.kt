package ca.mcgill.science.tepid.common

import com.fasterxml.jackson.annotation.*
import java.util.*
import javax.annotation.Generated
import kotlin.collections.HashMap

/**
 * Created by Allan Wang on 2017-05-03.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder("_id", "_rev", "type", "name", "protocol", "username", "password", "path", "domain")
data class Destination(@JsonProperty("_id")
                       var _id: String = "",
                       @JsonProperty("_rev")
                       var _rev: String = "",
                       @JsonProperty("type")
                       var type: String = "",
                       @JsonProperty("name")
                       var name: String = "",
                       @JsonProperty("protocol")
                       var protocol: String? = null,
                       @JsonProperty("username")
                       var username: String? = null,
                       @JsonProperty("password")
                       var password: String? = null,
                       @JsonProperty("path")
                       var path: String? = null,
                       @JsonProperty("domain")
                       var domain: String? = null,
                       @JsonProperty("ticket")
                       var ticket: DestinationTicket? = null,
                       @JsonProperty("up")
                       var up: Boolean = false,
                       @JsonProperty("ppm")
                       var ppm: Int = 0,
                       @get:JsonAnyGetter
                       @JsonIgnore
                       val additionalProperties: HashMap<String, Any> = HashMap()) {

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