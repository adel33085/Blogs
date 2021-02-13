package com.example.blogs.base.utils

import androidx.test.espresso.IdlingResource
import java.util.concurrent.atomic.AtomicInteger

class CountingIdlingResource(
        private val resourceName: String
) : IdlingResource {

    private val counter = AtomicInteger(0)

    @Volatile
    private var resourceCallback: IdlingResource.ResourceCallback? = null

    override fun getName() = resourceName

    override fun isIdleNow() = counter.get() == 0

    override fun registerIdleTransitionCallback(resourceCallback: IdlingResource.ResourceCallback) {
        this.resourceCallback = resourceCallback
    }

    fun increment() {
        counter.getAndIncrement()
    }

    fun decrement() {
        val counterValue = counter.decrementAndGet()
        if (counterValue == 0) {
            resourceCallback?.onTransitionToIdle()
        } else if (counterValue < 0) {
            throw IllegalStateException("Counter has been corrupted!")
        }
    }
}
