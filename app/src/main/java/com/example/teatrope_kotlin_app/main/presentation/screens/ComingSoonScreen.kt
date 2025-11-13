package com.example.teatrope_kotlin_app.main.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.teatrope_kotlin_app.content.presentation.theaters.ObraUi
import com.example.teatrope_kotlin_app.core.ui.components.ObraCard
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.teatrope_kotlin_app.presentation.theater.ObrasViewModel
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Button
import androidx.compose.runtime.getValue

@Composable
fun ComingSoonScreen(
    onOpenDetail: (String) -> Unit,
    obrasVm: ObrasViewModel = hiltViewModel()
) {
    val obrasState by obrasVm.state.collectAsStateWithLifecycle()

    if (obrasState.loading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if (obrasState.error != null) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {
            Text("Error: ${obrasState.error}")
            Spacer(Modifier.height(8.dp))
            Button(onClick = { obrasVm.refresh() }) {
                Text("Retry")
            }
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(obrasState.items, key = { it.id }) { obra ->
                ObraCard(obra = obra, onClick = { onOpenDetail(obra.id) })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ComingSoonScreenPreview() {
    ComingSoonScreen(onOpenDetail = {})
}
