package com.quirky.notes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.quirky.notes.data.local.dao.NoteDao
import com.quirky.notes.data.local.entity.NoteEntity

@Database(
    entities = [NoteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}
