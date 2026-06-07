package com.example.ido_prism.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "aspirasi")
data class AspirasiEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val kategori: String,
    val isiAspirasi: String,
    val tanggal: String,
    val status: String = "Draft" // Draft, Terkirim, Diproses
)