package com.openclassrooms.notes.widget.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.notes.model.data.Note
import com.openclassrooms.notes.model.data.repository.NotesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.openclassrooms.notes.widget.NotesAdapter
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NoteViewModel(private val notesRepository: NotesRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(NoteUISTate())
    val uiState: StateFlow<NoteUISTate> = _uiState.asStateFlow()

    init {
        collectNotes()
    }

    fun addNote(note: Note) {
        viewModelScope.launch {
            notesRepository.addNote(note)
        }
    }

    /**
     * Collects notes from the repository and updates the adapter.
     */
    private fun collectNotes() = viewModelScope.launch {
        notesRepository.notes.onEach { noteUpdate ->
            _uiState.update { currentNote ->
                currentNote.copy(
                    listNotes = noteUpdate
                )
            }
        }
            .launchIn(viewModelScope)
    }

    data class NoteUISTate(
        val listNotes: List<Note> = emptyList()
    )
}