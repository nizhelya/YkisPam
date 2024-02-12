package com.ykis.ykispam.domain.user


data class UserEntity(
    val uid: String,
    val name: String = "Unknown",
    val email: String = "Unknown",
    val image: String = "Unknown",
    val token: String = "Unknown"
)