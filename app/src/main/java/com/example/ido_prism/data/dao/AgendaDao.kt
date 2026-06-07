package com.example.ido_prism.data.dao

import androidx.room.*
import com.example.ido_prism.data.entity.AgendaEntity

@Dao
interface AgendaDao {
    @Query("SELECT * FROM agenda ORDER BY id DESC")
    suspend fun getAll(): List<AgendaEntity>

    @Insert
    suspend fun insert(agenda: AgendaEntity)

    @Update
    suspend fun update(agenda: AgendaEntity)

    @Delete
    suspend fun delete(agenda: AgendaEntity)
}