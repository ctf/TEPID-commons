package ca.mcgill.science.tepid.models.data

import ca.mcgill.science.tepid.models.bindings.TepidDb
import ca.mcgill.science.tepid.models.bindings.TepidDbDelegate
import ca.mcgill.science.tepid.models.bindings.TepidExtras
import ca.mcgill.science.tepid.models.bindings.TepidExtrasDelegate

/**
 * Created by Allan Wang on 2017-05-03.
 */
data class EmailReasons(
        var heading: String? = null,
        var body: String? = null
) : TepidDb by TepidDbDelegate(), TepidExtras by TepidExtrasDelegate()