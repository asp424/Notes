package com.lm.notes.presentation

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.lm.notes.core.IntentController
import com.lm.notes.core.appComponent
import com.lm.notes.data.local_data.FilesProvider
import com.lm.notes.data.local_data.SPreferences
import com.lm.notes.data.local_data.ShareType
import com.lm.notes.di.compose.MainScreenDependencies
import com.lm.notes.ui.cells.view.app_widget.NoteAppWidgetController
import com.lm.notes.ui.screens.MainScreen
import com.lm.notes.ui.theme.NotesTheme
import com.lm.notes.utils.log
import com.lm.notes.utils.longToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin


class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: dagger.Lazy<ViewModelFactory>

    @Inject
    lateinit var sPreferences: SPreferences

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var filesProvider: FilesProvider

    @Inject
    lateinit var noteAppWidgetController: NoteAppWidgetController

    @Inject
    lateinit var intentController: IntentController

    private val notesViewModel by viewModels<NotesViewModel> { viewModelFactory.get() }

    @RequiresApi(Build.VERSION_CODES.Q)
    private val takeImageResult =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia())
        {

        }

    val chooseFolderPath =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        {
            it.data?.data?.apply {
                val file = filesProvider.saveText(
                    0, notesViewModel.editTextController.editText.text.toString(),
                    ShareType.AsTxt
                )

                filesProvider.saveFile(this, file, contentResolver)

                it.data?.also {int ->
                    longToast(filesProvider.getFolderNameFromUri(int)) }
            }
        }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //takeImageResult.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        appComponent.inject(this)
    }

    override fun onResume() {
        super.onResume()
        start(intent)
        intent?.action = ""
        notesViewModel.clipboardProvider.addListener()
        with(notesViewModel) { uiStates.setSelection(lifecycleScope, this) }
    }

    override fun onPause() {
        super.onPause()
        notesViewModel.clipboardProvider.removeListener()
        CoroutineScope(IO).launch { notesViewModel.updateChangedNotes() }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        start(intent)
        intent?.action = ""
    }

    fun start(intent: Intent?) {
        intentController.checkForIntentAction(intent, notesViewModel, lifecycleScope)
        {
            setContent {
                NotesTheme(viewModelFactory = viewModelFactory.get()) {
                    MainScreenDependencies(
                        sPreferences,
                        viewModelFactory.get(),
                        firebaseAuth,
                        filesProvider,
                        noteAppWidgetController
                    ) { MainScreen(it) }
                }
            }
        }
    }

    @Composable
    fun Main() {
        LocalDensity.current.apply {
            val offset = 19f
            val lines = 4
            val delay = suspend { delay(15L) }
            val listOffsets = remember {
                mutableListOf<Float>().apply { (0 until lines).forEach { add(it * offset) } }
            }
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

                remember { mutableStateListOf<Offset>() }.apply {

                    Canvas(Modifier) { forEach { drawCircle(Color.Black, 5f, it) } }

                    LaunchedEffect(true) {
                        var n = 0f
                        (0..10000).asFlow().onEach { delay.invoke() }.collect {
                            n += 0.01f
                            listOffsets.forEach { add(n.offset(it)) }
                        }
                    }
                }
            }
        }
    }

    private fun Float.offset(k: Float) = with(this + k) {
        Offset(
            x = (140 * sin(this) + 80 * sin(this * PI)).toFloat(),
            y = (5 * 140 * cos(this) + 80 * cos(this * PI)).toFloat()
        )
    }
}


