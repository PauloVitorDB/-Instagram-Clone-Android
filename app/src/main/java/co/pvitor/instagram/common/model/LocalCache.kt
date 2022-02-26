package co.pvitor.instagram.common.model

interface LocalCache<T> {

    fun isCached(): Boolean
    fun set(data: T?)
    fun get(key: String?): T?

}