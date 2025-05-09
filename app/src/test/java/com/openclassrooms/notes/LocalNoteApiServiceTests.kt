package com.openclassrooms.notes

import com.openclassrooms.notes.model.data.service.LocalNotesApiService
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class LocalNotesApiServiceTest {

    private val localNotesApiService = LocalNotesApiService()

    @Test
    fun compareAndCheckNotEmptyNotes() {

        val notes = localNotesApiService.getAllNotes()

        assertTrue(notes.isNotEmpty(), "The list of notes should not be empty")
        assertEquals(10, notes.size)

        assertEquals("titre 1", notes[0].title)
        assertEquals("corps 1", notes[0].body)

        assertEquals("titre 2", notes[1].title)
        assertEquals("corps 2", notes[1].body)
    }
}
