package co.pvitor.instagram.login.data

interface LoginDataSource {
    fun login(email: String, password: String, callback: LoginCallback)
}