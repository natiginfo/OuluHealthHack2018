package com.koonat.ouluhealth.domain

interface UseCaseWithParameter<P, R> {
    interface Callback<R> {
        fun onSuccess(result: R)
        fun onError(exception: Exception)
    }

    fun execute(parameter: P, callback: Callback<R>)
}

interface UseCaseWithoutParameter<R> {
    interface Callback<R> {
        fun onSuccess(result: R)
        fun onError(exception: Exception)
    }

    fun execute(callback: Callback<R>)
}

interface CompletableUseCaseWithParameter<P> {
    interface Callback {
        fun onSuccess()
        fun onError(exception: Exception)
    }

    fun execute(paremeter: P, callback: Callback)
}

interface CompletableUseCaseWithoutParameter {
    interface Callback {
        fun onSuccess()
        fun onError(exception: Exception)
    }

    fun execute(callback: Callback)
}