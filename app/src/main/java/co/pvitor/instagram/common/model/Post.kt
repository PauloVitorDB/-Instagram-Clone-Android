package co.pvitor.instagram.common.model

import android.net.Uri
import java.sql.Timestamp

data class Post(
    val uuid: String? = null,
    val uri: String? = null,
    val caption: String? = null,
    val timestamp: Long? = null,
    val publisher: User? = null
)