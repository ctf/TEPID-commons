package ca.mcgill.science.tepid.models.bindings

import org.hibernate.engine.spi.SharedSessionContractImplementor
import org.hibernate.id.IdentifierGenerator
import java.io.Serializable
import java.util.*

class FallbackUuidIdGenerator : IdentifierGenerator {
    override fun generate(session: SharedSessionContractImplementor?, `object`: Any?): Serializable {
        val obj = `object` as TepidDb
        return obj._id ?: UUID.randomUUID().toString()
    }
}