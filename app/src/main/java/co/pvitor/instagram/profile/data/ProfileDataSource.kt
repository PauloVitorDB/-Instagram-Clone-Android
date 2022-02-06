package co.pvitor.instagram.profile.data

import co.pvitor.instagram.common.model.Post
import co.pvitor.instagram.common.model.UserAuth
import co.pvitor.instagram.common.util.RequestCallback

interface ProfileDataSource {
    fun fetchProfileUser(uuid: String, callback: RequestCallback<UserAuth>)
    fun fetchProfilePosts(uuid: String, callback: RequestCallback<List<Post>>)
}