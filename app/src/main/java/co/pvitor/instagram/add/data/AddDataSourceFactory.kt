package co.pvitor.instagram.add.data

class AddDataSourceFactory {

    fun createLocalDataSource(): AddDataSource {
        return AddLocalDataSource()
    }

    fun createRemoteDataSource(): AddDataSource {
        return FireAddDataSource()
    }

}