package com.example.teatrope_kotlin_app.main.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.teatrope_kotlin_app.ui.theme.*

@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    onLogout: () -> Unit
) {
    var email by remember { mutableStateOf("user@gmail.com") }
    var pass  by remember { mutableStateOf("password") }
    var notif by remember { mutableStateOf(true) }
    var loc   by remember { mutableStateOf(false) }

    Column(
        Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(listOf(SurfaceDeep, Color(0xFF0D1017))))
            .padding(16.dp)
    ) {
        // Header
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            TextButton(onClick = onBack) { Icon(Icons.Rounded.ArrowBack, contentDescription = null) }
            Text("Settings", color = TextPrimary, modifier = Modifier.padding(top = 10.dp))
            Spacer(Modifier.width(48.dp))
        }

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = email, onValueChange = { email = it }, label = { Text("E-mail") }, singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = FieldFill, unfocusedContainerColor = FieldFill,
                focusedBorderColor = FieldStroke, unfocusedBorderColor = FieldStroke, cursorColor = AccentRed
            ),
            modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)
        )
        Spacer(Modifier.height(10.dp))
        OutlinedTextField(
            value = pass, onValueChange = { pass = it }, label = { Text("Password") }, singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = FieldFill, unfocusedContainerColor = FieldFill,
                focusedBorderColor = FieldStroke, unfocusedBorderColor = FieldStroke, cursorColor = AccentRed
            ),
            modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)
        )
        Spacer(Modifier.height(10.dp))
        Button(onClick = { /* TODO: update via API */ }, shape = RoundedCornerShape(12.dp)) { Text("Update") }

        Spacer(Modifier.height(16.dp))
        Text("Permissions", color = TextPrimary)
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Notifications"); Switch(checked = notif, onCheckedChange = { notif = it })
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Location"); Switch(checked = loc, onCheckedChange = { loc = it })
        }

        Spacer(Modifier.height(16.dp))
        Text("About us", color = TextSecondary)
        Text("Terms & Conditions", color = TextSecondary)

        Spacer(Modifier.weight(1f))
        Button(
            onClick = onLogout,
            colors = ButtonDefaults.buttonColors(containerColor = AccentRed, contentColor = TextPrimary),
            modifier = Modifier.fillMaxWidth().height(48.dp),
            shape = RoundedCornerShape(14.dp)
        ) { Text("Logout") }
    }
}
