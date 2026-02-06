package com.yta.mvvm.data

import com.yta.mvvm.domain.User
import kotlinx.coroutines.delay

class MockDb : DataSource {
    private val userList = listOf(
        User(
            userId = 1L,
            firstName = "John",
            middleName = "A.",
            lastName = "Smith",
            age = 28,
            rank = "Junior Developer",
            company = "TechNova"
        ),
        User(
            userId = 2L,
            firstName = "Emily",
            middleName = "B.",
            lastName = "Johnson",
            age = 34,
            rank = "Senior Developer",
            company = "CodeWorks"
        ),
        User(
            userId = 3L,
            firstName = "Michael",
            middleName = "C.",
            lastName = "Brown",
            age = 41,
            rank = "Tech Lead",
            company = "InnovateX"
        ),
        User(
            userId = 4L,
            firstName = "Sophia",
            middleName = "D.",
            lastName = "Davis",
            age = 26,
            rank = "QA Engineer",
            company = "QualitySoft"
        ),
        User(
            userId = 5L,
            firstName = "Daniel",
            middleName = "E.",
            lastName = "Wilson",
            age = 38,
            rank = "Product Manager",
            company = "NextGen Apps"
        ),
        User(
            userId = 6L,
            firstName = "Olivia",
            middleName = "F.",
            lastName = "Martinez",
            age = 31,
            rank = "UX Designer",
            company = "DesignHub"
        ),
        User(
            userId = 7L,
            firstName = "James",
            middleName = "G.",
            lastName = "Anderson",
            age = 45,
            rank = "Engineering Manager",
            company = "CloudSphere"
        ),
        User(
            userId = 8L,
            firstName = "Isabella",
            middleName = "H.",
            lastName = "Taylor",
            age = 29,
            rank = "Data Analyst",
            company = "DataFlow"
        ),
        User(
            userId = 9L,
            firstName = "William",
            middleName = "I.",
            lastName = "Thomas",
            age = 36,
            rank = "DevOps Engineer",
            company = "InfraTech"
        ),
        User(
            userId = 10L,
            firstName = "Ava",
            middleName = "J.",
            lastName = "Moore",
            age = 24,
            rank = "Intern",
            company = "StartupLabs"
        )
    )

    override suspend fun getAllUsers(): Result<List<User>> {
        delay(1000)
        return Result.success(userList)
    }

    override suspend fun getUser(userId: Long): Result<User> {
        delay(1000)
        val user = userList.find { it.userId == userId }
            ?: return Result.failure(IllegalStateException())

        return Result.success(user)
    }
}