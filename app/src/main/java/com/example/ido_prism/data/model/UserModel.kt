package com.example.ido_prism.data.model

data class UserModel(
    val id: Int,
    val name: String,
    val company: Company
)

data class Company(
    val name: String
)
