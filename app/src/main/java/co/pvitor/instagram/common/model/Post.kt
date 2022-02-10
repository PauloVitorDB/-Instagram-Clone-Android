package co.pvitor.instagram.common.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.sql.Timestamp

@Parcelize
data class Post(
    val uuid: String,
    val uri: Uri,
    val caption: String,
    val timestamp: Timestamp
) : Parcelable