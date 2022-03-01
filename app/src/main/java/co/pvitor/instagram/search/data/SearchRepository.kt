package co.pvitor.instagram.search.data

import co.pvitor.instagram.common.model.UserAuth
import co.pvitor.instagram.common.util.RequestCallback

class SearchRepository(
    private val dataSource: SearchDataSource
) {

    fun fetchUsers(search: String, callback: RequestCallback<List<UserAuth>>) {
        dataSource.fetchUsers(search, callback)
    }

}