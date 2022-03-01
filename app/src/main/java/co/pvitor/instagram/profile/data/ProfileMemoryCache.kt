package co.pvitor.instagram.profile.data

import co.pvitor.instagram.common.model.LocalCache
import co.pvitor.instagram.common.model.UserAuth

object ProfileMemoryCache : LocalCache<Pair<UserAuth, Boolean?>> {

    private var userAuth: Pair<UserAuth, Boolean?>? = null

    override fun isCached(): Boolean {
        return userAuth != null
    }

    override fun get(key: String?) : Pair<UserAuth, Boolean?>? {
        if(userAuth?.first?.uuid == key) {
            return userAuth
        }
        return null
    }

    override fun set(data: Pair<UserAuth, Boolean?>?) {
        userAuth = data
    }

}