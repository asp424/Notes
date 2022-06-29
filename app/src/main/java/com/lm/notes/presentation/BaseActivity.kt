package com.lm.notes.presentation

import android.content.Intent
import androidx.activity.ComponentActivity
import com.google.firebase.auth.FirebaseAuth

abstract class BaseActivity : ComponentActivity() {

    protected val fBAuth by lazy { FirebaseAuth.getInstance() }

    protected val isAuth by lazy { fBAuth.currentUser?.uid == null }

    protected val startLoginActivity by lazy {
        startActivity(Intent(this, LoginActivity::class.java))
    }

}