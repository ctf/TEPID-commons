package ca.mcgill.science.tepid.models

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class About(val debug: Boolean, val ldapEnabled: Boolean,
                 val startTime: String = "",
                 val hash: String = "", val warnings: List<String> = emptyList())