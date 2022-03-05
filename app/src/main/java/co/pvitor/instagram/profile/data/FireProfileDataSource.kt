package co.pvitor.instagram.profile.data

import co.pvitor.instagram.R
import co.pvitor.instagram.common.model.Post
import co.pvitor.instagram.common.model.User
import co.pvitor.instagram.common.util.RequestCallback
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class FireProfileDataSource: ProfileDataSource {

    override fun fetchProfileUser(
        uuid: String,
        callback: RequestCallback<Pair<User, Boolean?>>
    ) {
        FirebaseFirestore.getInstance()
            .collection("/users")
            .document(uuid)
            .get()
            .addOnSuccessListener { document ->

                val user: User? = document.toObject(User::class.java)

                when(user) {
                    null -> callback.onFailure(R.string.internal_server_error)
                    else -> {
                        if(user.uuid == FirebaseAuth.getInstance().uid) {
                            callback.onSuccess(Pair(user, null))
                        } else {
                            FirebaseFirestore.getInstance()
                                .collection("/followers")
                                .document(FirebaseAuth.getInstance().uid!!)
                                .collection("followers")
                                .whereEqualTo("uuid", uuid)
                                .get()
                                .addOnSuccessListener { document ->
                                    callback.onSuccess(Pair(user, document.isEmpty))
                                }
                                .addOnFailureListener {
                                    callback.onFailure(R.string.internal_server_error)
                                }
                                .addOnCompleteListener {
                                    callback.onComplete()
                                }
                        }
                    }
                }
            }
            .addOnFailureListener {
                callback.onFailure(R.string.internal_server_error)
            }
    }

    override fun fetchProfilePosts(uuid: String, callback: RequestCallback<List<Post>>) {
        FirebaseFirestore.getInstance()
            .collection("posts")
            .document(uuid)
            .collection("posts")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { documents ->

                val postList = mutableListOf<Post>()
                for(document in documents) {
                    val post =  document.toObject(Post::class.java)
                    post.uri?.let {
                        postList.add(post)
                    }
                }

                callback.onSuccess(postList)
            }
            .addOnFailureListener {
                callback.onFailure(R.string.internal_server_error)
            }
            .addOnCompleteListener {
                callback.onComplete()
            }
    }

    override fun followUser(uuid: String, isFollow: Boolean, callback: RequestCallback<Boolean>) {

    }

}