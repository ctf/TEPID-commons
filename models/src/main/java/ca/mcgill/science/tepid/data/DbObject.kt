package ca.mcgill.science.tepid.data

import ca.mcgill.science.tepid.data.bindings.TepidDb
import ca.mcgill.science.tepid.data.bindings.TepidDbDelegate
import ca.mcgill.science.tepid.data.bindings.TepidExtras
import ca.mcgill.science.tepid.data.bindings.TepidExtrasDelegate
import com.fasterxml.jackson.annotation.*
import com.fasterxml.jackson.annotation.JsonInclude.Include

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class DbObjectJson : TepidDb by TepidDbDelegate(), TepidExtras by TepidExtrasDelegate()