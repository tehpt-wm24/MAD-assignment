package com.example.splashmaniaapp.data

data class FakeUser(
    val email: String,
    val password: String,
    val username: String
)

val fakeUser = FakeUser(
    email = "pingting060906@gmail.com",
    password = "123456",
    username = "Ping Ting"
)