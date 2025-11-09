package com.example.teatrope_kotlin_app.core.ui.detail

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

import com.example.teatrope_kotlin_app.content.presentation.theaters.ObraUi
import com.example.teatrope_kotlin_app.presentation.theater.ObrasViewModel



@Composable
fun ObraDetailRoute(
    obraId: String,
    obrasVm: ObrasViewModel = hiltViewModel()
) {
    val state = obrasVm.state.collectAsStateWithLifecycle().value
    val obra: ObraUi? = state.items.firstOrNull { it.id == obraId }  // <— ObraUi

    when {
        obra != null -> ObraDetailScreen(obra = obra)
        state.loading -> ObraDetailLoading()
        state.error != null -> ObraDetailError(msg = state.error!!) { obrasVm.refresh() }
    }
}
