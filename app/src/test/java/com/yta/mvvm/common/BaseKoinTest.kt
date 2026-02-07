package com.yta.mvvm.common

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.koin.core.module.Module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule

@OptIn(ExperimentalCoroutinesApi::class)
abstract class BaseKoinTest : KoinTest {
    abstract val modules: List<Module>

    @get:Rule
    val koinRule = KoinTestRule.create {
        modules(modules)
    }

    @get:Rule
    val mainCoroutineTestRule = MainCoroutineTestRule()
}