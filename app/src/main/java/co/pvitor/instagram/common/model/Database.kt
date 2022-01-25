package co.pvitor.instagram.common.model

import java.util.*

object Database {

    val usersAuth = hashSetOf<UserAuth>()

    var sessionUserAuth: UserAuth? = null

    init {
        usersAuth.add(UserAuth(UUID.randomUUID().toString(), "userA@test.com", "12345678"))
        usersAuth.add(UserAuth(UUID.randomUUID().toString(), "userB@test.com", "12345678"))
    }

}