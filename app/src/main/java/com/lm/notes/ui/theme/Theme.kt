package com.lm.notes.ui.theme

import android.app.Activity
import android.content.res.Configuration
import android.os.Build
import android.view.View
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.lm.notes.presentation.NotesViewModel
import com.lm.notes.presentation.ViewModelFactory

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun NotesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    viewModelFactory: ViewModelFactory,
    content: @Composable () -> Unit
) {
    LocalViewModelStoreOwner.current?.also { owner ->

        val notesViewModel = remember {
            ViewModelProvider(owner, viewModelFactory)[NotesViewModel::class.java]
        }
        val colorScheme = when {
            dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                val context = LocalContext.current
                if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            }

            darkTheme -> DarkColorScheme
            else -> LightColorScheme
        }
        val view = LocalView.current
        if (!view.isInEditMode) {
            val configuration = LocalConfiguration.current
            val window = (view.context as Activity).window
            notesViewModel.uiStates.getMainColor.also { color ->
                LaunchedEffect(color) {

                    WindowCompat.getInsetsController(window, view).apply {
                        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
                                window.decorView.systemUiVisibility =
                                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        isAppearanceLightStatusBars = darkTheme
                        window.statusBarColor = color.toArgb()
                        window.navigationBarColor =
                            if (darkTheme) Color.Black.toArgb() else color.toArgb()
                    }
                }
            }
        }

        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}

