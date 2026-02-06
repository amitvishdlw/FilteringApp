package com.yta.mvvm.di

fun appModule() = listOf(
    commonModule,
    repositoryModule,
    useCaseModule,
    viewModelModule
)