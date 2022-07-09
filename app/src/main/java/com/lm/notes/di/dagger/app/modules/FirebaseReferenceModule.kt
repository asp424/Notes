package com.lm.notes.di.dagger.app.modules

import com.google.firebase.database.FirebaseDatabase
import com.lm.notes.di.dagger.app.AppScope
import dagger.Module
import dagger.Provides

@Module
class FirebaseReferenceModule {

    @Provides
    @AppScope
    fun providesFirebaseReference() = FirebaseDatabase.getInstance().reference
}