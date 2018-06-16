package com.koonat.ouluhealth.data;

import com.koonat.ouluhealth.domain.repository.PredictionRepository;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class PredictionRepositoryCreator {
    public static PredictionRepository createPredictionRepository(String token) {
        OkHttpClient okHttpClient = DependencyRegistryForData.provideOkHttpClient(token);
        Retrofit retrofit = DependencyRegistryForData.provideRetrofitForEtsimo(okHttpClient);
        return retrofit.create(PredictionRepository.class);
    }
}
