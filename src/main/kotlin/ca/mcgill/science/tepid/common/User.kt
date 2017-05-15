package ca.mcgill.science.tepid.common

import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by Allan Wang on 2017-05-14.
 *
 * BEWARE that the password is printed in user.toString(). It's also a public variable...
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class User(
        var _id: String = "",
        val _rev: String = "",
        var displayName: String? = null,
        var givenName: String? = null,
        var middleName: String? = null,
        var lastName: String? = null,
        var shortUser: String? = null,
        var longUser: String? = null,
        var email: String? = null,
        var faculty: String? = null,
        var nick: String? = null,
        var realName: String? = null,
        var salutation: String? = null,
        var authType: String? = null,
        var role: String? = null,
        var password: String? = null,
        var groups: List<String> = ArrayList<String>(),
        var preferredName: List<String>? = null,
        var activeSince: Date? = null,
        var studentId: Int? = null,
        var jobExpiration: Long = TimeUnit.DAYS.toMillis(7),
        var colorPrinting: Boolean = false,
        val type: String = "user",
        @get:JsonAnyGetter
        @JsonIgnore
        val additionalProperties: MutableMap<String, Any> = HashMap()
) {
    @JsonAnySetter
    fun setAdditionalProperty(name: String, value: Any) {
        this.additionalProperties.put(name, value)
    }

}