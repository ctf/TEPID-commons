package ca.mcgill.science.tepid.test

import kotlin.test.fail

/**
 * Given two variables with the same type, map their respective values and compare them individually
 */
fun <T> T.assertComponentsEqual(expected: T, values: (T) -> List<Any?>): T {
    val expectedValues = values(expected)
    val actualValues = values(this)
    var valid = true
    expectedValues.zip(actualValues).forEachIndexed { i, (exp, act) ->
        if (act != exp) {
            valid = false
            System.err.println("Component $i mismatch: expected $exp, actually $act")
        }
    }
    if (!valid) fail("Not all components matched")
    return this
}

/**
 * Given one variable, make its components to expected values
 */
fun <T> T.assertComponentsEqual(value: T.() -> List<Pair<Any?, Any?>>): T {
    var valid = true
    this.value().forEachIndexed { index, (act, exp) ->
        if (act != exp) {
            valid = false
            System.err.println("Component $index mismatch: expected $exp, actually $act")
        }
    }
    if (!valid) fail("Not all components matched")
    return this
}