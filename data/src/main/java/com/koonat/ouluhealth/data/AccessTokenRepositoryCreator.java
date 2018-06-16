package com.koonat.ouluhealth.data;

import com.koonat.ouluhealth.domain.repository.AccessTokenRepository;

import retrofit2.Retrofit;

public class AccessTokenRepositoryCreator {
    public static AccessTokenRepository createAccessTokenRepository() {
        Retrofit retrofit = DependencyRegistryForData.provideRetrofitForAccessToken();
        return retrofit.create(AccessTokenRepository.class);
    }
}
