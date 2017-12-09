package ca.mcgill.science.tepid.models

import ca.mcgill.science.tepid.models.bindings.TepidDb
import ca.mcgill.science.tepid.models.bindings.TepidDbDelegate
import ca.mcgill.science.tepid.models.bindings.TepidExtras
import ca.mcgill.science.tepid.models.bindings.TepidExtrasDelegate
import com.fasterxml.jackson.annotation.*

import java.util.HashMap

@JsonInclude(JsonInclude.Include.NON_NULL)
data class CheckedIn(
        var currentCheckIn: Map<String, Array<String>> = emptyMap(),
        var lateCheckIns: Map<String, Array<String>> = emptyMap(),
        var lateCheckOuts: Map<String, Array<String>> = emptyMap()
) : TepidDb by TepidDbDelegate(), TepidExtras by TepidExtrasDelegate()
