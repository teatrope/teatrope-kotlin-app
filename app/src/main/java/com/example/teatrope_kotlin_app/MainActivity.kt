package com.example.teatrope_kotlin_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.teatrope_kotlin_app.feature.home.HomeViewModel
import com.example.teatrope_kotlin_app.feature.home.TeatropeHomeRoute
import com.example.teatrope_kotlin_app.ui.theme.TeatropekotlinappTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TeatropekotlinappTheme {
                val vm: HomeViewModel = viewModel()
                TeatropeHomeRoute(viewModel = vm)
            }
        }
    }
}
