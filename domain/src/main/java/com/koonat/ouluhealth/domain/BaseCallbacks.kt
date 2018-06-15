package com.koonat.ouluhealth.domain

interface CallbackWithResult<R> {
    fun onSuccess(result: R)
    fun onError(exception: Exception)
}

interface CallbackWithoutResult {
    fun onSuccess()
    fun onError(exception: Exception)
}