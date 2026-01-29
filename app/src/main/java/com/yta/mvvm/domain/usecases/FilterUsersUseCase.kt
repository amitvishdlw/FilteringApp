package com.yta.mvvm.domain.usecases

import com.yta.mvvm.domain.User
import kotlinx.coroutines.delay

class FilterUsersUseCase {
    suspend operator fun invoke(
        usersList: List<User>,
        userQuery: String
    ): Result<List<User>> {
        delay(300)
        val filteredUsers = usersList.filter { user ->
            return@filter user.firstName.contains(userQuery, ignoreCase = true)
                    || user.middleName.contains(userQuery, ignoreCase = true)
                    || user.lastName.contains(userQuery, ignoreCase = true)
                    || user.age.toString().contains(userQuery)
                    || user.rank.contains(userQuery, ignoreCase = true)
                    || user.company.contains(userQuery, ignoreCase = true)
        }
        return Result.success(filteredUsers)
    }
}