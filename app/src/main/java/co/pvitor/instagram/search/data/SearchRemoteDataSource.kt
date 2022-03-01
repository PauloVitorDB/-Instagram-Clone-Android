package co.pvitor.instagram.search.data

import android.os.Handler
import android.os.Looper
import co.pvitor.instagram.common.model.Database
import co.pvitor.instagram.common.model.UserAuth
import co.pvitor.instagram.common.util.RequestCallback

class SearchRemoteDataSource: SearchDataSource {

    override fun fetchUsers(search: String, callback: RequestCallback<List<UserAuth>>) {

        Handler(Looper.getMainLooper()).postDelayed({

            val users = Database.usersAuth.filter {
                it.name.lowercase().contains(search.lowercase(), ignoreCase = true)  && it.uuid != Database.sessionUserAuth?.uuid
            }

            callback.onSuccess(users.toList())
            callback.onComplete()

        }, 1500)

    }

}