package co.pvitor.instagram.common.model

import java.util.*

object Database {

    val usersAuth = hashSetOf<UserAuth>()

    var sessionUserAuth: UserAuth? = null
    var userPhoto: UserPhoto? = null

    init {

        usersAuth.add(UserAuth(UUID.randomUUID().toString(), "userA", "userA@test.com", "12345678"))
        usersAuth.add(UserAuth(UUID.randomUUID().toString(), "userB", "userB@test.com", "12345678"))

        sessionUserAuth = usersAuth.first()
    }

}