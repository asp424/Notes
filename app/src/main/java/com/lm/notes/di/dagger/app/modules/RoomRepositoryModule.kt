package com.lm.notes.di.dagger.app.modules

import com.lm.notes.data.rerositories.RoomRepository
import com.lm.notes.di.dagger.app.AppScope
import dagger.Binds
import dagger.Module

@Module
interface RoomRepositoryModule {

    @Binds
    @AppScope
    fun bindsRoomRepository(roomRepository: RoomRepository.Base): RoomRepository
}