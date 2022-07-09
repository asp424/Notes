package com.lm.notes.presentation

import android.content.Intent
import androidx.activity.ComponentActivity
import com.google.firebase.auth.FirebaseAuth
import com.lm.notes.di.dagger.DaggerRegComponent

abstract class BaseActivity : ComponentActivity() {

    protected val LoginActivity.regComponent
        get() = DaggerRegComponent.builder().loginActivity(this).create()

    val FirebaseAuth.isAuth get() = currentUser?.uid != null

    val startLoginActivity by lazy {
        startActivity(Intent(applicationContext, LoginActivity::class.java))
    }

    val LoginActivity.startMainActivity
        get() = run {
            startActivity(Intent(applicationContext, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                action = IS_AUTH_ACTION
            })
            this.finish()
        }

    companion object {
        const val IS_AUTH_ACTION = "isAuth"
    }
}
