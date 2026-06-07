package com.example.ido_prism.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "agenda")
data class AgendaEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val namaKegiatan: String,
    val lokasi: String,
    val tanggal: String,
    val deskripsi: String
)