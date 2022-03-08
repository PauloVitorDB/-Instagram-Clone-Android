package co.pvitor.instagram.search.data

import co.pvitor.instagram.common.model.User
import co.pvitor.instagram.common.util.RequestCallback
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FireSearchDataSource : SearchDataSource {

    override fun fetchUsers(search: String, callback: RequestCallback<List<User>>) {

        FirebaseFirestore.getInstance()
            .collection("/users")
            .whereGreaterThanOrEqualTo("name", search)
            .whereLessThanOrEqualTo("name", "$search\uf8ff")
            .get()
            .addOnSuccessListener { documents ->
                val userList = mutableListOf<User>()
                for(document in documents) {
                    val user = document.toObject(User::class.java)
                    if(user.uuid != FirebaseAuth.getInstance().uid) {
                        userList.add(user)
                    }
                }
                callback.onSuccess(userList)
            }
            .addOnFailureListener {
                callback.onFailure()
            }
            .addOnCompleteListener {
                callback.onComplete()
            }

    }

}