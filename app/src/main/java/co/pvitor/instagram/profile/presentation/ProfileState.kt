package co.pvitor.instagram.profile.presentation

import co.pvitor.instagram.common.model.Post
import co.pvitor.instagram.common.model.UserAuth
import co.pvitor.instagram.profile.Profile

class ProfileState(
    private val posts: List<Post>?,
    private val userAuth: UserAuth?
): Profile.State {

    override fun fetchProfilePosts(): List<Post>? {
        return posts
    }

    override fun fetchProfileUser(): UserAuth? {
        return userAuth
    }
}