package ca.mcgill.science.tepid.api

import ca.mcgill.science.tepid.api.internal.api
import ca.mcgill.science.tepid.api.internal.apiUnauth
import ca.mcgill.science.tepid.api.internal.executeExpectingError
import ca.mcgill.science.tepid.api.internal.executeTest
import ca.mcgill.science.tepid.models.enums.PrinterId
import org.junit.Test

class PrinterInfoTest {

    @Test
    fun get() {
        val data = api.getPrinterInfo().executeTest()
        assert(data.size == PrinterId.values.size) {
            "Not all printers are mapped"
        }
        val names = data.map { it.value.name }.toSet()
        PrinterId.values.forEach {
            assert(names.contains(it.toString())) {
                "PrinterId.toString() should reflect actual name; did not find $it"
            }
        }
        data.map { it.value }.forEach {
            assert((it.ticket == null) == it.up) {
                "Printers that are up should not have tickets, and printers that are down should have tickets"
            }
            assert(it.domain == "***REMOVED***")
        }
    }

    @Test
    fun unauth() {
        apiUnauth.getPrinterInfo().executeExpectingError(401)
    }
}