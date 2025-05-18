package com.todolist.app.core.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

/**
 * Only proceed with the given action if the coroutine has not been cancelled.
 * Necessary because Flow.collect receives items even after coroutine was cancelled
 * https://github.com/Kotlin/kotlinx.coroutines/issues/1265#issuecomment-568216706
 */
suspend inline fun <T> Flow<T>.safeCollect(crossinline action: suspend (T) -> Unit) {
    collect {
        coroutineContext.ensureActive()
        action(it)
    }
}

fun <T> Flow<T>.launchWhenStarted(
    lifecycleOwner: LifecycleOwner,
    action: suspend (T) -> Unit
) = with(lifecycleOwner) {
    lifecycleScope.launch {
        this@launchWhenStarted.flowWithLifecycle(
            lifecycle = lifecycleOwner.lifecycle
        ).collect {
            action.invoke(it)
        }
    }
}