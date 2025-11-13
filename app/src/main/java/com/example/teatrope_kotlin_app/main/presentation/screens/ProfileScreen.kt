package com.example.teatrope_kotlin_app.main.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teatrope_kotlin_app.ui.theme.*

@Composable
fun ProfileScreen(
    onOpenSettings: () -> Unit,
    onLogout: () -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(listOf(SurfaceDeep, Color(0xFF0D1017))))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("Profile", fontSize = 22.sp, color = TextPrimary)
            IconButton(onClick = onOpenSettings) { Icon(Icons.Outlined.Settings, contentDescription = null) }
        }

        Box(Modifier.size(84.dp).clip(RoundedCornerShape(20.dp)).background(Color(0x22FFFFFF)))

        Text("Hi! User", color = TextPrimary, fontSize = 18.sp)
        Text("Welcome", color = TextSecondary)

        Column(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
            ProfileRow("My tickets")
            ProfileRow("My credit cards")
            ProfileRow("History")
        }

        Spacer(Modifier.height(8.dp))
        Text("About us", color = TextSecondary)
        Text("Terms & Conditions", color = TextSecondary)

        Spacer(Modifier.weight(1f))
        Button(onClick = onLogout, colors = ButtonDefaults.buttonColors(containerColor = AccentRed, contentColor = TextPrimary), modifier = Modifier.fillMaxWidth().height(48.dp), shape = RoundedCornerShape(14.dp)) {
            Text("Logout")
        }
    }
}

@Composable private fun ProfileRow(title: String) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = Color(0x18FFFFFF),
        modifier = Modifier.fillMaxWidth().height(48.dp)
    ) { Box(Modifier.fillMaxSize(), contentAlignment = Alignment.CenterStart) { Text(title, modifier = Modifier.padding(horizontal = 16.dp)) } }
}
