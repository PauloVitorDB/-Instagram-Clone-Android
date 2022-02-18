package co.pvitor.instagram.common.model

import android.net.Uri
import java.sql.Timestamp

data class Post(
    val uuid: String,
    val uri: Uri,
    val caption: String,
    val timestamp: Timestamp,
    val userAuth: UserAuth
)