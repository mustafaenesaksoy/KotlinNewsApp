package com.enesaksoy.kotlinnewsapp.api

import com.enesaksoy.kotlinnewsapp.response.NewResponse
import com.enesaksoy.kotlinnewsapp.util.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    @GET("v2/top-headlines")
    suspend fun getNewsApi(
        @Query("country") country : String = "us",
        @Query("apiKey") apikey : String = API_KEY
    ) :Response<NewResponse>
}