package com.lm.notes.utils

import android.util.Log

val <T> T.log get() = Log.d("My", toString())