package ca.mcgill.science.tepid.models.data

import ca.mcgill.science.tepid.models.bindings.TepidDb

data class CheckedIn(
        var currentCheckIn: Map<String, Array<String>> = emptyMap(),
        var lateCheckIns: Map<String, Array<String>> = emptyMap(),
        var lateCheckOuts: Map<String, Array<String>> = emptyMap()
) : TepidDb ()
