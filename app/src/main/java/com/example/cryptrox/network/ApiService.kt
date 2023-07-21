package com.example.cryptrox.network

import com.example.cryptrox.model.ParentModelClass
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiService {

    @Headers("X-CMC_PRO_API_KEY: 3069b872-06b0-40f1-bf44-5a3f7f73d796")
    @GET("/v1/cryptocurrency/listings/latest")
    suspend fun getCryptoData():Response<ParentModelClass>

}