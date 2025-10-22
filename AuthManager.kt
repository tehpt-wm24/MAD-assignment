package com.example.splashmaniaapp.data

object AuthManager {
    private var currentUser: FakeUser? = null

    fun login(email: String, password: String): Boolean {
        return when {
            currentUser?.email == email && currentUser?.password == password -> true

            email == fakeUser.email && password == fakeUser.password -> {
                currentUser = fakeUser
                true
            }

            else -> false
        }
    }

    fun signUp(email: String, password: String, username: String) {
        currentUser = FakeUser(email = email, password = password, username = username)
    }

    fun resetPassword(email: String): Boolean {
        return currentUser?.email == email || fakeUser.email == email
    }

    fun getCurrentUser(): FakeUser? {
        return currentUser
    }

    fun setCurrentUser(user: FakeUser) {
        currentUser = user
    }

    fun clearUser() {
        currentUser = null
    }
}