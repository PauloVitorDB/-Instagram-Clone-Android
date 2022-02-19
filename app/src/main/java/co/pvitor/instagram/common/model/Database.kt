package co.pvitor.instagram.common.model

import co.pvitor.instagram.R
import java.sql.Timestamp
import java.util.*

object Database {

    val usersAuth = hashSetOf<UserAuth>()

    var sessionUserAuth: UserAuth? = null
    var userPhoto: UserPhoto? = null
    var posts = hashMapOf<String, Set<Post>>()
    var feedList = hashMapOf<String, Set<Post>?>()

    private var uuidLoggedTest: String = UUID.randomUUID().toString()
    var setPostFeedList: Set<Post>? = null

    init {

        usersAuth.add(UserAuth(uuidLoggedTest, "userA", "userA@test.com", "12345678", 0, 0, 0))
        usersAuth.add(UserAuth(UUID.randomUUID().toString(), "userB", "userB@test.com", "12345678", 0, 0,0))

        setPostFeedList = setOf(Post(
            uuidLoggedTest,
            R.drawable.insta_grid_photo,
            "",
            null,
            usersAuth.last()
        ))

        feedList[uuidLoggedTest] = setPostFeedList
        sessionUserAuth = usersAuth.first()


    }

}