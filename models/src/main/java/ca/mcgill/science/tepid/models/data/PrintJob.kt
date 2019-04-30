package ca.mcgill.science.tepid.models.data

import ca.mcgill.science.tepid.models.bindings.TepidDb
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.*

@Entity
data class PrintJob(
        var name: String = "",
        var queueName: String? = null,
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
        @JsonProperty(value="isRefunded")
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