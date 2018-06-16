package com.koonat.ouluhealth.domain.repository;

import com.koonat.ouluhealth.domain.model.TokenHolder;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface AccessTokenRepository {
    @GET("/token")
    public Single<TokenHolder> getAccessToken();
}
