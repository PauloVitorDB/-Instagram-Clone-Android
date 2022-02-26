package co.pvitor.instagram.common.model

import android.net.Uri

data class UserAuth(
    val uuid: String,

    val name: String,
    val email: String,
    val password: String,
    val profilePhoto: Uri?,
    val countPosts: Int,
    val countFollowers: Int,
    val countFollowing: Int
)