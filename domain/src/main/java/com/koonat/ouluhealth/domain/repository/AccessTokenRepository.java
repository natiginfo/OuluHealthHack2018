package com.koonat.ouluhealth.domain.repository;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface AccessTokenRepository {
    @GET("/token")
    public Single<String> getAccessToken();
}
