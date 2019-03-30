package ca.mcgill.science.tepid

/**
 * Some common dependencies, backed by the supplied versions
 */
class Dependencies {
    static def rxJava = "io.reactivex.rxjava2:rxjava:${Versions.rxJava}"
    static def rxKotlin = "io.reactivex.rxjava2:rxkotlin:${Versions.rxKotlin}"
    static def kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    static def kotlinTest = "org.jetbrains.kotlin:kotlin-test-junit5:${Versions.kotlin}"
    static def junit = ["org.junit.jupiter:junit-jupiter-engine:${Versions.junit}", "org.junit.jupiter:junit-jupiter-api:${Versions.junit}"]
}