package co.pvitor.instagram.profile.data

import co.pvitor.instagram.common.model.Post
import co.pvitor.instagram.common.model.UserAuth
import co.pvitor.instagram.common.util.RequestCallback

class ProfileRepository(
    private val profileDataSourceFactory: ProfileDataSourceFactory
) {

    fun clearCache() {
        val localDataSource = profileDataSourceFactory.createLocalDataSource()
        localDataSource.putPostList(null)
    }

    fun fetchProfileUser(callback: RequestCallback<UserAuth>) {

        val localDataSource: ProfileDataSource = profileDataSourceFactory.createLocalDataSource()

        val userAuthSession = localDataSource.fetchSession()

        val dataSource = profileDataSourceFactory.createFromUser()

        dataSource.fetchProfileUser(userAuthSession.uuid, object : RequestCallback<UserAuth> {
            override fun onComplete() {
                callback.onComplete()
            }

            override fun onFailure() {
                callback.onFailure()
            }

            override fun onSuccess(response: UserAuth) {
                localDataSource.putUser(response)
                callback.onSuccess(response)
            }
        })
    }

    fun fetchProfilePosts(callback: RequestCallback<List<Post>>) {

        val localDataSource: ProfileDataSource = profileDataSourceFactory.createLocalDataSource()

        val sessionUserAuth = localDataSource.fetchSession()

        val dataSource: ProfileDataSource = profileDataSourceFactory.createFromPosts()

        dataSource.fetchProfilePosts(sessionUserAuth.uuid, object : RequestCallback<List<Post>> {

            override fun onSuccess(response: List<Post>) {
                localDataSource.putPostList(response)
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