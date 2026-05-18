package com.example.ido_prism.Proyek

data class DataProyek(
    val kode: String,
    val nama: String,
    val tahun: Int,
    val lokasi: String,
    val anggaran: String,
    val sumberDana: String,
    val latitude: Double,
    val longitude: Double,
    val deskripsi: String,
    val gambar: String? = null
)