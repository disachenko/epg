package com.noriginmedia.epg.di;


import android.content.Context;

import com.bluelinelabs.logansquare.LoganSquare;
import com.github.aurae.retrofit2.LoganSquareConverterFactory;
import com.noriginmedia.epg.BuildConfig;
import com.noriginmedia.epg.data.network.NetworkDataSource;
import com.noriginmedia.epg.data.network.converters.TimestampConverter;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    static OkHttpClient provideHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(BuildConfig.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(BuildConfig.READ_TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    static Converter.Factory provideLoganConverterFactory() {
        LoganSquare.registerTypeConverter(Long.class, new TimestampConverter());
        return LoganSquareConverterFactory.create();
    }

    @Provides
    @Singleton
    static Retrofit provideRetrofit(OkHttpClient okHttpClient, Converter.Factory converterFactory) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BACKEND_HOST)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(converterFactory)
                .build();
    }

    @Provides
    @Singleton
    static NetworkDataSource provideNetworkDataSource(Context context, Retrofit retrofit) {
        return new NetworkDataSource(context, retrofit);
    }

}
