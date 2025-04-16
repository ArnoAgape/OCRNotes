package com.openclassrooms.notes

import android.app.Application
import com.openclassrooms.notes.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.core.context.GlobalContext.startKoin

class OCRNotesApplication : Application(), KoinComponent {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@OCRNotesApplication)
            modules(appModule)
        }
    }
}