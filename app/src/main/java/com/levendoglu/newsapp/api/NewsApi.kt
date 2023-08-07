package com.levendoglu.newsapp.api

import com.levendoglu.newsapp.model.NewsModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("top-headlines")
    fun getBreakingNews(
        @Query("country") country:String,
        @Query("apiKey") apiKey:String,
    ) : Call<NewsModel>
    @GET("everything")
    fun searchNews(
        @Query("q") q:String,
        @Query("apiKey") apiKey:String
    ) : Call<NewsModel>
}