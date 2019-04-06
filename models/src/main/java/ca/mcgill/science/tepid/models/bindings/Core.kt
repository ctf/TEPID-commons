package ca.mcgill.science.tepid.models.bindings

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import org.hibernate.annotations.GenericGenerator
import javax.persistence.*

/**
 * DB Metadata common to all db related classes
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
interface TepidJackson

@MappedSuperclass
abstract class TepidId : TepidJackson {
    @Id
    @Column(columnDefinition = "char(36) default 'undefined'")
    var _id: String? = null

    /*
     * Helper function to retrieve a nonnull id
     * Defaults to an empty string
     */
    @JsonIgnore
    @Transient
    fun getId() = _id ?: ""
}

class TepidIdDelegate : TepidId() {
    //    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(
//            name = "UUID",
//    strategy = "org.hibernate.id.UUIDGenerator"
//    )
}

fun <T : TepidId> T.withIdData(main: TepidId): T {
    _id = main._id
    return this
}

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
@MappedSuperclass
abstract class TepidDb : TepidId() {
    var _rev: String? = null
    var type: String? = null
    var schema: String? = null

    /*
     * Helper function to retrieve a nonnull rev
     * Defaults to an empty string
     */
    @JsonIgnore
    @Transient
    fun getRev() = _rev ?: ""
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

class TepidDbDelegate : TepidDb() {

}