package com.yta.mvvm.ui.test

import com.yta.mvvm.common.BaseKoinTest
import com.yta.mvvm.di.testModule
import com.yta.mvvm.domain.User
import com.yta.mvvm.domain.repository.UserRepository
import com.yta.mvvm.presentation.userList.UserListAction
import com.yta.mvvm.presentation.userList.UserListEvent
import com.yta.mvvm.presentation.userList.UserListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.koin.core.component.inject
import org.koin.core.module.Module
import org.koin.test.mock.declare


@OptIn(ExperimentalCoroutinesApi::class)
class UserListViewModelTest : BaseKoinTest() {
    override val modules: List<Module> = testModule()


    @Test
    fun userListViewModel_successfullyLoadedUsers_updatedStateCorrectly() = runTest {
        val userListViewModel: UserListViewModel by inject()

        var currentUiState = userListViewModel.state
        // Assert users list is empty
        assertTrue(currentUiState.value.users.isEmpty())
        // Assert user search is empty
        assertTrue(currentUiState.value.userQuery.isEmpty())
        // Assert loading is false
        assertFalse(currentUiState.value.isLoading)

        backgroundScope.launch(UnconfinedTestDispatcher()) {
            userListViewModel.event.collect()
        }

        userListViewModel.loadUsers()
        advanceUntilIdle()

        currentUiState = userListViewModel.state
        // Assert users list is not empty
        assertTrue(currentUiState.value.users.isNotEmpty())
        // Assert user search is empty
        assertTrue(currentUiState.value.userQuery.isEmpty())
        // Assert loading is false
        assertFalse(currentUiState.value.isLoading)
    }

    @Test
    fun userListViewModel_onUserClick_openUserDetailEvent() = runTest {
        val userListViewModel: UserListViewModel by inject()
        val events = mutableListOf<UserListEvent>()

        backgroundScope.launch(UnconfinedTestDispatcher()) {
            userListViewModel.event.collect {
                events.add(it)
            }
        }

        userListViewModel.loadUsers()
        advanceUntilIdle()

        // Assert events has no event
        assertTrue(events.isEmpty())

        userListViewModel.onAction(UserListAction.OnUserClicked(1))
        advanceUntilIdle()

        // Assert events has one event
        assertTrue(events.size == 1)
        // Assert event is OpenUserDetailEvent
        assertTrue(events[0] is UserListEvent.OpenUserDetailEvent)
    }

    @Test
    fun userListViewModel_onUserSearch_updatedStateCorrectly() = runTest {
        val userListViewModel: UserListViewModel by inject()
        val events = mutableListOf<UserListEvent>()

        backgroundScope.launch(UnconfinedTestDispatcher()) {
            userListViewModel.event.collect {
                events.add(it)
            }
        }

        userListViewModel.loadUsers()
        advanceUntilIdle()

        // Assert events has no event
        assertTrue(events.isEmpty())

        userListViewModel.onAction(UserListAction.OnUserSearch("John"))
        advanceUntilIdle()

        // Assert users size is 2
        assertTrue(userListViewModel.state.value.users.size == 2)
        // Assert events has no event
        assertTrue(events.isEmpty())
        // Assert user search is not empty
        assertTrue(userListViewModel.state.value.userQuery.isNotEmpty())
        // Assert loading is false
        assertFalse(userListViewModel.state.value.isLoading)
    }

    @Test
    fun userListViewModel_loadUsers_errorOccurredEvent() = runTest {
        declare<UserRepository> {
            object : UserRepository {
                override suspend fun getUsers() =
                    Result.failure<List<User>>(IllegalStateException())

                override suspend fun getUser(userId: Long) =
                    Result.failure<User>(IllegalStateException())
            }
        }

        val userListViewModel: UserListViewModel by inject()

        val events = mutableListOf<UserListEvent>()
        var currentUiState = userListViewModel.state
        // Assert users list is empty
        assertTrue(currentUiState.value.users.isEmpty())
        // Assert user search is empty
        assertTrue(currentUiState.value.userQuery.isEmpty())
        // Assert loading is false
        assertFalse(currentUiState.value.isLoading)

        backgroundScope.launch(UnconfinedTestDispatcher()) {
            userListViewModel.event.collect {
                events.add(it)
            }
        }

        // Assert events has no event
        assertTrue(events.isEmpty())

        userListViewModel.loadUsers()
        advanceUntilIdle()

        currentUiState = userListViewModel.state
        // Assert users list is empty
        assertTrue(currentUiState.value.users.isEmpty())
        // Assert user search is empty
        assertTrue(currentUiState.value.userQuery.isEmpty())
        // Assert loading is false
        assertFalse(currentUiState.value.isLoading)
        // Assert events has one event
        assertTrue(events.size == 1)
        // Assert event is ErrorOccuredEvent
        assertTrue(events[0] is UserListEvent.ErrorOccurredEvent)
    }
}
