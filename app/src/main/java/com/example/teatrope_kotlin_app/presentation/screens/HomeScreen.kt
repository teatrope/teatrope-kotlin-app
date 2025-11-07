package com.example.teatrope_kotlin_app.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.teatrope_kotlin_app.presentation.theater.ObrasSection

@Composable
fun HomeScreen(
    onOpenDetail: (showId: String) -> Unit,
    onOpenNotifications: () -> Unit,
    onOpenTheater: (theaterId: String) -> Unit
) {
    Column(Modifier.fillMaxSize()) {
        // Render de las obras; cuando se toque una obra, navega a detail
        ObrasSection(
            modifier = Modifier.weight(1f),
            onObraClick = { obra -> onOpenDetail(obra.id) }
        )
    }
}
