package com.example.teatrope_kotlin_app.auth.presentation.signin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.teatrope_kotlin_app.auth.presentation.components.*

@Composable
fun SignInScreen(
    onForgotPassword: () -> Unit,
    onSignIn: (email: String, password: String, remember: Boolean) -> Unit,
    onGoToSignUp: () -> Unit,
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var remember by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }

    val vm: SignInViewModel = hiltViewModel()
    val state by vm.state.collectAsState()

    val isLoading = state is SignInState.Loading

    AuthBackground {
        Box(modifier = Modifier.fillMaxSize()) { 
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BrandTitle()
                Spacer(Modifier.height(32.dp))
                PanelCard(Modifier.fillMaxWidth()) {
                    Text(
                        "Sign in",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(Modifier.height(16.dp))
                    FieldLabel("E-mail")
                    Spacer(Modifier.height(6.dp))
                    FilledField(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = "user@gmail.com"
                    )

                    Spacer(Modifier.height(12.dp))
                    FieldLabel("Password")
                    Spacer(Modifier.height(6.dp))
                    FilledField(
                        value = password,
                        onValueChange = { password = it },
                        placeholder = "••••••••",
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        trailingIcon = {
                            val image = if (passwordVisible)
                                Icons.Filled.Visibility
                            else Icons.Filled.VisibilityOff

                            val description = if (passwordVisible) "Hide password" else "Show password"

                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(imageVector = image, description)
                            }
                        }
                    )

                    Spacer(Modifier.height(10.dp))
                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(checked = remember, onCheckedChange = { remember = it })
                            Spacer(Modifier.width(4.dp))
                            Text("Remember me")
                        }
                        UnderlineLink("Forgot password?") { onForgotPassword() }
                    }

                    Spacer(Modifier.height(14.dp))
                    PrimaryButton(
                        text = "Sign in",
                        enabled = email.isNotBlank() && password.isNotBlank() && !isLoading
                    ) {
                        onSignIn(email.trim(), password, remember)
                    }

                    when (val s = state) {
                        is SignInState.Loading -> {
                            Spacer(Modifier.height(8.dp))
                            Text("Signing in...", color = MaterialTheme.colorScheme.primary)
                        }
                        is SignInState.Error -> {
                            Spacer(Modifier.height(8.dp))
                            Text(s.message, color = MaterialTheme.colorScheme.error)
                        }
                        else -> Unit
                    }

                    Spacer(Modifier.height(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Don’t have an account? ")
                        UnderlineLink("Sign up") { onGoToSignUp() }
                    }
                }
            }
        }
    }
}
