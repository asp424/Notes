package com.lm.notes.di.dagger.app.modules

import com.lm.notes.data.local_data.FilesProvider
import com.lm.notes.di.dagger.app.AppScope
import dagger.Binds
import dagger.Module

@Module
interface FilesProviderModule {

    @Binds
    @AppScope
    fun bindFilesProvider(filesProvider: FilesProvider.Base): FilesProvider
}