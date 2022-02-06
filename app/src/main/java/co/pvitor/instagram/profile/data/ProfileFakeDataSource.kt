package co.pvitor.instagram.profile.data

import android.os.Handler
import android.os.Looper
import co.pvitor.instagram.common.model.Database
import co.pvitor.instagram.common.model.Post
import co.pvitor.instagram.common.model.UserAuth
import co.pvitor.instagram.common.util.RequestCallback

class ProfileFakeDataSource: ProfileDataSource {

    override fun fetchProfileUser(uuid: String, callback: RequestCallback<UserAuth>) {

        Handler(Looper.getMainLooper()).postDelayed({
            val userAuth: UserAuth? = Database.usersAuth.firstOrNull {
                uuid == it.uuid
            }

            if(userAuth != null) {
                callback.onSuccess(userAuth)
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
                callback.onFailure()
            }

            callback.onComplete()

        }, 3000)
    }

}