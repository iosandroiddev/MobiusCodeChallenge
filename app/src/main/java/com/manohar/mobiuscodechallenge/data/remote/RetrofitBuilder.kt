package com.manohar.mobiuscodechallenge.data.remote

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private const val baseUrl = "https://run.mocky.io/v3/"

    /**
     * Initialized Retrofit Builder
     */
    private val retrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
    }

    /**
     * Binding Interface to Retrofit Builder
     */
    val apiService: ApiService by lazy {
        retrofitBuilder.build().create(ApiService::class.java)
    }

}