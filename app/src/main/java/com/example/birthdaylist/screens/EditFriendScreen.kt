package com.example.birthdaylist.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.birthdaylist.components.FriendContent
import com.example.birthdaylist.components.LogoutButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditFriendScreen(
    navController: NavHostController,
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Friend") },
                actions = {
                    LogoutButton(navController)
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
            title = "Update Friend",
            subtitle = "Update friend information and click save",
            initialName = "", //TODO: Add Friend ExistingName
            initialBirthday = null, //TODO: Add Friend ExistingBirthday
            onCancel = { navController.popBackStack() },
            onSave = { name, birthday -> /* TODO logic */ }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EditFriendScreenPreview() {
    EditFriendScreen(
        navController = rememberNavController()
    )
}
