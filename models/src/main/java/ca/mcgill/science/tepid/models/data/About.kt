package ca.mcgill.science.tepid.models.data

import ca.mcgill.science.tepid.models.bindings.TepidJackson

data class About(val debug: Boolean, val ldapEnabled: Boolean,
                 val startTime: String = "",
                 val hash: String = "", val warnings: List<String> = emptyList()) : TepidJackson