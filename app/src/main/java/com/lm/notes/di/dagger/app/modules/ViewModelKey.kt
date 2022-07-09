package com.lm.notes.di.dagger.app.modules

import androidx.lifecycle.ViewModel
import com.lm.notes.di.dagger.app.AppScope
import dagger.MapKey
import kotlin.reflect.KClass

@MapKey
@AppScope
@Target(AnnotationTarget.FUNCTION)
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)
