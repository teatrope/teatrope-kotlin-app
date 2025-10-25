package com.example.teatrope_kotlin_app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColors = darkColorScheme(
    primary = AccentRed,
    onPrimary = Color.White,
    background = SurfaceDeep,
    onBackground = TextPrimary,
    surface = CardDeep,
    onSurface = TextPrimary
)

@Composable
fun TeatropeTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColors,
        typography = Typography,
        content = content
    )
}
