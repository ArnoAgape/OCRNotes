package com.openclassrooms.notes.model.data.repository

import android.util.Log
import com.openclassrooms.notes.model.data.Note
import com.openclassrooms.notes.model.data.service.LocalNotesApiService
import com.openclassrooms.notes.model.data.service.NotesApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toSet

/**
 * Repository class for the notes.
 */

class NotesRepository(notesApiService: NotesApiService = LocalNotesApiService()) {

    /**
     * A flow that emits a list of all notes.
     */
    private val _notes = MutableStateFlow(notesApiService.getAllNotes())
    val notes: StateFlow<List<Note>> = _notes.asStateFlow()

    fun addNote(note: Note) {
        _notes.value = listOf(note) + _notes.value
        Log.d("RepoNote", "addNote: $notes")
    }
}