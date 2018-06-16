package com.koonat.ouluhealth.domain

import io.reactivex.Completable
import io.reactivex.Single

interface UseCase<P, R> {
    fun execute(param: P): Single<R>
}

interface UseCaseWithoutParameter<R> {
    fun execute(): Single<R>
}


interface CompletableUseCase<P> {
    fun execute(param: P): Completable
}

interface CompletableUseCaseWithoutParameter {
    fun execute(): Completable
}