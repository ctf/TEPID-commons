package ca.mcgill.science.tepid.utils

open class PropHolder(propLoaders: List<PropLoader>) : WithLogging() {
    val propLoaders: List<PropLoader> by lazy { propLoaders }
    fun get(key: String): Lazy<String?> {
        for (loader in propLoaders) {
            loader.get(key)?.let { value -> return lazy { value } }
        }
        log.warn("Could not load property $key")
        return lazy { null }
    }

    fun getNonNull(key: String): Lazy<String> {
        return lazy { this.get(key).value ?: throw NoSuchElementException("Could not load property $key") }
    }

    fun getInt(key: String): Lazy<Int?> {
        return lazy { this.get(key).value?.toInt() }
    }
}