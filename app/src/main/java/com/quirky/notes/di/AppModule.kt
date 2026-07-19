package com.quirky.notes.di

import android.content.Context
import androidx.room.Room
import com.quirky.notes.data.local.NoteDatabase
import com.quirky.notes.data.local.dao.NoteDao
import com.quirky.notes.data.repository.NoteRepositoryImpl
import com.quirky.notes.domain.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNoteDatabase(
        @ApplicationContext context: Context
    ): NoteDatabase {
        return Room.databaseBuilder(
            context,
            NoteDatabase::class.java,
            "quirky_notes_db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideNoteDao(
        database: NoteDatabase
    ): NoteDao {
        return database.noteDao()
    }

    @Singleton
    @Provides
    fun provideNoteRepository(
        noteDao: NoteDao
    ): NoteRepository {
        return NoteRepositoryImpl(noteDao)
    }
}
