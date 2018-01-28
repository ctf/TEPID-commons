package ca.mcgill.science.tepid.models.bindings

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

/**
 * Created by Allan Wang on 2017-10-29.
 *
 * Base variables that are in all db related classes
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
interface TepidJackson

/**
 * Base db attributes for Couch
 * It is important to note that these values are null by default
 * As our jackson configs will ignore null values, it will not save the attributes
 * if none is provided
 *
 * See [withDbData] for an easy way to propagate these attributes
 *
 * todo see if type is needed
 */
interface TepidDb : TepidJackson {
    var _id: String?
    var _rev: String?
    var type: String?

    /*
     * Helper function to retrieve a nonnull id
     * Defaults to an empty string
     */
    @JsonIgnore
    fun getId() = _id ?: ""

    /*
     * Helper function to retrieve a nonnull rev
     * Defaults to an empty string
     */
    @JsonIgnore
    fun getRev() = _rev ?: ""
}

/**
 * Helper function to inherit db data form a [main] db dataset
 */
fun <T : TepidDb> T.withDbData(main: TepidDb): T {
    _id = main._id
    _rev = main._rev
    type = main.type
    return this
}

class TepidDbDelegate : TepidDb {
    override var _id: String? = null
    override var _rev: String? = null
    override var type: String? = null
}