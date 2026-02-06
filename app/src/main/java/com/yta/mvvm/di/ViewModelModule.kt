package com.yta.mvvm.di

import com.yta.mvvm.presentation.userList.UserListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::UserListViewModel)
}