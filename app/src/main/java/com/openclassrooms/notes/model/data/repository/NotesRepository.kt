package com.openclassrooms.notes.model.data.repository

import com.openclassrooms.notes.model.data.Note
import com.openclassrooms.notes.model.data.service.LocalNotesApiService
import com.openclassrooms.notes.model.data.service.NotesApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


/**
 * Repository class for the notes.
 */

class NotesRepository(notesApiService: NotesApiService = LocalNotesApiService()) {

    /**
     * A flow that emits a list of all notes.
     */
    private val _notes = MutableStateFlow(notesApiService.getAllNotes())
    val notes: Flow<List<Note>> = _notes.asStateFlow()

    fun addNote(note: Note) {
        _notes.value = listOf(note) + _notes.value
    }
}