package ca.mcgill.science.tepid.models.data

import ca.mcgill.science.tepid.models.bindings.TepidDb
import ca.mcgill.science.tepid.models.enums.PrintError
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.Access
import javax.persistence.AccessType
import javax.persistence.Entity
import javax.persistence.Transient

@Entity
data class PrintJob(
        var name: String = "",
        var queueId: String? = null,
        var originalHost: String? = null,
        var userIdentification: String? = null,
        var destination: String? = null,
        var error: String? = null,
        var file: String? = null,
        var colorPages: Int = 0,
        var pages: Int = 0,
        @Access(AccessType.FIELD)
        val started: Long = System.currentTimeMillis(),
        var processed: Long = -1,
        var printed: Long = -1,
        var failed: Long = -1,
        var received: Long = -1,
        @Access(AccessType.FIELD)
        @get:JsonProperty(value="refunded")
        var isRefunded: Boolean = false,
        var eta: Long = 0,
        var deleteDataOn: Long = 0
) : TepidDb(type = "job"), Comparable<PrintJob> {



    fun truncateName(length: Int): String {
        return if (name.length > length) name.substring(0, length - 1) + "\u2026" else name
    }

    fun fail(error: String) {
        this.failed = System.currentTimeMillis()
        this.error = error
    }

    fun fail(error: PrintError) {
        fail(error.display)
    }

    val displayDate: Long
        @Transient
        @JsonIgnore
        get() = when {
            failed != -1L -> failed
            processed != -1L -> processed
            else -> started
        }

    override fun compareTo(other: PrintJob): Int {
        val date1 = displayDate
        val date2 = other.displayDate
        val compare = date1.compareTo(date2)
        if (compare != 0) return compare
        return getId().compareTo(other.getId())
    }

}