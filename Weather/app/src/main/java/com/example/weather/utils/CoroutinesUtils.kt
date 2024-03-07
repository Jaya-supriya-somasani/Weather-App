package com.example.weather.utils

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStateAtLeast
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun LifecycleOwner.safeLaunchWhenResumed(
    showToastOnError: Boolean = true,
    block: suspend CoroutineScope.() -> Unit
) = safeLaunch {
    lifecycle.whenStateAtLeast(Lifecycle.State.RESUMED) {
        try {
            block()
        } catch (e: Exception) {
            if (showToastOnError) {
                Log.d("error", e.toString())
            }
            throw e
        }
    }
}

val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
    if (throwable is CancellationException) return@CoroutineExceptionHandler
    throwable.printStackTrace()
    Log.d("throwable", "coroutineExceptionHandler")
}

fun LifecycleOwner.safeLaunch(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    showToastOnError: Boolean = true,
    block: suspend CoroutineScope.() -> Unit
): Job {
    return lifecycleScope.safeLaunch(context, start) {
        try {
            block()
        } catch (e: Exception) {
            Log.d("Exception", "Exception $e")
            throw e
        }
    }
}

fun CoroutineScope.safeLaunch(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
): Job {
    return launch(context + coroutineExceptionHandler, start, block)
}