package com.example.teatrope.ui.billboard

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.example.teatrope_kotlin_app.content.presentation.theaters.ObraUi
import com.example.teatrope_kotlin_app.core.ui.components.ObraCard

@Composable
fun BillboardScreen(
    obras: List<ObraUi>,
    onObraClick: (String) -> Unit
) {
    LazyColumn {
        items(
            items = obras,
            key = { it.id }
        ) { obra ->
            ObraCard(obra = obra, onClick = { onObraClick(obra.id) })
        }
    }
}
