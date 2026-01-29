package com.yta.mvvm.domain.repository

import com.yta.mvvm.domain.User

interface UserRepository {
    suspend fun getUsers(): Result<List<User>>
    suspend fun getUser(userId: Long): Result<User>
}