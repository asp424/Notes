package com.lm.notes.di.dagger.app.modules

import com.lm.notes.data.sources.RoomSource
import com.lm.notes.di.dagger.app.AppScope
import dagger.Binds
import dagger.Module

@Module
interface RoomSourceModule {

    @Binds
    @AppScope
    fun bindsRoomRepository(roomRepository: RoomSource.Base): RoomSource
}