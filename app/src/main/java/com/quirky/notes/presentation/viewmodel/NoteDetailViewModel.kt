package com.quirky.notes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quirky.notes.domain.model.Note
import com.quirky.notes.domain.repository.NoteRepository
import com.quirky.notes.domain.usecase.AutoTagUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject

data class NoteDetailUiState(
    val note: Note? = null,
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val error: String? = null,
    val savedSuccessfully: Boolean = false
)

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val autoTagUseCase: AutoTagUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(NoteDetailUiState())
    val uiState: StateFlow<NoteDetailUiState> = _uiState.asStateFlow()

    fun loadNote(noteId: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                noteRepository.getNoteById(noteId).collect { note ->
                    _uiState.update { state ->
                        state.copy(
                            note = note,
                            isLoading = false
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    fun createNewNote() {
        _uiState.update { state ->
            state.copy(
                note = Note(
                    id = 0,
                    title = "",
                    content = "",
                    createdAt = Instant.now(),
                    updatedAt = Instant.now()
                )
            )
        }
    }

    fun updateNoteTitle(title: String) {
        _uiState.update { state ->
            state.copy(
                note = state.note?.copy(title = title)
            )
        }
    }

    fun updateNoteContent(content: String) {
        _uiState.update { state ->
            state.copy(
                note = state.note?.copy(content = content)
            )
        }
    }

    fun appendToContent(text: String) {
        _uiState.update { state ->
            state.copy(
                note = state.note?.copy(content = state.note.content + text)
            )
        }
    }

    fun saveNote() {
        val currentNote = _uiState.value.note ?: return
        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }
            try {
                // Auto-tag the note
                val autoTags = autoTagUseCase.autoTag(currentNote.content)
                val noteWithTags = currentNote.copy(
                    tags = autoTags,
                    updatedAt = Instant.now()
                )

                if (noteWithTags.id == 0L) {
                    noteRepository.insertNote(noteWithTags)
                } else {
                    noteRepository.updateNote(noteWithTags)
                }

                _uiState.update { state ->
                    state.copy(
                        isSaving = false,
                        savedSuccessfully = true,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update { state ->
                    state.copy(
                        isSaving = false,
                        error = e.message
                    )
                }
            }
        }
    }

    fun togglePin() {
        _uiState.update { state ->
            state.copy(
                note = state.note?.copy(isPinned = state.note.isPinned.not())
            )
        }
    }

    fun clearSavedState() {
        _uiState.update { it.copy(savedSuccessfully = false) }
    }
}
