package com.manohar.mobiuscodechallenge.data.remote

import com.manohar.mobiuscodechallenge.model.CouponCodeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiService {

    /**
     * Fetch all the bonus codes.
     */
    @Headers("Content-Type: application/json")
    @GET("4c663239-03af-49b5-bcb3-0b0c41565bd2")
    suspend fun getBonusCodes(): Response<CouponCodeResponse>
}