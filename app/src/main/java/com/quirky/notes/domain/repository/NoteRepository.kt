package com.quirky.notes.domain.repository

import com.quirky.notes.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getAllNotes(): Flow<List<Note>>
    fun getNoteById(noteId: Long): Flow<Note?>
    fun searchNotes(query: String): Flow<List<Note>>
    fun getNotesByTag(tag: String): Flow<List<Note>>
    suspend fun insertNote(note: Note): Long
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(noteId: Long)
}
