package com.example.teatrope_kotlin_app.presentation.theater

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teatrope_kotlin_app.content.data.mapper.toUi
import com.example.teatrope_kotlin_app.content.presentation.theaters.ObraUi
import com.example.teatrope_kotlin_app.content.data.repository.PlayRepository
import com.example.teatrope_kotlin_app.content.presentation.funciones.FuncionUi
import com.example.teatrope_kotlin_app.content.presentation.funciones.toUi
import com.example.teatrope_kotlin_app.content.presentation.personas.PersonaUi
import com.example.teatrope_kotlin_app.content.presentation.personas.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ObrasViewModel @Inject constructor(
    private val repo: PlayRepository
) : ViewModel() {

    data class UiState(

        private val allItems: List<ObraUi> = emptyList(),
        val loading: Boolean = false,
        val error: String? = null,


        val selectedGenre: String = "All",
        val selectedDistrict: String = "All",


        val obraDetail: ObraUi? = null,
        val loadingDetail: Boolean = false,
        val funciones: List<FuncionUi> = emptyList(),
        val loadingFunciones: Boolean = false,
        val reparto: List<PersonaUi> = emptyList(),
        val loadingReparto: Boolean = false
    ) {
        val genres: List<String> = listOf("All") + allItems.map { it.genero }.distinct()
        val districts: List<String> = listOf("All") + allItems.map { it.distrito }.distinct()


        val items: List<ObraUi> = allItems.filter { obra ->
            (selectedGenre == "All" || obra.genero == selectedGenre) &&
            (selectedDistrict == "All" || obra.distrito == selectedDistrict)
        }
    }

    private val _state = MutableStateFlow(UiState(loading = true))
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        load()
    }

    fun setGenre(genre: String) {
        _state.update { it.copy(selectedGenre = genre) }
    }

    fun setDistrict(district: String) {
        _state.update { it.copy(selectedDistrict = district) }
    }

    fun load() = viewModelScope.launch {
        _state.update { it.copy(loading = true, error = null) }

        runCatching { repo.getPlays() }
            .onSuccess { plays ->
                val ui = plays.map { it.toUi() }
                _state.update { it.copy(loading = false, allItems = ui) }
            }
            .onFailure { t ->
                _state.update {
                    it.copy(loading = false, error = t.message ?: "Error")
                }
            }
    }

    fun loadObraById(id: String) = viewModelScope.launch {
        _state.update { it.copy(loadingDetail = true, loadingFunciones = true, loadingReparto = true, error = null) }


        runCatching {
            repo.getPlay(id)
        }.onSuccess { play ->
            _state.update {
                it.copy(loadingDetail = false, obraDetail = play.toUi())
            }
        }.onFailure { t ->
            _state.update {
                it.copy(loadingDetail = false, error = t.message ?: "Error loading play details")
            }
        }


        runCatching {
            repo.getFunciones()
        }.onSuccess { funcionesDto ->
            val funcionesUi = funcionesDto
                .filter { it.obra.id == id }
                .map { it.toUi() }
            _state.update {
                it.copy(loadingFunciones = false, funciones = funcionesUi)
            }
        }.onFailure { t ->
            _state.update {
                it.copy(loadingFunciones = false, error = t.message ?: "Error loading funciones")
            }
        }


        runCatching {
            repo.getPersonas()
        }.onSuccess { personasDto ->
            val repartoUi = personasDto
                .filter { it.obra.id == id }
                .map { it.toUi() }
            _state.update {
                it.copy(loadingReparto = false, reparto = repartoUi)
            }
        }.onFailure { t ->
            _state.update {
                it.copy(loadingReparto = false, error = t.message ?: "Error loading reparto")
            }
        }
    }

    fun refresh() = load()
}
