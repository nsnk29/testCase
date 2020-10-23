package com.nsnk.testcase.data.retrofit

import com.nsnk.testcase.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain
        .request()
        .newBuilder()
        .header("Authorization", "Client-ID ${BuildConfig.IMGUR_CLIENT_ID}")
        .build()
        .let { chain.proceed(it) }
}