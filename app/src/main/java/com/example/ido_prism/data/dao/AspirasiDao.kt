package com.example.ido_prism.data.dao

import androidx.room.*
import com.example.ido_prism.data.entity.AspirasiEntity

@Dao
interface AspirasiDao {
    @Query("SELECT * FROM aspirasi ORDER BY id DESC")
    suspend fun getAll(): List<AspirasiEntity>

    @Insert
    suspend fun insert(aspirasi: AspirasiEntity)

    @Update
    suspend fun update(aspirasi: AspirasiEntity)

    @Delete
    suspend fun delete(aspirasi: AspirasiEntity)
}