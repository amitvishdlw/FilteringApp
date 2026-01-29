package com.yta.mvvm.domain.usecases

import com.yta.mvvm.domain.User
import com.yta.mvvm.domain.repository.UserRepository

class GetUserUseCase(
    private val repo: UserRepository
) {
    suspend operator fun invoke(userId: Long): Result<User> = repo.getUser(userId)
}