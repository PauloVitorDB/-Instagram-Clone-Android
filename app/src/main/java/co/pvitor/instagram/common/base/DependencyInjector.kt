package co.pvitor.instagram.common.base

import co.pvitor.instagram.login.data.FakeDataSource
import co.pvitor.instagram.login.data.LoginDataSource
import co.pvitor.instagram.login.data.LoginRepository
import co.pvitor.instagram.register.data.FakeRegisterDataSource
import co.pvitor.instagram.register.data.RegisterDataSource
import co.pvitor.instagram.register.data.RegisterRepository

object DependencyInjector {

    fun loginRepository(): LoginRepository {
        val dataSource: LoginDataSource = FakeDataSource()
        return LoginRepository(dataSource)
    }

    fun registerRepository(): RegisterRepository {
        val dataSource: RegisterDataSource = FakeRegisterDataSource()
        return RegisterRepository(dataSource)
    }

}