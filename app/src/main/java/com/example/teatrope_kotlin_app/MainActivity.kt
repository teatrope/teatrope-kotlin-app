package com.example.teatrope_kotlin_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.teatrope_kotlin_app.navigation.RootNav
import com.example.teatrope_kotlin_app.ui.theme.TeatropeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TeatropeTheme {
                // startInMain = true si ya tienes sesión iniciada (token guardado)
                RootNav(startInMain = false)
            }
        }
    }
}
