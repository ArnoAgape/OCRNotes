package com.openclassrooms.notes

import com.openclassrooms.notes.model.data.Note
import com.openclassrooms.notes.model.data.repository.NotesRepository
import com.openclassrooms.notes.model.data.service.LocalNotesApiService
import com.openclassrooms.notes.widget.viewmodel.NoteViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class OCRNotesTest {

    private lateinit var viewModel: NoteViewModel
    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        val notesApiService = LocalNotesApiService()
        val notesRepository = NotesRepository(notesApiService)
        viewModel = NoteViewModel(notesRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain() // Très important pour éviter des effets de bord
    }

    @Test
    fun testAddNotes() {
        val note =  Note("title", "body")
        viewModel.addNote(note)

        assertEquals("title", note.title)
        assertEquals("body", note.body)
    }
}