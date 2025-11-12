package com.example.teatrope_kotlin_app.auth.presentation.forgot

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.teatrope_kotlin_app.auth.presentation.components.AuthBackground
import com.example.teatrope_kotlin_app.auth.presentation.components.FieldLabel
import com.example.teatrope_kotlin_app.auth.presentation.components.FilledField
import com.example.teatrope_kotlin_app.auth.presentation.components.PrimaryButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(
    onBack: () -> Unit,
    viewModel: ForgotPasswordViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    val state by viewModel.state.collectAsStateWithLifecycle()

    AuthBackground {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Reset Password") },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        titleContentColor = MaterialTheme.colorScheme.onSurface,
                        navigationIconContentColor = MaterialTheme.colorScheme.onSurface
                    )
                )
            },
            containerColor = Color.Transparent
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                if (state is ForgotPasswordState.Success) {
                    Text(
                        "Password reset link sent!",
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Check your email to continue.",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                } else {
                    Text(
                        "Forgot Password?",
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Enter your email and we'll send you a link to reset your password.",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(Modifier.height(24.dp))

                    FieldLabel("E-mail")
                    Spacer(Modifier.height(6.dp))
                    FilledField(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = "user@gmail.com"
                    )

                    Spacer(Modifier.height(16.dp))
                    PrimaryButton(
                        text = "Send Reset Link",
                        enabled = email.isNotBlank() && state !is ForgotPasswordState.Loading,
                        onClick = { viewModel.requestPasswordReset(email) }
                    )

                    if (state is ForgotPasswordState.Loading) {
                        Spacer(Modifier.height(8.dp))
                        CircularProgressIndicator()
                    } 

                    (state as? ForgotPasswordState.Error)?.let { errorState ->
                        Spacer(Modifier.height(8.dp))
                        Text(errorState.message, color = MaterialTheme.colorScheme.error)
                    }       
                }
            }
        }
    }
}
