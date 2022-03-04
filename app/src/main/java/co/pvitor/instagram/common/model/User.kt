package co.pvitor.instagram.common.model

import android.net.Uri

data class User(
    val uuid: String? = null,
    val name: String? = null,
    val email: String? = null,
    val profilePhotoUrl: String? = null,
    val countPosts: Int = 0,
    val followers: Int = 0,
    val following: Int = 0
)
