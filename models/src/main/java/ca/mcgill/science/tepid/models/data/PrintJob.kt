package ca.mcgill.science.tepid.models.data

import ca.mcgill.science.tepid.models.bindings.TepidDb
import ca.mcgill.science.tepid.models.bindings.TepidDbDelegate

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
        val started: Long = System.currentTimeMillis(),
        var processed: Long = -1,
        var printed: Long = -1,
        var failed: Long = -1,
        var received: Long = -1,
        var isRefunded: Boolean = false,
        var eta: Long = 0,
        var deleteDataOn: Long = 0
) : TepidDb by TepidDbDelegate(), Comparable<PrintJob> {

    override var type: String? = "job"

    fun truncateName(length: Int): String {
        return if (name.length > length) name.substring(0, length - 1) + "\u2026" else name
    }

    fun fail(error: String) {
        this.failed = System.currentTimeMillis()
        this.error = error
    }

    private fun dateToCompare() =
            if (failed != -1L) started else processed

    override fun compareTo(other: PrintJob): Int {
        val date1 = dateToCompare()
        val date2 = other.dateToCompare()
        val compare = date1.compareTo(date2)
        if (compare != 0) return compare
        return getId().compareTo(other.getId())
    }

}