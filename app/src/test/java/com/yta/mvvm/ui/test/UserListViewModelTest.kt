package com.yta.mvvm.ui.test

import com.yta.mvvm.common.BaseKoinTest
import com.yta.mvvm.di.testModule
import com.yta.mvvm.presentation.userList.UserListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.koin.core.component.inject
import org.koin.core.module.Module


@OptIn(ExperimentalCoroutinesApi::class)
class UserListViewModelTest : BaseKoinTest() {
    override val modules: List<Module> = testModule()
    private val userListViewModel: UserListViewModel by inject()

    @Test
    fun userListViewModel_successfullyLoadedUsers_updatedStateCorrectly() = runTest {
        var currentUiState = userListViewModel.state
        assertTrue(currentUiState.value.allUsers.isEmpty())
        assertTrue(currentUiState.value.filteredUsers.isEmpty())
        assertTrue(currentUiState.value.userQuery.isEmpty())
        assertFalse(currentUiState.value.isLoading)

        userListViewModel.loadUsers()
        advanceUntilIdle()

        currentUiState = userListViewModel.state

        assertTrue(currentUiState.value.allUsers.isNotEmpty())
        assertTrue(currentUiState.value.filteredUsers.isNotEmpty())
        assertTrue(currentUiState.value.userQuery.isEmpty())
        assertFalse(currentUiState.value.isLoading)
    }
}
