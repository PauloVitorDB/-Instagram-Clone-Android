package co.pvitor.instagram.profile.data

import android.os.Handler
import android.os.Looper
import co.pvitor.instagram.common.model.Database
import co.pvitor.instagram.common.model.Post
import co.pvitor.instagram.common.model.UserAuth
import co.pvitor.instagram.common.util.RequestCallback

class ProfileFakeDataSource: ProfileDataSource {

    override fun fetchProfileUser(uuid: String, callback: RequestCallback<Pair<UserAuth, Boolean?>>) {

        Handler(Looper.getMainLooper()).postDelayed({
            val userAuth: UserAuth? = Database.usersAuth.firstOrNull {
                uuid == it.uuid
            }

            if(userAuth != null) {
                if(uuid == Database.sessionUserAuth?.uuid) {
                    callback.onSuccess(Pair(userAuth, null) )
                } else {

                    val followers = Database.followers[Database.sessionUserAuth?.uuid]

                    val isFollower = followers?.firstOrNull { it == uuid }
                    callback.onSuccess(Pair(userAuth, isFollower != null))
                }
            } else {
                callback.onFailure()
            }

            callback.onComplete()
        }, 3000)

    }

    override fun fetchProfilePosts(uuid: String, callback: RequestCallback<List<Post>>) {

        val posts: Set<Post>? = Database.posts[uuid]

        Handler(Looper.getMainLooper()).postDelayed({

            if(posts != null) {
                callback.onSuccess(posts.toList())
            } else {
                 callback.onSuccess(emptyList())
            }

            callback.onComplete()

        }, 3000)
    }

}