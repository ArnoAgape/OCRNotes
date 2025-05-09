package com.openclassrooms.notes

import app.cash.turbine.test
import app.cash.turbine.testIn
import app.cash.turbine.turbineScope
import com.openclassrooms.notes.model.data.Note
import com.openclassrooms.notes.model.data.repository.NotesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class NotesRepositoryTest {

    @Test
    fun addNoteOnTheFirstRow() = runTest {

        val repository = NotesRepository()
        repository.notes.test {

            awaitItem()

            val newNote = Note(
                title = "Nouveau titre",
                body = "Nouveau corps"
            )
            repository.addNote(newNote)

            val updatedNotes = awaitItem()

            assertEquals(newNote, updatedNotes.first())

            cancelAndIgnoreRemainingEvents()
        }
    }
}
