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
interface TepidDb : TepidId {
    var _rev: String?
    var type: String?
    var schema: String?

    /*
     * Helper function to retrieve a nonnull rev
     * Defaults to an empty string
     */
    @JsonIgnore
    fun getRev() = _rev ?: ""
    /*
     * Helper function to retrieve default schema version
     * Defaults to 0-0-0
     */
    @JsonIgnore
    fun getSchema() = schema ?: "00-00-00"
}

/**
 * Helper function to inherit db data form a [main] db dataset
 */
fun <T : TepidDb> T.withDbData(main: TepidDb): T {
    withIdData(main)
    _rev = main._rev
    type = main.type
    schema = main.schema
    return this
}

class TepidDbDelegate : TepidDb, TepidId by TepidIdDelegate() {
    override var _rev: String? = null
    override var type: String? = null
    override var schema: String? = null
}

interface TepidId : TepidJackson {
    var _id: String?

    /*
     * Helper function to retrieve a nonnull id
     * Defaults to an empty string
     */
    @JsonIgnore
    fun getId() = _id ?: ""
}

class TepidIdDelegate : TepidId {
    override var _id: String? = null
}

fun <T : TepidId> T.withIdData(main: TepidId): T {
    _id = main._id
    return this
}