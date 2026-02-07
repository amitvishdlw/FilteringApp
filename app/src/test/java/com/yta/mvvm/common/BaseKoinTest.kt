package com.yta.mvvm.common

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.core.module.Module
import org.koin.test.KoinTest

@OptIn(ExperimentalCoroutinesApi::class)
abstract class BaseKoinTest : KoinTest {
    abstract val modules: List<Module>

    @Before
    fun setup() {
        startKoin {
            modules(modules)
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @get:Rule
    val mainCoroutineTestRule = MainCoroutineTestRule()
}