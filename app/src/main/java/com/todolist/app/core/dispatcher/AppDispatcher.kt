package com.todolist.app.core.dispatcher

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Don't hardcode Dispatchers when creating new coroutines or calling withContext .
 * This dependency injection pattern makes testing easier as you can replace those
 * dispatchers in unit and instrumentation tests with a test dispatcher to make your tests
 * more deterministic.
 *
 * https://developer.android.com/kotlin/coroutines/coroutines-best-practices
 */
class AppDispatcher : DispatcherProvider {
    override fun ui(): CoroutineDispatcher = Dispatchers.Main
    override fun main(): CoroutineDispatcher = Dispatchers.Default
    override fun io(): CoroutineDispatcher = Dispatchers.IO
}