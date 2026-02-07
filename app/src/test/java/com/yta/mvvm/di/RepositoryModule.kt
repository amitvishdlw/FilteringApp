package com.yta.mvvm.di

import com.yta.mvvm.data.repositoryImpl.UserRepositoryImpl
import com.yta.mvvm.domain.repository.UserRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::UserRepositoryImpl) bind UserRepository::class
}