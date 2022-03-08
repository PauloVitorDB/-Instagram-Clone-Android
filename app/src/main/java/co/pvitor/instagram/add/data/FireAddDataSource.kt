package co.pvitor.instagram.add.data

import android.net.Uri
import co.pvitor.instagram.common.model.Post
import co.pvitor.instagram.common.model.User
import co.pvitor.instagram.common.util.RequestCallback
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class FireAddDataSource: AddDataSource {

    override fun createPost(
        userUUID: String,
        uri: Uri,
        caption: String,
        callback: RequestCallback<Boolean>
    ) {
        val uriPath : String? = uri.lastPathSegment
        if(uriPath != null) {

            val storageRef = FirebaseStorage.getInstance().reference
                .child("/images")
                .child(userUUID)
                .child(uriPath)

            storageRef.putFile(uri)
                .addOnSuccessListener { result ->
                    storageRef.downloadUrl
                        .addOnSuccessListener { postUriUrl ->

                            FirebaseFirestore.getInstance()
                                .collection("/users")
                                .document(userUUID)
                                .get()
                                .addOnSuccessListener { user ->

                                    val user = user.toObject(User::class.java)

                                    val postRef = FirebaseFirestore.getInstance()
                                        .collection("/posts")
                                        .document(userUUID)
                                        .collection("posts")
                                        .document()

                                    val post = Post(
                                        postRef.id,
                                        postUriUrl.toString(),
                                        caption,
                                        System.currentTimeMillis(),
                                        user
                                    )

                                    postRef.set(post)
                                        .addOnSuccessListener {

                                            FirebaseFirestore.getInstance()
                                                .collection("/feeds")
                                                .document(userUUID)
                                                .collection("posts")
                                                .document(postRef.id)
                                                .set(post)
                                                .addOnSuccessListener {

                                                    FirebaseFirestore.getInstance()
                                                        .collection("/followers")
                                                        .document(userUUID)
                                                        .get()
                                                        .addOnSuccessListener { resFollowers ->

                                                            val list = resFollowers.get("followers") as List<String>

                                                            for(uuid in list) {

                                                                FirebaseFirestore.getInstance()
                                                                    .collection("/feeds")
                                                                    .document(uuid)
                                                                    .collection("posts")
                                                                    .document(postRef.id)
                                                                    .set(post)

                                                            }

                                                            callback.onSuccess(true)
                                                        }
                                                        .addOnCompleteListener {
                                                            callback.onComplete()
                                                        }

                                                }

                                        }
                                        .addOnCompleteListener {
                                            callback.onComplete()
                                        }
                                }

                        }
                }
                .addOnFailureListener {
                    callback.onFailure()
                }

        } else {
            callback.onFailure()
        }

    }

}