package ca.mcgill.science.tepid.models

import ca.mcgill.science.tepid.models.bindings.TepidDb
import ca.mcgill.science.tepid.models.bindings.TepidDbDelegate
import ca.mcgill.science.tepid.models.bindings.TepidExtras
import ca.mcgill.science.tepid.models.bindings.TepidExtrasDelegate
import com.fasterxml.jackson.annotation.*
import com.fasterxml.jackson.annotation.JsonInclude.Include
import java.util.*

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class PrintJobJson(
        var name: String = "",
        var queueName: String? = null,
        var originalHost: String? = null,
        var userIdentification: String? = null,
        var destination: String? = null,
        var error: String? = null,
        var file: String? = null,
        var colorPages: Int = 0,
        var pages: Int = 0,
        val started: Date = Date(),
        var processed: Date? = null,
        var printed: Date? = null,
        var failed: Date? = null,
        var received: Date? = null,
        var isRefunded: Boolean = false,
        var eta: Long = 0,
        var deleteDataOn: Long = 0
) : TepidDb by TepidDbDelegate(), TepidExtras by TepidExtrasDelegate() {

    override var type: String? = "job"

    fun truncateName(length: Int): String {
        return if (name.length > length) name.substring(0, length - 3) + "..." else name
    }

    fun setFailed(failed: Date, error: String) {
        this.failed = failed
        this.error = error
    }
}

data class PrintJob(
        val name: String,
        val queueName: String?,
        val originalHost: String?,
        val userIdentification: String?,
        val destination: String?,
        val error: String?,
        val file: String?,
        val colorPages: Int,
        val pages: Int,
        val started: Date,
        val processed: Date?,
        val printed: Date?,
        val failed: Date?,
        val received: Date?,
        val isRefunded: Boolean,
        val eta: Long,
        val deleteDataOn: Long = 0
)