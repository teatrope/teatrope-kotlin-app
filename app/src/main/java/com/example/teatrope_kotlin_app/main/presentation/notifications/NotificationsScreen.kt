package com.example.teatrope_kotlin_app.main.presentation.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.teatrope_kotlin_app.ui.theme.AccentRed
import com.example.teatrope_kotlin_app.ui.theme.SurfaceDeep

data class NotificationItem(val id: String, val title: String, val body: String)

@Composable
fun NotificationsScreen(onBack: () -> Unit) {
    val items = remember {
        mutableStateListOf(
            NotificationItem("1", "New play just added!", "“Voices of the City” is now on Teatrope. Check out showtimes and book your tickets early!"),
            NotificationItem("2", "Something’s happening near you!", "A new performance is opening this weekend at Teatro La Plaza, just a few blocks away."),
            NotificationItem("3", "A change in your favorite show", "“The Match” has a new schedule. Tap to see the updated dates."),
            NotificationItem("4", "Recommended for You", "A new play matches your taste.")
        )
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(listOf(SurfaceDeep, Color(0xFF0D1017))))
            .padding(16.dp)
    ) {
        // Header
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("teatrope", color = AccentRed, fontWeight = FontWeight.Bold)
            TextButton(onClick = { items.clear() }) { Text("Clear all") }
        }

        Spacer(Modifier.height(16.dp))

        items.forEach { n ->
            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0x14FFFFFF))
            ) {
                Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Box(Modifier.size(10.dp).clip(CircleShape).background(AccentRed))
                        Text(n.title, fontWeight = FontWeight.SemiBold)
                    }
                    Text(n.body)
                }
            }
        }

        if (items.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No notifications")
            }
        }
    }
}
