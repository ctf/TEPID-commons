package ca.mcgill.science.tepid.utils

class PropHolder(val propLoaders: List<PropLoader>) : WithLogging(){
    fun get(key:String):String? {
        for (loader in propLoaders){
            loader.get(key)?.let {value -> return value}
        }
        log.warn("Could not load property $key")
        return null
    }

    fun getNonNull(key:String):String {
        return get(key) ?: throw NoSuchElementException("Could not load property $key")
    }
}