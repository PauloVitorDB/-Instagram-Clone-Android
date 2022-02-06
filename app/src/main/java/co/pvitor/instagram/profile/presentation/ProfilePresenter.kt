package co.pvitor.instagram.profile.presentation

import co.pvitor.instagram.R
import co.pvitor.instagram.common.model.Database
import co.pvitor.instagram.common.model.Post
import co.pvitor.instagram.common.model.UserAuth
import co.pvitor.instagram.common.util.RequestCallback
import co.pvitor.instagram.profile.Profile
import co.pvitor.instagram.profile.data.ProfileRepository

class ProfilePresenter(
    private var _view: Profile.View?,
    private val repository: ProfileRepository
): Profile.Presenter {

    private val view get() = _view!!

    private val uuid: String = Database.sessionUserAuth?.uuid ?: throw RuntimeException("User was not found in session")

    override fun fetchProfilePosts() {

        view.showProgress(true)

        repository.fetchProfilePosts(uuid, object: RequestCallback<List<Post>> {

            override fun onSuccess(response: List<Post>) {
                // val posts: List<Post> = response.ifEmpty { emptyList() }
                if(response.isEmpty()) {
                    view.displayEmptyPosts()
                } else {
                    view.displayUserPosts(response)
                }
            }

            override fun onComplete() {
            }

            override fun onFailure() {
                view.displayRequestFailure(R.string.request_user_profile_posts_fail)
            }

        })
    }

    override fun fetchProfileUser() {
        repository.fetchProfileUser(uuid, object: RequestCallback<UserAuth> {

            override fun onSuccess(response: UserAuth) {
                view.displayUserProfile(response)
            }

            override fun onComplete() {
                view.showProgress(false)
            }

            override fun onFailure() {
                view.displayRequestFailure(R.string.request_user_profile_fail)
            }
        })
    }

    override fun onDestroy() {
        _view = null
    }

}