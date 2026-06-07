package com.example.ido_prism.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ido_prism.data.dao.NoteDao
import com.example.ido_prism.data.dao.AgendaDao
import com.example.ido_prism.data.dao.AspirasiDao
import com.example.ido_prism.data.entity.NoteEntity
import com.example.ido_prism.data.entity.AgendaEntity
import com.example.ido_prism.data.entity.AspirasiEntity

@Database(
    entities = [NoteEntity::class, AgendaEntity::class, AspirasiEntity::class],
    version = 3
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao
    abstract fun agendaDao(): AgendaDao
    abstract fun aspirasiDao(): AspirasiDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration() // Menghindari crash saat ganti versi
                    .build().also { INSTANCE = it }
            }
        }
    }
}