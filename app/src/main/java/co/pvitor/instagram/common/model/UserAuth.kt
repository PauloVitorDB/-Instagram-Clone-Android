package co.pvitor.instagram.common.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserAuth(
    val uuid: String,
    val name: String,
    val email: String,
    val password: String,
    val countPosts: Int,
    val countFollowers: Int,
    val countFollowing: Int
) : Parcelable