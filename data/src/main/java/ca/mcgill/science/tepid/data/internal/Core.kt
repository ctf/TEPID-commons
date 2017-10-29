package ca.mcgill.science.tepid.data.internal

import com.fasterxml.jackson.annotation.*
import java.util.HashMap

/**
 * Created by Allan Wang on 2017-10-29.
 *
 * Base variables that are in all db related classes
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
internal interface TepidDb {
    var _id: String
    var rev: String?
    var type: String?
}

internal class TepidDbDelegate : TepidDb {
    override var _id: String = ""
    override var rev: String? = null
    override var type: String? = null
}

internal interface TepidExtras {
    val additionalProperties: MutableMap<String, Any>
    fun setAdditionalProperty(name: String, value: Any)
}

internal class TepidExtrasDelegate : TepidExtras {
    @get:JsonAnyGetter
    @JsonIgnore
    override val additionalProperties: MutableMap<String, Any> = HashMap()

    @JsonAnySetter
    override fun setAdditionalProperty(name: String, value: Any) {
        this.additionalProperties.put(name, value)
    }
}