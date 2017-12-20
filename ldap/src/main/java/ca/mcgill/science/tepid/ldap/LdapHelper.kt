package ca.mcgill.science.tepid.ldap

import ca.mcgill.science.tepid.models.data.FullUser
import javax.naming.directory.Attribute
import javax.naming.directory.Attributes

/**
 * Some helper functions for ldap
 */
interface LdapHelperContract {
    fun FullUser.mergeWith(other: FullUser?)
    fun Attributes.toList(): List<Attribute>
    fun Attribute.toList(): List<String>
}

class LdapHelperDelegate : LdapHelperContract {
    /**
     * Copy over attributes from another user
     */
    override fun FullUser.mergeWith(other: FullUser?) {
        other ?: return
        if (_id == "") _id = other._id
        _rev = _rev ?: other._rev
        studentId = other.studentId
        preferredName = other.preferredName
        nick = nick ?: other.nick
        colorPrinting = other.colorPrinting
        jobExpiration = other.jobExpiration
        shortUser = shortUser ?: other.shortUser
        if (studentId <= 0) studentId == other.studentId
    }

    /**
     * Convert attributes to attribute list
     */
    override fun Attributes.toList(): List<Attribute> {
        val ids = iDs
        val data = mutableListOf<Attribute>()
        while (ids.hasMore()) {
            val id = ids.next()
            data.add(get(id))
        }
        ids.close()
        return (data)
    }

    /**
     * Convert attribute to string list
     */
    override fun Attribute.toList() = (0 until size()).map { get(it).toString() }
}