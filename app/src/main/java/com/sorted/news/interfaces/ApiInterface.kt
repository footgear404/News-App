package com.sorted.news.interfaces

import com.sorted.news.clases.models.ArticlesList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Interface {
    @GET("top-headlines")
    fun getNews(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String
    ): Call<ArticlesList>

    @GET("everything")
    fun getNewsSearch(
        @Query("q") keyword: String,
        @Query("language") language: String,
        @Query("sortBy") sortBy: String,
        @Query("apiKey") apiKey: String

    ): Call<ArticlesList>
}