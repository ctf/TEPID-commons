package ca.mcgill.science.tepid.common

import com.fasterxml.jackson.annotation.*
import com.fasterxml.jackson.annotation.JsonInclude.Include

import java.util.Date
import java.util.HashMap

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class PrintJob(
        val type: String = "job",
        var name: String = "",
        var queueName: String? = null,
        var originalHost: String? = null,
        var userIdentification: String? = null,
        var destination: String? = null,
        var error: String? = null,
        var file: String? = null,
        var colorPages: Int = 0,
        var pages: Int = 0,
        val started: Date = Date(),
        var processed: Date? = null,
        var printed: Date? = null,
        var failed: Date? = null,
        var received: Date? = null,
        var isRefunded: Boolean = false,
        var eta: Long = 0,
        var deleteDataOn: Long = 0,
        var _id: String = "",
        var _rev: String = "",
        @get:JsonAnyGetter
        @JsonIgnore
        val additionalProperties: MutableMap<String, Any> = HashMap()
) {

    @JsonAnySetter
    fun setAdditionalProperty(name: String, value: Any) {
        this.additionalProperties.put(name, value)
    }

    fun truncateName(length: Int): String {
        return if (name.length > length) name.substring(0, length - 3) + "..." else name
    }

    fun setFailed(failed: Date, error: String) {
        this.failed = failed
        this.error = error
    }

    override fun toString(): String {
        val builder = StringBuilder()
        builder.append("PrintJob [name=").append(name).append(", queueName=")
                .append(queueName).append(", originalHost=")
                .append(originalHost).append(", userIdentification=")
                .append(userIdentification).append("]")
        return builder.toString()
    }
}
