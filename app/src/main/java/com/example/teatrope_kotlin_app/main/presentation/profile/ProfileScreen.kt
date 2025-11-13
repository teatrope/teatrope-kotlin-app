package com.example.teatrope_kotlin_app.main.presentation.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.teatrope_kotlin_app.core.network.api.UserDto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    vm: ProfileViewModel = hiltViewModel()
) {
    val state by vm.state.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text("My Profile", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(24.dp))
        }

        item {
            when {
                state.isLoading && state.user == null -> CircularProgressIndicator()
                state.error != null -> {
                    Text("Error: ${state.error}", color = MaterialTheme.colorScheme.error)
                    Spacer(Modifier.height(8.dp))
                    Button(onClick = { vm.loadUserProfile() }) {
                        Text("Retry")
                    }
                }
                state.user != null -> {
                    if (state.isEditing) {
                        ProfileEdit(state = state, vm = vm)
                    } else {
                        ProfileView(user = state.user!!, onEditClick = { vm.onEditToggle() })
                    }
                }
            }
        }
    }
}

@Composable
private fun ProfileView(user: UserDto, onEditClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
        InfoRow("Email", user.email.orEmpty())
        InfoRow("Role", user.role.orEmpty())
        InfoRow("Calle", user.calle.orEmpty())
        InfoRow("Distrito", user.distrito.orEmpty())
        InfoRow("Generos Preferidos", user.generosPreferidos?.joinToString(", ").orEmpty())
        Spacer(Modifier.height(24.dp))
        Button(onClick = onEditClick, modifier = Modifier.fillMaxWidth()) {
            Text("Edit Profile")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileEdit(state: ProfileState, vm: ProfileViewModel) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
        OutlinedTextField(
            value = state.editedCalle,
            onValueChange = { vm.onCalleChanged(it) },
            label = { Text("Calle") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = state.editedDistrito,
            onValueChange = { vm.onDistritoChanged(it) },
            label = { Text("Distrito") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))
        Text("Generos Preferidos", fontWeight = FontWeight.Bold)
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            vm.availableGenres.forEach { genre ->
                FilterChip(
                    selected = state.editedGeneros.contains(genre),
                    onClick = { vm.onGenreToggle(genre) },
                    label = { Text(genre) }
                )
            }
        }
        Spacer(Modifier.height(24.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = { vm.onSave() }, modifier = Modifier.weight(1f)) {
                if (state.isLoading) CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
                else Text("Save")
            }
            OutlinedButton(onClick = { vm.onEditToggle() }, modifier = Modifier.weight(1f)) {
                Text("Cancel")
            }
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Column(Modifier.padding(vertical = 8.dp)) {
        Text(label, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
        Text(value, fontSize = 16.sp)
        Divider(modifier = Modifier.padding(top = 8.dp))
    }
}