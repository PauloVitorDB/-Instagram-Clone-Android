package co.pvitor.instagram.home.data

interface HomeCache<C> {

    fun get(key: String): C?

    fun set(data: C)

    fun isCached(): Boolean

}