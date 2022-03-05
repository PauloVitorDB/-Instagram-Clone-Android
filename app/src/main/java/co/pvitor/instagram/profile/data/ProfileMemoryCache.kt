package co.pvitor.instagram.profile.data

import co.pvitor.instagram.common.model.LocalCache
import co.pvitor.instagram.common.model.User
import co.pvitor.instagram.common.model.UserAuth

object ProfileMemoryCache : LocalCache<Pair<User, Boolean?>> {

    private var userAuth: Pair<User, Boolean?>? = null

    override fun isCached(): Boolean {
        return userAuth != null
    }

    override fun get(key: String?) : Pair<User, Boolean?>? {
        if(userAuth?.first?.uuid == key) {
            return userAuth
        }
        return null
    }

    override fun set(data: Pair<User, Boolean?>?) {
        userAuth = data
    }

}