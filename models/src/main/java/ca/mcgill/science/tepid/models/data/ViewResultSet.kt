package ca.mcgill.science.tepid.models.data

open class ViewResultSet<K : Any, V : Any> {
    open var rows: List<Row<K, V>> = emptyList()

    open class Row<out K, out V>(open val key: K?, open val value: V?) {
        override fun hashCode() = (key?.hashCode() ?: 13) * 13 + (value?.hashCode() ?: 7)

        override fun equals(other: Any?) =
                if (other !is Row<*, *>) false else key == other.key && value == other.value

        override fun toString() = "($key, $value)"
    }

    //    Copied from Kotlin 1.2. Remove once ide's are updated
    private fun <T : Any> Iterable<T?>.filterNotNull(): List<T> {
        return filterNotNullTo(ArrayList())
    }

    /**
     * Appends all elements that are not `null` to the given [destination].
     */
    private fun <C : MutableCollection<in T>, T : Any> Iterable<T?>.filterNotNullTo(destination: C): C {
        for (element in this) if (element != null) destination.add(element)
        return destination
    }

    fun getValues() : List<V> = rows.map { it.value }.filterNotNull()

    override fun hashCode() = rows.hashCode()

    override fun equals(other: Any?) =
            if (other !is ViewResultSet<*, *>) false else rows == other.rows

    override fun toString() =
            "ViewResultSet[${rows.joinToString(", ")}]"
}