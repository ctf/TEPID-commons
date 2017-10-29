package ca.mcgill.science.tepid.models

import ca.mcgill.science.tepid.models.bindings.TepidDb
import ca.mcgill.science.tepid.models.bindings.TepidDbDelegate
import ca.mcgill.science.tepid.models.bindings.TepidExtras
import ca.mcgill.science.tepid.models.bindings.TepidExtrasDelegate
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
