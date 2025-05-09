package com.openclassrooms.notes

import app.cash.turbine.test
import com.openclassrooms.notes.widget.viewmodel.NoteViewModel
import com.openclassrooms.notes.model.data.Note
import com.openclassrooms.notes.model.data.repository.NotesRepository
import com.openclassrooms.notes.model.data.service.LocalNotesApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

@OptIn(ExperimentalCoroutinesApi::class)
class NoteViewModelTest {

    private lateinit var viewModel: NoteViewModel
    private val testDispatcher = StandardTestDispatcher()
    private val fakeNotesFlow = MutableStateFlow<List<Note>>(emptyList())

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        val notesApiService = LocalNotesApiService()
        val notesRepository = NotesRepository(notesApiService)
        viewModel = NoteViewModel(notesRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun checkNotes() = runTest {

        viewModel.uiState.test {

            assertEquals(emptyList<Note>(), awaitItem().listNotes)

            val note1 = Note(title = "titre 1", body = "corps 1")
            val note2 = Note(title = "titre 2", body = "corps 2")
            fakeNotesFlow.value = listOf(note1, note2)

            val actualNotes = awaitItem().listNotes

            assertTrue(actualNotes.containsAll(listOf(note1, note2)))
            cancelAndIgnoreRemainingEvents()
        }
    }
}
