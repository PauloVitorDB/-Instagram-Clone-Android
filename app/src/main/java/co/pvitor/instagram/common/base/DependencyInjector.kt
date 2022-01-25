package co.pvitor.instagram.common.base

import co.pvitor.instagram.login.Login
import co.pvitor.instagram.login.data.FakeDataSource
import co.pvitor.instagram.login.data.LoginDataSource
import co.pvitor.instagram.login.data.LoginRepository

object DependencyInjector {

    fun loginRepository(): LoginRepository {
        val dataSource: LoginDataSource = FakeDataSource()
        return LoginRepository(dataSource)
    }

}