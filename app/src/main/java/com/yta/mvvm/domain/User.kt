package com.yta.mvvm.domain

data class User(
    val userId: Long,
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val age: Int,
    val rank: String,
    val company: String
)