package com.example.pop_flake

import androidx.lifecycle.Observer

class TestObserver<T> : Observer<T> {
    private val observedValues = mutableListOf<T>()

    override fun onChanged(value: T) {
        observedValues.add(value)
    }

    fun assertValue(predicate: (T) -> Boolean) {
        val matchingValues = observedValues.filter(predicate)
        if (matchingValues.isEmpty()) {
            throw AssertionError("No value matching the predicate was observed.")
        }
    }
}
