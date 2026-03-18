package com.example.birthdaylist.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.birthdaylist.components.FriendContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewFriendScreen(
    onNavigateBack: () -> Unit,
    onAdd: (String, Long?) -> Unit,
    onLogout: () -> Unit,
    navigateToLogin: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add New Friend") },
                actions = { 
                    Button(onClick = onLogout) {
                        Text("Logout")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
            )
        },
    ) { innerPadding ->
        FriendContent(
            innerPadding = innerPadding,
            title = "New Friend",
            subtitle = "Enter friend information and click save",
            initialName = "",
            initialBirthday = null,
            onCancel = onNavigateBack,
            onSave = onAdd
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NewFriendScreenPreview() {
    NewFriendScreen(
        onNavigateBack = {},
        onAdd = { _, _ -> },
        onLogout = {},
        navigateToLogin = {}
    )
}
