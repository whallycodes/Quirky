package com.quirky.notes.data.repository

import com.quirky.notes.data.local.dao.NoteDao
import com.quirky.notes.data.local.entity.NoteEntity
import com.quirky.notes.domain.model.Note
import com.quirky.notes.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Instant
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
) : NoteRepository {

    override fun getAllNotes(): Flow<List<Note>> =
        noteDao.getAllNotes().map { entities ->
            entities.map { it.toDomain() }
        }

    override fun getNoteById(noteId: Long): Flow<Note?> =
        noteDao.getNoteById(noteId).map { entity ->
            entity?.toDomain()
        }

    override fun searchNotes(query: String): Flow<List<Note>> =
        noteDao.searchNotes(query).map { entities ->
            entities.map { it.toDomain() }
        }

    override fun getNotesByTag(tag: String): Flow<List<Note>> =
        noteDao.getNotesByTag(tag).map { entities ->
            entities.map { it.toDomain() }
        }

    override suspend fun insertNote(note: Note): Long =
        noteDao.insertNote(note.toEntity())

    override suspend fun updateNote(note: Note) =
        noteDao.updateNote(note.toEntity())

    override suspend fun deleteNote(noteId: Long) =
        noteDao.deleteNoteById(noteId)

    private fun NoteEntity.toDomain() = Note(
        id = id,
        title = title,
        content = content,
        tags = tags.split(",").filter { it.isNotBlank() },
        createdAt = Instant.ofEpochMilli(createdAt),
        updatedAt = Instant.ofEpochMilli(updatedAt),
        isPinned = isPinned
    )

    private fun Note.toEntity() = NoteEntity(
        id = id,
        title = title,
        content = content,
        tags = tags.joinToString(","),
        createdAt = createdAt.toEpochMilli(),
        updatedAt = updatedAt.toEpochMilli(),
        isPinned = isPinned
    )
}
