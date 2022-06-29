package com.lm.notes.di.dagger.modules.app

import androidx.lifecycle.ViewModel
import com.lm.notes.di.dagger.annotations.AppScope
import dagger.MapKey
import javax.inject.Singleton
import kotlin.reflect.KClass

@MapKey
@AppScope
@Target(AnnotationTarget.FUNCTION)
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)
