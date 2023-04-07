package com.criticalgnome.cleanarchitecturedemo

import com.criticalgnome.cleanarchitecturedemo.dispatcher.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher

@ExperimentalCoroutinesApi
class TestDispatcherProvider : DispatcherProvider {
    override val main: CoroutineDispatcher get() = UnconfinedTestDispatcher()
    override val io: CoroutineDispatcher get() = UnconfinedTestDispatcher()
    override val default: CoroutineDispatcher get() = UnconfinedTestDispatcher()
}
