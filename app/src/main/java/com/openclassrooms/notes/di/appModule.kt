package com.openclassrooms.notes.di

import com.openclassrooms.notes.model.data.repository.NotesRepository
import com.openclassrooms.notes.model.data.service.LocalNotesApiService
import com.openclassrooms.notes.widget.viewmodel.NoteViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { NotesRepository(notesApiService = LocalNotesApiService())}
    viewModel { NoteViewModel(get()) }
}