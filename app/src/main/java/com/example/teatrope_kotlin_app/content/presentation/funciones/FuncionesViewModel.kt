package com.example.teatrope_kotlin_app.content.presentation.funciones

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teatrope_kotlin_app.core.network.api.ContentApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FuncionesViewModel : ViewModel() {

    private val _funciones = MutableStateFlow<List<FuncionUi>>(emptyList())
    val funciones: StateFlow<List<FuncionUi>> = _funciones.asStateFlow()

    // TODO: This should be injected with Hilt or another DI framework
    private val contentApi: ContentApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://teatrope-api-production-278a.up.railway.app/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ContentApi::class.java)
    }

    init {
        loadFunciones()
    }

    private fun loadFunciones() {
        viewModelScope.launch {
            try {
                val response = contentApi.funcionesList()
                if (response.isSuccessful) {
                    _funciones.value = response.body()?.map { it.toUi() } ?: emptyList()
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}
