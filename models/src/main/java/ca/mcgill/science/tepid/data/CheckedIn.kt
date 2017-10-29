package ca.mcgill.science.tepid.data

import ca.mcgill.science.tepid.data.bindings.TepidDb
import ca.mcgill.science.tepid.data.bindings.TepidDbDelegate
import ca.mcgill.science.tepid.data.bindings.TepidExtras
import ca.mcgill.science.tepid.data.bindings.TepidExtrasDelegate
import com.fasterxml.jackson.annotation.*

import java.util.HashMap

@JsonInclude(JsonInclude.Include.NON_NULL)
data class CheckedInJson(
        var currentCheckIn: MutableMap<String, Array<String>> = HashMap(),
        var lateCheckIns: MutableMap<String, Array<String>> = HashMap(),
        var lateCheckOuts: MutableMap<String, Array<String>> = HashMap()
) : TepidDb by TepidDbDelegate(), TepidExtras by TepidExtrasDelegate()

data class CheckedIn(
        val currentCheckIn: Map<String, Array<String>>,
        val lateCheckIns: Map<String, Array<String>>,
        val lateCheckOuts: Map<String, Array<String>>
)