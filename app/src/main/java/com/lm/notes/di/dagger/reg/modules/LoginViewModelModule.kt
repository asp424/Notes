package com.lm.notes.di.dagger.reg.modules

import androidx.lifecycle.ViewModel
import com.lm.notes.di.dagger.reg.RegScope
import com.lm.notes.di.dagger.app.modules.ViewModelKey
import com.lm.notes.presentation.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Module
interface LoginViewModelModule {
    @IntoMap
    @Binds
    @RegScope
    @ViewModelKey(LoginViewModel::class)
    fun bindsMainViewModel(viewModel: LoginViewModel): ViewModel
}