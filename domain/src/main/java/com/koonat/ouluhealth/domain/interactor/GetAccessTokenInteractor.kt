package com.koonat.ouluhealth.domain.interactor

import com.koonat.ouluhealth.domain.UseCaseWithoutParameter
import com.koonat.ouluhealth.domain.model.TokenHolder
import com.koonat.ouluhealth.domain.repository.AccessTokenRepository
import io.reactivex.Single

class GetAccessTokenInteractor(private val accessTokenRepo: AccessTokenRepository): UseCaseWithoutParameter<TokenHolder> {
    override fun execute(): Single<TokenHolder> {
        return accessTokenRepo.accessToken
    }

}
