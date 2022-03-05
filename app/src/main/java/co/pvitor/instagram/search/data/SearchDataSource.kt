package co.pvitor.instagram.search.data

import co.pvitor.instagram.common.model.User
import co.pvitor.instagram.common.model.UserAuth
import co.pvitor.instagram.common.util.RequestCallback

interface SearchDataSource {

    fun fetchUsers(search: String, callback: RequestCallback<List<User>>)

}