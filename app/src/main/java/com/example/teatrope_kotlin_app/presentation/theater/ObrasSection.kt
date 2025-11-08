package com.example.teatrope_kotlin_app.presentation.theater

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.teatrope_kotlin_app.core.network.api.ObraDto

@Composable
fun ObrasSection(
    items: List<ObraDto> = emptyList(),
    isLoading: Boolean = false,
    error: String? = null,
    onRetry: () -> Unit = {},
    onOpen: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    when {
        isLoading -> Box(
            modifier = modifier
                .fillMaxWidth()
                .height(120.dp),
            contentAlignment = Alignment.Center
        ) { CircularProgressIndicator() }

        error != null -> Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.weight(1f)
            )
            TextButton(onClick = onRetry) { Text("Retry") }
        }

        else -> {
            //
            Column(modifier = modifier.fillMaxWidth()) {
                items.forEach { obra ->
                    ListItem(
                        headlineContent = { Text(obra.titulo) },
                        supportingContent = { Text(obra.genero ?: "") },
                        modifier = Modifier.clickable { onOpen(obra.id) }
                    )
                    Divider()
                }
            }
        }
    }
}
