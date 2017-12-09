package ca.mcgill.science.tepid.models.data

open class ViewResultSet<K, T> {
    open var rows: List<Row<K, T>> = emptyList()

    open class Row<out K, out T>(open val key: K?, open val value: T?) {
        override fun hashCode() = (key?.hashCode() ?: 13) * 13 + (value?.hashCode() ?: 7)

        override fun equals(other: Any?) =
                if (other !is Row<*, *>) false else key == other.key && value == other.value

        override fun toString() = "($key, $value)"
    }

    fun getValues() = rows.map { it.value }.filter { it != null }

    override fun hashCode() = rows.hashCode()

    override fun equals(other: Any?) =
            if (other !is ViewResultSet<*, *>) false else rows == other.rows

    override fun toString() =
            "ViewResultSet[${rows.joinToString(", ")}]"
}