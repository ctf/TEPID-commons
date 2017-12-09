package ca.mcgill.science.tepid.models

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class About(val isDebug: Boolean, val isLdapEnabled: Boolean,
                        val startTime: String,
                        val hash: String, val warnings: List<String>)