package com.yta.mvvm.presentation.userList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yta.mvvm.data.repositoryImpl.MockDb
import com.yta.mvvm.data.repositoryImpl.UserRepositoryImpl
import com.yta.mvvm.domain.User
import com.yta.mvvm.domain.usecases.FilterUsersUseCase
import com.yta.mvvm.domain.usecases.GetUsersUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserListViewModel() : ViewModel() {
    private val getUsersUseCase = GetUsersUseCase(UserRepositoryImpl(MockDb()))
    private val filterUsersUseCase = FilterUsersUseCase()

    private val _state = MutableStateFlow(UserListModel())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<UserListEvent>()
    val event = _event.asSharedFlow()

    fun loadUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true) }

            val usersResource = getUsersUseCase()

            when {
                usersResource.isSuccess -> {
                    _state.update {
                        it.copy(
                            allUsers = usersResource.getOrDefault(emptyList()),
                            filteredUsers = usersResource.getOrDefault(emptyList())
                        )
                    }
                }

                usersResource.isFailure -> {
                    _event.emit(UserListEvent.ErrorOccurredEvent)
                }
            }

            _state.update { it.copy(isLoading = false) }
        }
    }

    fun onAction(action: UserListAction) {
        when (action) {
            is UserListAction.OnUserClicked -> {
                viewModelScope.launch {
                    _event.emit(UserListEvent.OpenUserDetailEvent(action.userId))
                }
            }

            is UserListAction.OnUserSearch -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            isLoading = true,
                            userQuery = action.userQuery
                        )
                    }

                    val filteredUsersResource = filterUsersUseCase(
                        usersList = _state.value.allUsers,
                        userQuery = action.userQuery
                    )

                    when {
                        filteredUsersResource.isSuccess -> {
                            _state.update {
                                it.copy(
                                    filteredUsers = filteredUsersResource.getOrDefault(
                                        emptyList()
                                    )
                                )
                            }
                        }

                        filteredUsersResource.isFailure -> {
                            _event.emit(UserListEvent.ErrorOccurredEvent)
                        }
                    }

                    _state.update { it.copy(isLoading = false) }
                }
            }
        }
    }
}

data class UserListModel(
    val allUsers: List<User> = emptyList(),
    val filteredUsers: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val userQuery: String = ""
)

sealed interface UserListAction {
    data class OnUserSearch(val userQuery: String) : UserListAction
    data class OnUserClicked(val userId: Long) : UserListAction
}

sealed interface UserListEvent {
    data object ErrorOccurredEvent : UserListEvent
    data class OpenUserDetailEvent(val userId: Long) : UserListEvent
}

