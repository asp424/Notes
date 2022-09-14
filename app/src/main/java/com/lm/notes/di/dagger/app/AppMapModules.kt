package com.lm.notes.di.dagger.app

import com.lm.notes.di.dagger.app.modules.*
import com.lm.notes.di.dagger.app.modules.NotesDataBaseDaoModule
import dagger.Module
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Module(
    includes = [
        ViewModelFactoryModule::class,
        FBAuthInstanceModule::class,
        FirebaseRepositoryModule::class,
        FirebaseSourceModule::class,
        ValueListenerModule::class,
        ChildListenerModule::class,
        FirebaseReferenceModule::class,
        NotesMapperModule::class,
        NotesDataBaseDaoModule::class,
        NotesRepositoryModule::class,
        RoomRepositoryModule::class,
        NotesListDataModule::class,
        CoroutinesDispatcherModule::class,
        FilesProviderModule::class,
        ShortcutsModule::class,
        NoteDataModule::class,
        EditTextProviderModule::class,
        InputMethodManagerModule::class
    ]
)
interface AppMapModules

