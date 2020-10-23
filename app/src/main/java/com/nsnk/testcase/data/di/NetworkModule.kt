package com.nsnk.testcase.data.di

import android.util.Log
import com.nsnk.testcase.data.retrofit.AuthorizationInterceptor
import com.nsnk.testcase.data.retrofit.ImgurApi
import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
object NetworkModule {
    private const val BASE_URL = "https://api.imgur.com/"

    @Provides
    @Singleton
    internal fun provideImgurApi(retrofit: Retrofit): ImgurApi {
        return retrofit.create(ImgurApi::class.java)
    }

    @Provides
    @Singleton
    internal fun provideRetrofitInterface(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createHttpClient().build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    }

    @Provides
    @Singleton
    fun createHttpClient(): OkHttpClient.Builder {
        val authorizationInterceptor = AuthorizationInterceptor()
        val logger = HttpLoggingInterceptor {
            Log.d("M_NetworkModule", it)
        }.apply { level = HttpLoggingInterceptor.Level.BASIC }

        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(authorizationInterceptor)
            .addInterceptor(logger)
    }
}