package com.koonat.ouluhealth.domain.interactor

import com.koonat.ouluhealth.domain.UseCase
import com.koonat.ouluhealth.domain.UseCaseWithoutParameter
import com.koonat.ouluhealth.domain.repository.AccessTokenRepository
import io.reactivex.Single

class GetAccessTokenInteractor(private val accessTokenRepo: AccessTokenRepository): UseCaseWithoutParameter<String> {
    override fun execute(): Single<String> {
        return accessTokenRepo.accessToken
    }

}
