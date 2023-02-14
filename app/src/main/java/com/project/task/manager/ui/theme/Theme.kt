package com.project.task.manager.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = taskDarkPrimary,
    onPrimary = taskDarkOnPrimary,
    primaryContainer = taskDarkPrimaryContainer,
    onPrimaryContainer = taskDarkOnPrimaryContainer,
    inversePrimary = taskDarkPrimaryInverse,
    secondary = taskDarkSecondary,
    onSecondary = taskDarkOnSecondary,
    secondaryContainer = taskDarkSecondaryContainer,
    onSecondaryContainer = taskDarkOnSecondaryContainer,
    tertiary = taskDarkTertiary,
    onTertiary = taskDarkOnTertiary,
    tertiaryContainer = taskDarkTertiaryContainer,
    onTertiaryContainer = taskDarkOnTertiaryContainer,
    error = taskDarkError,
    onError = taskDarkOnError,
    errorContainer = taskDarkErrorContainer,
    onErrorContainer = taskDarkOnErrorContainer,
    background = taskDarkBackground,
    onBackground = taskDarkOnBackground,
    surface = taskDarkSurface,
    onSurface = taskDarkOnSurface,
    inverseSurface = taskDarkInverseSurface,
    inverseOnSurface = taskDarkInverseOnSurface,
    surfaceVariant = taskDarkSurfaceVariant,
    onSurfaceVariant = taskDarkOnSurfaceVariant,
    outline = taskDarkOutline
)

private val LightColorScheme = lightColorScheme(
    primary = taskLightPrimary,
    onPrimary = taskLightOnPrimary,
    primaryContainer = taskLightPrimaryContainer,
    onPrimaryContainer = taskLightOnPrimaryContainer,
    inversePrimary = taskLightPrimaryInverse,
    secondary = taskLightSecondary,
    onSecondary = taskLightOnSecondary,
    secondaryContainer = taskLightSecondaryContainer,
    onSecondaryContainer = taskLightOnSecondaryContainer,
    tertiary = taskLightTertiary,
    onTertiary = taskLightOnTertiary,
    tertiaryContainer = taskLightTertiaryContainer,
    onTertiaryContainer = taskLightOnTertiaryContainer,
    error = taskLightError,
    onError = taskLightOnError,
    errorContainer = taskLightErrorContainer,
    onErrorContainer = taskLightOnErrorContainer,
    background = taskLightBackground,
    onBackground = taskLightOnBackground,
    surface = taskLightSurface,
    onSurface = taskLightOnSurface,
    inverseSurface = taskLightInverseSurface,
    inverseOnSurface = taskLightInverseOnSurface,
    surfaceVariant = taskLightSurfaceVariant,
    onSurfaceVariant = taskLightOnSurfaceVariant,
    outline = taskLightOutline
)

@Composable
fun TaskManagerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
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
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window,view).isAppearanceLightStatusBars=darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}