package com.levendoglu.newsapp.api

import com.levendoglu.newsapp.model.NewsModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("top-headlines")
    suspend fun getBreakingNews(
        @Query("country") country:String,
        @Query("apiKey") apiKey:String,
    ) : Response<NewsModel>
    @GET("everything")
    suspend fun searchNews(
        @Query("q") q:String,
        @Query("apiKey") apiKey:String
    ) : Response<NewsModel>
}