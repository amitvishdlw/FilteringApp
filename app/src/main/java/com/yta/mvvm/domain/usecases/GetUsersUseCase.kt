package com.yta.mvvm.domain.usecases

import com.yta.mvvm.domain.User
import com.yta.mvvm.domain.repository.UserRepository

class GetUsersUseCase(
    private val repo: UserRepository
) {
    suspend operator fun invoke(): Result<List<User>> = repo.getUsers()
}