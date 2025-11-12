package com.example.teatrope_kotlin_app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teatrope_kotlin_app.data.preferences.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    prefs: UserPreferencesRepository
) : ViewModel() {


    val startInMain: StateFlow<Boolean> = prefs.rememberMe
        .map { shouldRemember -> shouldRemember }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false // Start with login screen by default
        )
}
