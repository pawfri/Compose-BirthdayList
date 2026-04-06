package com.example.birthdaylist.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseUser

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    user: FirebaseUser? = null,
    message: String = "",
    signIn: (email: String, password: String) -> Unit = { _, _ -> },
    register: (email: String, password: String) -> Unit = { _, _ -> },
    navigateToNextScreen: () -> Unit = {}
) {
    Scaffold(
        topBar = {},
    ) { innerPadding ->
        LoginContent(
            innerPadding = innerPadding,
            user = user,
            message = message,
            signIn = signIn,
            register = register,
            navigateToNextScreen = navigateToNextScreen
        )
    }
}

@Composable
fun LoginContent(
    innerPadding: PaddingValues,
    user: FirebaseUser? = null,
    message: String = "",
    signIn: (email: String, password: String) -> Unit = { _, _ -> },
    register: (email: String, password: String) -> Unit = { _, _ -> },
    navigateToNextScreen: () -> Unit = {}
) {
    if (user != null) {
        LaunchedEffect(Unit) {
            navigateToNextScreen()
        }
    }
    val emailStart = "pat@pat.dk" //TODO: dummy mail. Remove later.
    val passwordStart = "pat12" //TODO: dummy password. Remove later.
    var email by remember { mutableStateOf(emailStart) }
    var password by remember { mutableStateOf(passwordStart) }
    var emailIsError by remember { mutableStateOf(false) }
    var passwordIsError by remember { mutableStateOf(false) }
    var showPassword by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .padding(24.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Login",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth(),
            isError = emailIsError,
        )
        if (emailIsError) {
            Text("Invalid email", color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            isError = passwordIsError,
            trailingIcon = {
                IconButton(onClick = { showPassword = !showPassword }) {
                    if (showPassword) {
                        Icon(Icons.Filled.Visibility, contentDescription = "Hide password")
                    } else {
                        Icon(Icons.Filled.VisibilityOff, contentDescription = "Show password")
                    }
                }
            }
        )
        if (passwordIsError) {
            Text("Invalid password", color = MaterialTheme.colorScheme.error)
        }
        if (message.isNotEmpty()) {
            Text(message, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                signIn(email, password)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

        OutlinedButton(
            onClick = {
                register(email, password)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Create New User")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}
