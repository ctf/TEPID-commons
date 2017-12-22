package ca.mcgill.science.tepid.models.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

/**
 * Collection of rows containing values
 */
@JsonInclude(JsonInclude.Include.ALWAYS)
@JsonIgnoreProperties(ignoreUnknown = true)
open class ViewResultSet<V : Any> {
    open var rows: List<Row<V>> = emptyList()

    @JsonInclude(JsonInclude.Include.ALWAYS)
    @JsonIgnoreProperties(ignoreUnknown = true)
    open class Row<out V>(open val value: V?) {
        override fun hashCode() = value?.hashCode() ?: 7

        override fun equals(other: Any?) =
                if (other !is Row<*>) false else value == other.value

        override fun toString() = value?.toString() ?: "null"
    }

    fun getValues(): List<V> = rows.mapNotNull { it.value }

    override fun hashCode() = rows.hashCode()

    override fun equals(other: Any?) =
            if (other !is ViewResultSet<*>) false else rows == other.rows

    override fun toString() =
            "ViewResultSet[${rows.joinToString(", ")}]"
}

/**
 * Collection of rows containing keys and values
 */
@JsonInclude(JsonInclude.Include.ALWAYS)
@JsonIgnoreProperties(ignoreUnknown = true)
open class ViewResultMap<K : Any, V : Any> {
    open var rows: List<Row<K, V>> = emptyList()

    @JsonInclude(JsonInclude.Include.ALWAYS)
    @JsonIgnoreProperties(ignoreUnknown = true)
    open class Row<out K, out V>(open val key: K?, open val value: V?) {
        override fun hashCode() = (key?.hashCode() ?: 13) * 13 + (value?.hashCode() ?: 7)

        override fun equals(other: Any?) =
                if (other !is Row<*, *>) false else key == other.key && value == other.value

        override fun toString() = "($key, $value)"
    }

    fun getValues(): List<V> = rows.mapNotNull { it.value }

    override fun hashCode() = rows.hashCode()

    override fun equals(other: Any?) =
            if (other !is ViewResultMap<*, *>) false else rows == other.rows

    override fun toString() =
            "ViewResultMap[${rows.joinToString(", ")}]"
}