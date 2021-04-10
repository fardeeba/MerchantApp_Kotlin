package com.example.merchantapp2

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private fun getRetrofit() : Retrofit
{
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

    val okHttpClient: OkHttpClient = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()

    val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl("https://api-stg.martcart.pk/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit
}

fun generateCallToAPI() : CallToAPI
{
    val callToAPI : CallToAPI = getRetrofit().create(CallToAPI::class.java)
    return callToAPI
}