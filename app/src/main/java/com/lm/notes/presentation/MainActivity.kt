package com.lm.notes.presentation

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.app.ShareCompat
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.lm.notes.R
import com.lm.notes.core.appComponentBuilder
import com.lm.notes.data.local_data.FilesProvider
import com.lm.notes.data.local_data.SPreferences
import com.lm.notes.databinding.EditTextBinding
import com.lm.notes.di.compose.mainScreenDependencies
import com.lm.notes.ui.screens.MainScreen
import com.lm.notes.ui.theme.NotesTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: dagger.Lazy<ViewModelFactory>

    private val notesViewModel by viewModels<NotesViewModel> { viewModelFactory.get() }

    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ashole)
        appComponentBuilder.intentBuilder { ShareCompat.IntentBuilder(this) }
            .editText(EditTextBinding.inflate(LayoutInflater.from(this)).root)
            .create().inject(this)
        if (intent.action.toString() == IS_AUTH_ACTION) {
            notesViewModel.synchronize(lifecycleScope)
        }
            /*
        val textView = findViewById<TextView>(R.id.ass)
        val text = "Здравствуйте, идите на хуй, а потом в пизду"
        val spannableString = SpannableString(text)
        val clickableSpan1: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                Toast.makeText(this@MainActivity, "Здравствуйте", Toast.LENGTH_SHORT).show()
            }
        }
        val clickableSpan2: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                Toast.makeText(this@MainActivity, "хуй", Toast.LENGTH_SHORT).show()
            }
        }
        val clickableSpan3: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                Toast.makeText(this@MainActivity, "пизду", Toast.LENGTH_SHORT).show()
            }
        }
        spannableString.setSpan(clickableSpan1, 0, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(clickableSpan2, 23, 26, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(clickableSpan3, 38, 43, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.setText(spannableString, TextView.BufferType.SPANNABLE)
        textView.movementMethod = LinkMovementMethod.getInstance()

             */
    }

    @Inject
    fun start(
        sPreferences: SPreferences,
        viewModelFactory: ViewModelFactory,
        firebaseAuth: FirebaseAuth,
        filesProvider: FilesProvider
    ) {
        setContent {
            NotesTheme(viewModelFactory = viewModelFactory) {
                mainScreenDependencies(sPreferences, viewModelFactory, firebaseAuth, filesProvider)
                { MainScreen() }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        lifecycleScope.launchWhenResumed {
            delay(300)
            with(notesViewModel.uiStates) {
                notesViewModel.spansProvider.setSelection()
                notesViewModel.clipboardProvider.clipBoardIsNotEmpty?.setClipboardIsEmpty
            }
        }
    }

    override fun onPause() {
        super.onPause()
         CoroutineScope(IO).launch { notesViewModel.updateChangedNotes() }
    }
}

