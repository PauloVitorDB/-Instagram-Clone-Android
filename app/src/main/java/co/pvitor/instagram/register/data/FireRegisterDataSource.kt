package co.pvitor.instagram.register.data

import android.net.Uri
import co.pvitor.instagram.R
import co.pvitor.instagram.common.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class FireRegisterDataSource: RegisterDataSource {

    override fun register(email: String, callback: RegisterCallback<String>) {

        FirebaseFirestore.getInstance()
            .collection("/users")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { documents ->
                if(documents.isEmpty) {
                    callback.onSuccess(email)
                } else {
                    callback.onFailure(R.string.email_alredy_exists)
                }
            }
            .addOnFailureListener {
                callback.onFailure(R.string.internal_server_error)
            }
            .addOnCompleteListener {
                callback.onComplete()
            }

    }

    override fun register(
        email: String,
        password: String,
        name: String,
        callback: RegisterCallback<String?>
    ) {
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { authResult ->

                val uuid = authResult.user?.uid

                if(uuid == null) {
                    callback.onFailure(R.string.internal_server_error)
                } else {
                    FirebaseFirestore.getInstance()
                        .collection("/users")
                        .document(uuid)
                        .set(
                            hashMapOf(
                                "uuid" to uuid,
                                "name" to name,
                                "email" to email,
                                "followers" to 0,
                                "following" to 0,
                                "postCount" to 0,
                                "profilePhotoUrl" to null
                            )
                        )
                        .addOnSuccessListener {
                            callback.onSuccess(email)
                        }
                        .addOnFailureListener {
                            callback.onFailure(R.string.internal_server_error)
                        }
                        .addOnCompleteListener {
                            callback.onComplete()
                        }
                }

            }
            .addOnFailureListener {
                callback.onFailure(R.string.internal_server_error)
            }
    }

    override fun updateUserPhoto(uri: Uri, callback: RegisterCallback<Uri>) {

        val uuid = FirebaseAuth.getInstance().uid

        if(uuid == null || Uri.EMPTY.equals(uri)) {
            callback.onFailure(R.string.error_session_user)
            return
        }

        val storageRef = FirebaseStorage.getInstance().reference

        val imgRef = storageRef.child("images/")
            .child(uuid)
            .child(uri.lastPathSegment!!)

        imgRef.putFile(uri)
            .addOnSuccessListener { result ->
                imgRef.downloadUrl.addOnSuccessListener { res ->
                    val userRef = FirebaseFirestore.getInstance()
                        .collection("/users")
                        .document(uuid)

                    userRef.get()
                        .addOnSuccessListener { document ->

                            val user = document.toObject(User::class.java)
                            val updatedUser = user?.copy(
                                profilePhotoUrl = res.toString()
                            )

                            updatedUser?.let {
                                userRef.set(updatedUser)
                                    .addOnSuccessListener {
                                        callback.onSuccess(uri)
                                    }
                                    .addOnFailureListener {
                                        callback.onFailure(R.string.error_update_photo)
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

}