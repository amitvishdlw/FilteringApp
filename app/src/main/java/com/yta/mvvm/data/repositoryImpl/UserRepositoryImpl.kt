package com.yta.mvvm.data.repositoryImpl

import com.yta.mvvm.data.DataSource
import com.yta.mvvm.domain.User
import com.yta.mvvm.domain.repository.UserRepository

class UserRepositoryImpl(
    private val db: DataSource
) : UserRepository {
    override suspend fun getUsers(): Result<List<User>> {
        return db.getAllUsers()
    }

    override suspend fun getUser(userId: Long): Result<User> {
        return db.getUser(userId)
    }
}

