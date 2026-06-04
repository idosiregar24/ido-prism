package com.example.ido_prism.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.ido_prism.data.entity.NoteEntity

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes")
    suspend fun getAll(): List<NoteEntity>

    @Insert
    suspend fun insert(note: NoteEntity)
}