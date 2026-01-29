package com.yta.mvvm.data.repositoryImpl

import com.yta.mvvm.domain.User

interface DataSource {
    suspend fun getAllUsers(): Result<List<User>>
    suspend fun getUser(userId: Long): Result<User>
}