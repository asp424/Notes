package com.lm.notes.di.dagger

import com.lm.notes.di.dagger.modules.app.NotesDataBaseDaoModule
import com.lm.notes.di.dagger.modules.app.*
import dagger.Module
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Module(
    includes = [
        ViewModelFactoryModule::class,
        ViewModelsModule::class,
        ScreensModule::class,
        ComposeDependenciesModule::class,
        ComposeValuesModule::class,
        FBAuthInstanceModule::class,
        FirebaseRepositoryModule::class,
        FirebaseHandlerModule::class,
        ValueListenerModule::class,
        ChildListenerModule::class,
        NotesHandlerModule::class,
        FirebaseReferenceModule::class,
        NotesMapperModule::class,
        NotesDataBaseDaoModule::class,
        NotesRepositoryModule::class,
        CalendarInstanceModule::class,
        TopBarModule::class,
        StateReceiverModule::class
    ]
)
interface AppMapModules

