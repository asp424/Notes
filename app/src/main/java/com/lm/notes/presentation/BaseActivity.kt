package com.lm.notes.presentation

import android.content.Intent
import androidx.activity.ComponentActivity
import com.lm.notes.di.dagger.reg.DaggerRegComponent

abstract class BaseActivity : ComponentActivity() {

    protected val LoginActivity.regComponent
        get() = DaggerRegComponent.builder().loginActivity(this).create()

    val startLoginActivity get() =
        startActivity(Intent(applicationContext, LoginActivity::class.java))

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
