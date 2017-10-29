package ca.mcgill.science.tepid.data

import ca.mcgill.science.tepid.data.bindings.TepidDb
import ca.mcgill.science.tepid.data.bindings.TepidDbDelegate
import ca.mcgill.science.tepid.data.bindings.TepidExtras
import ca.mcgill.science.tepid.data.bindings.TepidExtrasDelegate
import com.fasterxml.jackson.annotation.JsonInclude

/**
 * Created by Allan Wang on 2017-05-03.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
data class EmailReasonsJson(
        var heading: String? = null,
        var body: String? = null
) : TepidDb by TepidDbDelegate(), TepidExtras by TepidExtrasDelegate()


data class EmailReasons(val heading: String?, val body: String?)
