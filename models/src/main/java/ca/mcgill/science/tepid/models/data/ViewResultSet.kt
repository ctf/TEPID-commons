package ca.mcgill.science.tepid.models.data

data class ViewResultSet<K, T>(var rows: List<Row<K, T>> = emptyList()) {
    data class Row<out K, out T>(val key: K?, val value: T?)

    fun getValues() = rows.map { it.value }.filter { it != null }
}