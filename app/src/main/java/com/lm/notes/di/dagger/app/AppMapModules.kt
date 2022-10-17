package com.lm.notes.di.dagger.app

import com.lm.notes.di.dagger.app.modules.*
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
        InputMethodManagerModule::class,
        EditTextControllerModule::class,
        UiStatesModule::class,
        ClipboardManagerModule::class,
        ClipboardProviderModule::class,
        SharedPreferencesModule::class,
        SPreferencesModule::class,
        AppWidgetManagerModule::class,
        IntentControllerModule::class,
        ComponentNameModule::class,
        PendingIntentModule::class,
        NoteAppWidgetControllerModule::class,
        CallbackEditTextModule::class,
        AccessibilityDelegateModule::class
    ]
)
interface AppMapModules

