package co.pvitor.instagram.common.base

import co.pvitor.instagram.login.data.FakeDataSource
import co.pvitor.instagram.login.data.LoginDataSource
import co.pvitor.instagram.login.data.LoginRepository
import co.pvitor.instagram.profile.data.*
import co.pvitor.instagram.register.data.FakeRegisterDataSource
import co.pvitor.instagram.register.data.RegisterDataSource
import co.pvitor.instagram.register.data.RegisterRepository
import co.pvitor.instagram.splash.data.LocalSplashDataSource
import co.pvitor.instagram.splash.data.SplashDataSource
import co.pvitor.instagram.splash.data.SplashRepository

object DependencyInjector {

    fun splashRepository(): SplashRepository {
        val dataSource: SplashDataSource = LocalSplashDataSource()
        return SplashRepository(dataSource)
    }

    fun loginRepository(): LoginRepository {
        val dataSource: LoginDataSource = FakeDataSource()
        return LoginRepository(dataSource)
    }

    fun registerRepository(): RegisterRepository {
        val dataSource: RegisterDataSource = FakeRegisterDataSource()
        return RegisterRepository(dataSource)
    }

    fun profileRepository(): ProfileRepository {
        val dataSourceFactory = ProfileDataSourceFactory(ProfileMemoryCache, PostsMemoryCache)
        return ProfileRepository(dataSourceFactory)
    }

}