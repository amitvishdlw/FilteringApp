package com.yta.mvvm.di

import com.yta.mvvm.presentation.userList.UserListViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        UserListViewModel(
            get(),
            get(),
            Dispatchers.IO
        )
    }
}