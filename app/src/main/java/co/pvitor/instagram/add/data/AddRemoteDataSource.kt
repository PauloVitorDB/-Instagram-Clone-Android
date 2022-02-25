package co.pvitor.instagram.add.data

import android.net.Uri
import android.os.Handler
import android.os.Looper
import co.pvitor.instagram.common.model.Database
import co.pvitor.instagram.common.model.Post
import co.pvitor.instagram.common.util.RequestCallback

class AddRemoteDataSource: AddDataSource {

    override fun createPost(userUUID: String, uri: Uri, caption: String, callback: RequestCallback<Boolean>) {

        Handler(Looper.getMainLooper()).postDelayed({

            val post = Post(
                userUUID,
                uri,
                caption,
                System.currentTimeMillis(),
                Database.sessionUserAuth!!
            )

            Database.posts[userUUID]?.add(post)

            val followers = Database.followers[userUUID]

            followers?.let {
                followers.forEach {
                    Database.feedList[it]?.add(post)
                }
            }

            callback.onSuccess(true)

            callback.onComplete()

        }, 1500)

    }

}