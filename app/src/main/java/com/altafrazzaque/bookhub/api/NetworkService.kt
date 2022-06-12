package com.altafrazzaque.bookhub.api

import com.altafrazzaque.bookhub.BuildConfig
import com.altafrazzaque.bookhub.utilities.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkService {
    var api: ApiService

    init {
        val logger = HttpLoggingInterceptor()
        logger.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        val client = OkHttpClient.Builder()
            .connectTimeout(Constants.BASE_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Constants.BASE_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(Constants.BASE_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(logger)
            .build()

        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .build()

        api = retrofit.create(ApiService::class.java)
    }
}
