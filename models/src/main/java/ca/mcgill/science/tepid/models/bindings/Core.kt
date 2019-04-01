package ca.mcgill.science.tepid.models.bindings

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import org.hibernate.annotations.GenericGenerator
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Transient

/**
 * DB Metadata common to all db related classes
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
    fun setRev(value: String?) { _rev = value} // necessary for Hibernate, since this is a property with backing field
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

@Embeddable
/** Kotlin and Hibernate don't play nice
 *  Kotlin cannot have an interface with an actual field, only properties
 *  This is a problem, since the implementations of the interface must define the field
 *  And so getting Hibernate to understand this situation is confusing
 *
 *  The resolution is to
 *  1. Provide full getters and setters for the ID, which is not a Property of the interface.
 *      These may use a Property of the interface.
 *      Remember that the interface does not (because it cannot) have fields
 *  2. Place the annotations on these getters
 *  3. The Property which cannot have a field in the interface will have a Field in the implementation, so we have to mark the Field as transient.
 *      However, since the Field  doesn't actually exist (only the autogen getters and setters) we have to place the annotation on the getter of the Property
 */
interface TepidId : TepidJackson {
    @get:Transient
    var _id: String?

    /*
     * Helper function to retrieve a nonnull id
     * Defaults to an empty string
     */
    @JsonIgnore
    @Id
    @Column(columnDefinition = "char(36) default 'undefined'")
    fun getId() = _id ?: ""
    fun setId(value: String?) {println("▄▄▄▄TRYING TO SET ID▄▄▄▄ $value"); _id=value}
}

class TepidIdDelegate : TepidId {
//    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(
//            name = "UUID",
//    strategy = "org.hibernate.id.UUIDGenerator"
//    )
    override var _id: String? = null
}

fun <T : TepidId> T.withIdData(main: TepidId): T {
    _id = main._id
    return this
}