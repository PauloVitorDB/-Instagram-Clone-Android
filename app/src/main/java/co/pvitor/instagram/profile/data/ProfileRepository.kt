package co.pvitor.instagram.profile.data

import co.pvitor.instagram.common.model.Post
import co.pvitor.instagram.common.model.UserAuth
import co.pvitor.instagram.common.util.RequestCallback

class ProfileRepository(
    private val dataSource: ProfileDataSource
) {

    fun fetchProfileUser(uuid: String, callback: RequestCallback<UserAuth>) {
        dataSource.fetchProfileUser(uuid, callback)
    }

    fun fetchProfilePosts(uuid: String, callback: RequestCallback<List<Post>>) {
        dataSource.fetchProfilePosts(uuid, callback)
    }

}