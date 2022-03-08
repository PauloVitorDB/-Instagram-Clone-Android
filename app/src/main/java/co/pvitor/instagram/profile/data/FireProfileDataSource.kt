package co.pvitor.instagram.profile.data

import co.pvitor.instagram.R
import co.pvitor.instagram.common.model.Post
import co.pvitor.instagram.common.model.User
import co.pvitor.instagram.common.util.RequestCallback
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query

class FireProfileDataSource : ProfileDataSource {

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

                when (user) {
                    null -> callback.onFailure(R.string.internal_server_error)
                    else -> {
                        if (user.uuid == FirebaseAuth.getInstance().uid) {
                            callback.onSuccess(Pair(user, null))
                        } else {
                            FirebaseFirestore.getInstance()
                                .collection("/followers")
                                .document(uuid)
                                .get()
                                .addOnSuccessListener { document ->
                                    if (!document.exists()) {
                                        callback.onSuccess(Pair(user, false))
                                    } else {
                                        val list = document.get("followers") as List<String>
                                        callback.onSuccess(
                                            Pair(
                                                user,
                                                list.contains(FirebaseAuth.getInstance().uid)
                                            )
                                        )
                                    }
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
                for (document in documents) {
                    val post = document.toObject(Post::class.java)
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
        val uid = FirebaseAuth.getInstance().uid ?: throw RuntimeException()
        FirebaseFirestore.getInstance()
            .collection("/followers")
            .document(uuid)
            .update(
                "followers",
                if (isFollow) FieldValue.arrayUnion(uuid) else FieldValue.arrayRemove(uuid)
            )
            .addOnSuccessListener {
                followingCounter(uid, isFollow)
                followersCounter(uuid)
                updateFeed(uuid, isFollow)
            }
            .addOnCompleteListener {
                callback.onComplete()
            }
            .addOnFailureListener { exception ->
                val error = exception as? FirebaseFirestoreException
                error?.let {
                    if (error.code == FirebaseFirestoreException.Code.NOT_FOUND) {
                        FirebaseFirestore.getInstance()
                            .collection("/followers")
                            .document(uuid)
                            .set(
                                hashMapOf(
                                    "followers" to listOf(uuid)
                                )
                            )
                    }
                }
            }

    }

    private fun followersCounter(uid: String) {
        val user = FirebaseFirestore.getInstance()
            .collection("/users")
            .document(uid)

        FirebaseFirestore.getInstance()
            .collection("/followers")
            .document(uid)
            .get()
            .addOnSuccessListener { response ->
                if(response.exists()) {
                    val list = response.get("followers") as List<String>
                    user.update("followers", list.size)
                }
            }
    }

    private fun followingCounter(uid: String, isFollow: Boolean) {
        val user = FirebaseFirestore.getInstance()
            .collection("/users")
            .document(uid)

        if(isFollow) {
            user.update("following", FieldValue.increment(1))
        } else {
            user.update("following", FieldValue.increment(-1))
        }
    }

    override fun logout() {
        FirebaseAuth.getInstance().signOut()
    }

    override fun updateFeed(uid: String, isFollow: Boolean) {
        if(isFollow) {
            FirebaseFirestore.getInstance()
                .collection("/posts")
                .document(uid)
                .collection("posts")
                .get()
                .addOnSuccessListener { results ->
                    val posts = results.toObjects(Post::class.java)
                    posts.lastOrNull()?.let { post ->
                        FirebaseFirestore.getInstance()
                            .collection("/feeds")
                            .document(FirebaseAuth.getInstance().uid!!)
                            .collection("posts")
                            .document(post.uuid!!)
                            .set(post)
                    }
                }
        } else {
            FirebaseFirestore.getInstance()
                .collection("/feeds")
                .document(FirebaseAuth.getInstance().uid!!)
                .collection("posts")
                .whereEqualTo("publisher.uuid", uid)
                .get()
                .addOnSuccessListener { result ->
                    val documents = result.documents
                    for(document in documents) {
                        document.reference.delete()
                    }
                }
        }
    }

}