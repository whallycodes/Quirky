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

data class NoteListUiState(
    val notes: List<Note> = emptyList(),
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val selectedTag: String? = null,
    val error: String? = null
)

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val autoTagUseCase: AutoTagUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(NoteListUiState())
    val uiState: StateFlow<NoteListUiState> = _uiState.asStateFlow()

    init {
        loadNotes()
    }

    private fun loadNotes() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                noteRepository.getAllNotes().collect { notes ->
                    _uiState.update { state ->
                        state.copy(
                            notes = notes,
                            isLoading = false
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    fun search(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        viewModelScope.launch {
            if (query.isBlank()) {
                loadNotes()
            } else {
                noteRepository.searchNotes(query).collect { notes ->
                    _uiState.update { state ->
                        state.copy(notes = notes)
                    }
                }
            }
        }
    }

    fun filterByTag(tag: String) {
        _uiState.update { it.copy(selectedTag = tag) }
        viewModelScope.launch {
            noteRepository.getNotesByTag(tag).collect { notes ->
                _uiState.update { state ->
                    state.copy(notes = notes)
                }
            }
        }
    }

    fun clearFilters() {
        _uiState.update { it.copy(selectedTag = null, searchQuery = "") }
        loadNotes()
    }

    fun deleteNote(noteId: Long) {
        viewModelScope.launch {
            try {
                noteRepository.deleteNote(noteId)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }
}
