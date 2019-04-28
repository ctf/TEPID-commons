package ca.mcgill.science.tepid.models.data

import ca.mcgill.science.tepid.models.bindings.TepidDb

data class EmailReasons(
        var heading: String? = null,
        var body: String? = null
) : TepidDb()