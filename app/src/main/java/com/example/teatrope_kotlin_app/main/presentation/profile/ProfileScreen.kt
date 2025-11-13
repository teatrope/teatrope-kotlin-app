package com.example.teatrope_kotlin_app.main.presentation.profile // <-- Paquete corregido

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.teatrope_kotlin_app.ui.theme.*

@Composable
fun ProfileScreen(
    onOpenSettings: () -> Unit,
    onLogout: () -> Unit,
    onOpenMyTickets: () -> Unit,
    onOpenMyCreditCards: () -> Unit,
    onOpenHistory: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel() // <-- ViewModel del mismo paquete
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(listOf(SurfaceDeep, Color(0xFF0D1017))))
            .padding(16.dp),
    ) {
        // -- Header --
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Profile", fontSize = 28.sp, color = TextPrimary, fontWeight = FontWeight.Bold)
            IconButton(onClick = onOpenSettings) { 
                Icon(Icons.Outlined.Settings, contentDescription = "Settings", tint = TextPrimary) 
            }
        }

        Spacer(Modifier.height(32.dp))

        // -- User Info --
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = uiState.profileImageUrl,
                contentDescription = "Foto de perfil de ${uiState.userName}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(84.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color(0x22FFFFFF))
            )
            Spacer(Modifier.width(16.dp))
            Column {
                Text("Hi! ${uiState.userName}", color = TextPrimary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text("Welcome", color = TextSecondary, fontSize = 16.sp)
            }
        }

        Spacer(Modifier.height(32.dp))

        // -- Action Rows --
        Column(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
            ProfileRow("My tickets", onClick = onOpenMyTickets)
            ProfileRow("My credit cards", onClick = onOpenMyCreditCards)
            ProfileRow("History", onClick = onOpenHistory)
        }

        Spacer(Modifier.height(32.dp))

        // -- About Section --
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text("About us", color = TextSecondary)
            Text("Terms & Conditions", color = TextSecondary)
        }

        // -- Logout Button --
        Spacer(Modifier.weight(1f))
        Button(
            onClick = onLogout, 
            colors = ButtonDefaults.buttonColors(containerColor = AccentRed, contentColor = TextPrimary), 
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp), 
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Logout", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
private fun ProfileRow(title: String, onClick: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color(0x18FFFFFF),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        onClick = onClick
    ) { 
        Box(
            Modifier.fillMaxSize().padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) { 
            Text(title, fontSize = 16.sp)
        }
    }
}
