package com.example.ido_prism.data.api

import com.example.ido_prism.data.model.PhotoModel
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotoApiService {
    @GET("v2/list")
    suspend fun getPhotos(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): List<PhotoModel>
}
