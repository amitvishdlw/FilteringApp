package com.yta.mvvm.data

import com.yta.mvvm.domain.User

interface DataSource {
    suspend fun getAllUsers(): Result<List<User>>
    suspend fun getUser(userId: Long): Result<User>
}