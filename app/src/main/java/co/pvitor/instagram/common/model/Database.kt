package co.pvitor.instagram.common.model

import java.util.*

object Database {

    val usersAuth = hashSetOf<UserAuth>()

    var sessionUserAuth: UserAuth? = null
    var userPhoto: UserPhoto? = null
    var posts = hashMapOf<String, Set<Post>>()

    init {

        usersAuth.add(UserAuth(UUID.randomUUID().toString(), "userA", "userA@test.com", "12345678", 0, 0, 0))
        usersAuth.add(UserAuth(UUID.randomUUID().toString(), "userB", "userB@test.com", "12345678", 0, 0,0))

        sessionUserAuth = usersAuth.first()
    }

}