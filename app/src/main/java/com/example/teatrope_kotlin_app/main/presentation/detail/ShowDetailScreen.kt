package com.example.teatrope_kotlin_app.main.presentation.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ShowDetailScreen(showId: String, onBack: () -> Unit) {
    Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Detalle: $showId")
        Text("Aquí va sinopsis, elenco, horarios, rating… (mock)")
        Button(onClick = onBack) { Text("Back") }
    }
}
