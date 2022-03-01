package co.pvitor.instagram.profile.data

import co.pvitor.instagram.common.model.Post
import co.pvitor.instagram.common.model.UserAuth
import co.pvitor.instagram.common.util.RequestCallback

class ProfileRepository(
    private val profileDataSourceFactory: ProfileDataSourceFactory
) {

    private fun getUserUUID(uuid: String?): String {
        return uuid ?: profileDataSourceFactory.createLocalDataSource().fetchSession().uuid
    }

    fun clearCache() {
        val localDataSource = profileDataSourceFactory.createLocalDataSource()
        localDataSource.putPostList(null)
    }

    fun fetchProfileUser(uuid: String?, callback: RequestCallback<Pair<UserAuth, Boolean?>>) {

        val localDataSource: ProfileDataSource = profileDataSourceFactory.createLocalDataSource()

        val dataSource = profileDataSourceFactory.createFromUser(uuid)

        dataSource.fetchProfileUser(getUserUUID(uuid), object : RequestCallback<Pair<UserAuth, Boolean?>> {
            override fun onComplete() {
                callback.onComplete()
            }

            override fun onFailure() {
                callback.onFailure()
            }

            override fun onSuccess(response: Pair<UserAuth, Boolean?>) {
                if(uuid == null) {
                    localDataSource.putUser(response)
                }
                callback.onSuccess(response)
            }
        })
    }

    fun fetchProfilePosts(uuid: String?, callback: RequestCallback<List<Post>>) {

        val localDataSource: ProfileDataSource = profileDataSourceFactory.createLocalDataSource()

        val dataSource: ProfileDataSource = profileDataSourceFactory.createFromPosts(uuid)

        dataSource.fetchProfilePosts(getUserUUID(uuid), object : RequestCallback<List<Post>> {

            override fun onSuccess(response: List<Post>) {
                if(uuid == null) {
                    localDataSource.putPostList(response)
                }
                callback.onSuccess(response)
            }

            override fun onFailure() {
                callback.onFailure()
            }

            override fun onComplete() {
                callback.onComplete()
            }

        })

    }

}