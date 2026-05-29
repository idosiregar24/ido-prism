package com.example.ido_prism.data.model

data class NewsItem(
    val id: Int,
    val title: String,
    val body: String,
    val imageUrl: String,
    val date: String,
    val category: String,
    val author: String
)
