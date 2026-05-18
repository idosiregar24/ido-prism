package com.example.ido_prism.Proyek

data class DataKontraktor(
    val id: String,
    val nama: String,
    val perusahaan: String,
    val alamat: String,
    val spesialisasi: String,
    val rating: Float,
    val gambar: Int? = null
)