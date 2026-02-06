package com.yta.mvvm.di

import com.yta.mvvm.data.DataSource
import com.yta.mvvm.data.MockDb
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val commonModule = module {
    singleOf(::MockDb) bind DataSource::class
}