@file:Suppress("UnusedImport")

package com.example.teatrope_kotlin_app.core.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.ui.res.painterResource
import com.example.teatrope_kotlin_app.R
import com.example.teatrope_kotlin_app.content.presentation.theaters.ObraUi

@Composable
fun ObraCard(
    obra: ObraUi,
    onClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(obra.id) }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(obra.imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = obra.titulo,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            placeholder = painterResource(R.drawable.placeholder),
            error = painterResource(R.drawable.placeholder_error)
        )

        Column(Modifier.padding(12.dp)) {
            Text(text = obra.titulo)
            Text(text = obra.genero)
        }
    }
}
