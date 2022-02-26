package co.pvitor.instagram.profile.data

import co.pvitor.instagram.common.model.LocalCache
import co.pvitor.instagram.common.model.UserAuth

object ProfileMemoryCache : LocalCache<UserAuth> {

    private var userAuth: UserAuth? = null

    override fun isCached(): Boolean {
        return userAuth != null
    }

    override fun get(key: String?) : UserAuth? {
        if(userAuth?.uuid == key) {
            return userAuth
        }
        return null
    }

    override fun set(data: UserAuth?) {
        userAuth = data
    }

}