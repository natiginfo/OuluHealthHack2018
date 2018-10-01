package com.koonat.ouluhealth.data;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class DependencyRegistryForData {

    private static volatile DependencyRegistryForData mInstance;

    private DependencyRegistryForData() {
    }

    public static DependencyRegistryForData getInstance() {
        if (mInstance == null) {
            synchronized (DependencyRegistryForData.class) {
                if (mInstance == null) {
                    mInstance = new DependencyRegistryForData();
                }
            }
        }
        return mInstance;
    }

    //region Singleton OkHttp, because it's expensive to create
    private volatile static OkHttpClient okHttpInstance;

    public static OkHttpClient provideOkHttpClient(final String token) {
        if (okHttpInstance == null) {
            synchronized (DependencyRegistryForData.class) {
                if (okHttpInstance == null) {
                    OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
                    okHttpBuilder.addInterceptor(chain -> {
                        Request request = chain
                                .request()
                                .newBuilder()
                                .addHeader("Authorization", "Bearer " + token)
                                .build();
                        return chain.proceed(request);
                    });

                    okHttpInstance = okHttpBuilder.build();
                }
            }
        }

        return okHttpInstance;
    }
    //endregion

    //region Singleton Retrofit for Etsimo
    private volatile static Retrofit retrofitForEtsimo;

    public static Retrofit provideRetrofitForEtsimo(OkHttpClient okHttpClient) {
        if (retrofitForEtsimo == null) {
            synchronized (DependencyRegistryForData.class) {
                if (retrofitForEtsimo == null) {
                    Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                            .baseUrl(BuildConfig.API_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .client(okHttpClient);

                    retrofitForEtsimo = retrofitBuilder.build();
                }
            }
        }
        return retrofitForEtsimo;
    }

    //endregion
    private volatile static Retrofit retrofitForAccessToken;

    public static Retrofit provideRetrofitForAccessToken() {
        if (retrofitForAccessToken == null) {
            synchronized (DependencyRegistryForData.class) {
                if (retrofitForAccessToken == null) {
                    Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                            .baseUrl(BuildConfig.API_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

                    retrofitForAccessToken = retrofitBuilder.build();
                }
            }
        }
        return retrofitForAccessToken;
    }
}
