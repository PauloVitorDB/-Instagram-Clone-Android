package co.pvitor.instagram.add.data

import android.net.Uri
import co.pvitor.instagram.common.util.RequestCallback

class AddRepository(
    private val dataSourceFactory: AddDataSourceFactory
) {

    fun createPost(uri: Uri, caption: String, callback: RequestCallback<Boolean>) {

        val localDataSource = dataSourceFactory.createLocalDataSource()

        val userAuth = localDataSource.fetchSession()

        val dataSource = dataSourceFactory.createRemoteDataSource()

        userAuth.uuid?.let {
            dataSource.createPost(it, uri, caption, object: RequestCallback<Boolean> {
                override fun onSuccess(response: Boolean) {
                    callback.onSuccess(response)
                }

                override fun onFailure() {
                    callback.onFailure()
                }

                override fun onComplete() {
                    callback.onComplete()
                }

            })
        }

    }

}