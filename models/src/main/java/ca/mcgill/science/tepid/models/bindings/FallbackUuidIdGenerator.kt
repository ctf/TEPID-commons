package ca.mcgill.science.tepid.models.bindings

import org.hibernate.engine.spi.SharedSessionContractImplementor
import org.hibernate.id.IdentifierGenerator
import java.io.Serializable
import java.util.*

/**
 * Generates a UUID for the ID for a TepidDb object if it doesn't already have one
 * referenced through reflection
 */

class FallbackUuidIdGenerator : IdentifierGenerator {
    override fun generate(session: SharedSessionContractImplementor?, `object`: Any?): Serializable {
        val obj = `object` as TepidDb
        return obj._id ?: UUID.randomUUID().toString()
    }
}