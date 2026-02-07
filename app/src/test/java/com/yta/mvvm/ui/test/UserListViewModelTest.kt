package com.yta.mvvm.ui.test

import com.yta.mvvm.common.MainCoroutineTestRule
import com.yta.mvvm.di.testModule
import com.yta.mvvm.presentation.userList.UserListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.component.inject
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.test.KoinTest

@OptIn(ExperimentalCoroutinesApi::class)
class UserListViewModelTest : KoinTest {
    @Before
    fun setup() {
        startKoin {
            modules(testModule())
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @get:Rule
    val mainCoroutineTestRule = MainCoroutineTestRule()

    private val userListViewModel: UserListViewModel by inject()

    @Test
    fun userListViewModel_successfullyLoadedUsers_updatedAllUsers() = runTest {
        var currentUiState = userListViewModel.state
        assertTrue(currentUiState.value.allUsers.isEmpty())
        assertTrue(currentUiState.value.filteredUsers.isEmpty())

        userListViewModel.loadUsers()
        advanceUntilIdle()

        currentUiState = userListViewModel.state

        assertFalse(currentUiState.value.allUsers.isEmpty())
        assertFalse(currentUiState.value.filteredUsers.isEmpty())
    }
}