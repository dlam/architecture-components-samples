package com.example.benchmark

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import java.util.*
import kotlin.coroutines.CoroutineContext

/**
 * Flushable [CoroutineDispatcher] which retains knowledge of queued tasks for state guarantee while
 * under test.
 */
class TestDispatcher : CoroutineDispatcher() {
    val runnables = LinkedList<Runnable>()

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        runnables.add(block)
    }

    fun flush() {
        var block: Runnable? = runnables.poll()
        while (block != null) {
            block.run()
            block = runnables.poll()
        }
    }
}