package com.chus.clua.breakingbad.di

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val retrofitModule = module {

    single(named(DEFAULT_RETROFIT_CLIENT)) { (baseUrl: String?) ->
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get(named(DEFAULT_OK_HTTP_CLIENT)))
            .build()
    }

    single(named(DEFAULT_OK_HTTP_CLIENT)) {
        OkHttpClient().newBuilder()
            .connectTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
            )
            .build()
    }

}

const val DEFAULT_RETROFIT_CLIENT = "defaultRetrofitClient"
private const val READ_TIMEOUT = 10L
private const val WRITE_TIMEOUT = 60L
private const val DEFAULT_OK_HTTP_CLIENT = "defaultOkHttpClient"