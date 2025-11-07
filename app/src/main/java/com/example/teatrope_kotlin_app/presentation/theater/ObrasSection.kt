package com.example.teatrope_kotlin_app.presentation.theater

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.teatrope_kotlin_app.core.network.api.ObraDto
import coil.compose.rememberAsyncImagePainter

@Composable
fun ObrasSection(
    modifier: Modifier = Modifier,
    onObraClick: (ObraDto) -> Unit = {}
) {
    val vm: ObrasViewModel = hiltViewModel()
    val state by vm.state.collectAsState()

    LaunchedEffect(Unit) { vm.cargar() }

    when (state) {
        is ObrasUiState.Loading -> Text("Cargando…", Modifier.padding(16.dp))
        is ObrasUiState.Error -> Text(
            (state as ObrasUiState.Error).msg,
            Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.error
        )
        is ObrasUiState.Data -> {
            val obras = (state as ObrasUiState.Data).obras
            if (obras.isEmpty()) {
                Text("Sin obras por ahora.", Modifier.padding(16.dp))
            } else {
                LazyColumn(modifier) {
                    items(obras, key = { it.id }) { o ->
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            if (!o.imagen.isNullOrBlank()) {
                                AsyncImage(
                                    model = o.imagen,
                                    contentDescription = o.titulo,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(160.dp)
                                )
                                Spacer(Modifier.height(8.dp))
                            }
                            Text(o.titulo, style = MaterialTheme.typography.titleMedium)
                            o.descripcion?.let {
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    it,
                                    style = MaterialTheme.typography.bodyMedium,
                                    maxLines = 3,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            Spacer(Modifier.height(12.dp))
                            HorizontalDivider()
                        }
                    }
                }
            }
        }
    }
}
