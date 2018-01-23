package ca.mcgill.science.tepid.utils

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import kotlin.reflect.full.companionObject


/**
 * Created by Allan Wang on 2017-09-29.
 */

/**
 * We will use the benefits of log4j2,
 * but wrap it in a delegate to make it cleaner
 *
 * Typically, classes should have a companion object extending [WithLogging]
 *
 * See <a href="https://stackoverflow.com/a/34462577/4407321">Stack Overflow</a>
 */
fun <T : Any> T.logger() = logger(this.javaClass)

private fun <T : Any> logger(forClass: Class<T>): Logger
        = LogManager.getLogger(unwrapCompanionClass(forClass).name)

// unwrap companion class to enclosing class given a Java Class
private fun <T : Any> unwrapCompanionClass(ofClass: Class<T>): Class<*>
        = if (ofClass.enclosingClass != null && ofClass.enclosingClass.kotlin.companionObject?.java == ofClass)
    ofClass.enclosingClass else ofClass

interface Loggable {
    val log: Logger
}

/**
 * Base implementation of a static final logger
 *
 * Can by used through
 *
 * companion object: WithLogging()
 */
abstract class WithLogging : Loggable {
    override val log: Logger by lazy { this.logger() }
}