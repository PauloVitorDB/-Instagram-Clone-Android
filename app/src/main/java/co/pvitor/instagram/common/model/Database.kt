package co.pvitor.instagram.common.model

import co.pvitor.instagram.R
import java.sql.Timestamp
import java.util.*

object Database {

    val usersAuth = mutableListOf<UserAuth>()

    var sessionUserAuth: UserAuth? = null
    var posts = hashMapOf<String, MutableSet<Post>>()
    var feedList = hashMapOf<String, MutableSet<Post>?>()
    val followers = hashMapOf<String, Set<String>>()

    private var uuidLoggedTest: String = UUID.randomUUID().toString()

    init {

        val userA = UserAuth(uuidLoggedTest, "userA", "userA@test.com", "12345678", null, 0, 0, 0)
        val userB = UserAuth(UUID.randomUUID().toString(), "userB", "userB@test.com", "12345678", null, 0, 0,0)

        usersAuth.add(userA)
        usersAuth.add(userB)

        followers[userA.uuid] = hashSetOf()
        posts[userA.uuid] = mutableSetOf()
        feedList[userA.uuid] = mutableSetOf()

        followers[userB.uuid] = hashSetOf()
        posts[userB.uuid] = mutableSetOf()
        feedList[userB.uuid] = mutableSetOf()

        sessionUserAuth = usersAuth.first()

    }

}