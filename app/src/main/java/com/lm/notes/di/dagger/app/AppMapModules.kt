package com.lm.notes.di.dagger.app

import com.lm.notes.di.dagger.app.modules.*
import com.lm.notes.di.dagger.app.modules.NotesDataBaseDaoModule
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
        FirebaseSourceModule::class,
        ValueListenerModule::class,
        ChildListenerModule::class,
        FirebaseReferenceModule::class,
        NotesMapperModule::class,
        NotesDataBaseDaoModule::class,
        NotesRepositoryModule::class,
        CalendarInstanceModule::class,
        TopBarModule::class,
        MainColumnModule::class,
        NotesModule::class,
        BottomBarModule::class,
        RoomSourceModule::class
    ]
)
interface AppMapModules

