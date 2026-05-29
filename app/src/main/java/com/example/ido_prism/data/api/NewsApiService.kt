package com.example.ido_prism.data.api

import com.example.ido_prism.data.model.NewsModel
import com.example.ido_prism.data.model.UserModel
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("posts")
    suspend fun getNews(
        @Query("_limit") limit: Int = 30
    ): List<NewsModel>

    @GET("users")
    suspend fun getUsers(): List<UserModel>
}
