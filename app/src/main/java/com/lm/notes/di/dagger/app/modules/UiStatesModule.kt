package com.lm.notes.di.dagger.app.modules

import androidx.compose.ui.graphics.Color
import com.google.firebase.auth.FirebaseAuth
import com.lm.notes.data.local_data.SPreferences
import com.lm.notes.data.models.UiStates
import com.lm.notes.di.dagger.app.AppScope
import dagger.Module
import dagger.Provides

@Module
class UiStatesModule {

    @Provides
    @AppScope
    fun provideUiStates(sPreferences: SPreferences, firebaseAuth: FirebaseAuth) =
        UiStates().apply {
            Color(sPreferences.readMainColor()).setMainColor
            Color(sPreferences.readSecondColor()).setSecondColor
            (firebaseAuth.currentUser?.uid != null).setIsAuth
        }
}
