package co.pvitor.instagram.home.data

import co.pvitor.instagram.R
import co.pvitor.instagram.common.model.Post
import co.pvitor.instagram.common.util.RequestCallback
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class FireHomeDataSource : HomeDataSource {

    override fun fetchPosts(uuid: String, callback: RequestCallback<List<Post>>) {

        val uid = FirebaseAuth.getInstance().uid

        uid?.let {
            FirebaseFirestore.getInstance()
                .collection("/feeds")
                .document(it)
                .collection("posts")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener { result ->

                    val feed = mutableListOf<Post>()

                    for(document in result.documents) {
                        val post = document.toObject(Post::class.java)
                        if(post != null) {
                            feed.add(post)
                        }
                    }

                    callback.onSuccess(feed)

                }
                .addOnFailureListener {
                    callback.onFailure(R.string.error_loading_posts)
                }
                .addOnCompleteListener {
                    callback.onComplete()
                }
        }

    }

}