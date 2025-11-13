package com.example.teatrope_kotlin_app.content.presentation.funciones

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter

@Composable
fun FuncionListScreen(viewModel: FuncionesViewModel = viewModel()) {
    val funciones by viewModel.funciones.collectAsState()

    LazyColumn(modifier = Modifier.padding(8.dp)) {
        items(funciones) { funcion ->
            FuncionCard(funcion)
        }
    }
}

@Composable
fun FuncionCard(funcion: FuncionUi) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(funcion.obraImageUrl),
                contentDescription = null,
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = funcion.obraTitulo, style = MaterialTheme.typography.headlineSmall)
                Text(text = funcion.teatroNombre, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = funcion.fecha, style = MaterialTheme.typography.bodyMedium)
                Text(text = funcion.duracion, style = MaterialTheme.typography.bodyMedium)
                Text(text = funcion.disponibilidad, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { 
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(funcion.buyUrl))
                        context.startActivity(intent)
                     },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Comprar entradas")
                }
            }
        }
    }
}
