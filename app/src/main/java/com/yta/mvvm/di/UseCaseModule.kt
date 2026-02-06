package com.yta.mvvm.di

import com.yta.mvvm.domain.usecases.FilterUsersUseCase
import com.yta.mvvm.domain.usecases.GetUsersUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val useCaseModule = module {
    factoryOf(::GetUsersUseCase)
    factoryOf(::FilterUsersUseCase)
}