package com.example.teatrope_kotlin_app.core.ui.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.teatrope_kotlin_app.presentation.theater.ObrasViewModel

@Composable
fun ObraDetailRoute(
    obraId: String,
    onBack: () -> Unit,
    obrasVm: ObrasViewModel = hiltViewModel()
) {

    LaunchedEffect(obraId) {
        obrasVm.loadObraById(obraId)
    }

    val state = obrasVm.state.collectAsStateWithLifecycle().value

    when {
        state.loadingDetail || state.loadingFunciones || state.loadingReparto -> ObraDetailLoading()
        state.obraDetail != null -> ObraDetailScreen(
            obra = state.obraDetail,
            funciones = state.funciones,
            reparto = state.reparto,
            onBack = onBack
        )
        state.error != null -> ObraDetailError(msg = state.error) { obrasVm.loadObraById(obraId) }
    }
}
