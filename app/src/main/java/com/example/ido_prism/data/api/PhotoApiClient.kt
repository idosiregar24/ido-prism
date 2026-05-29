package com.example.ido_prism.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PhotoApiClient {
    private const val PICSUM_URL = "https://picsum.photos/"
    private const val JSONPLACEHOLDER_URL = "https://jsonplaceholder.typicode.com/"

    val apiService: PhotoApiService by lazy {
        Retrofit.Builder()
            .baseUrl(PICSUM_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PhotoApiService::class.java)
    }

    val newsService: NewsApiService by lazy {
        Retrofit.Builder()
            .baseUrl(JSONPLACEHOLDER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApiService::class.java)
    }
}
