package com.upsidedowndev.vibeplayer.core.presentation.designSystem.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = primary,
    surface = background,
    background = background,
    onBackground = white,
    onSurface = onSurface,
    onSurfaceVariant = onSurfaceVariant,
    surfaceContainer = surfaceContainerHighest,
    surfaceContainerHighest = surfaceContainerHighest,
    surfaceDim = surfaceDisable,
    tertiary = tertiary,

)

@Composable
fun VibePlayerTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}