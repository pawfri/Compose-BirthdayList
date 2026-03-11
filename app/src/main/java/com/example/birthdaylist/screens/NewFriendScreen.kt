package com.example.birthdaylist.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.birthdaylist.Friend
import com.example.birthdaylist.FriendsViewModel
import com.example.birthdaylist.components.AddFriendButton
import com.example.birthdaylist.components.LogoutButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewFriendScreen(
    navController: NavHostController,
    friendsViewModel: FriendsViewModel = viewModel()) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add New Friend") },
                actions = {LogoutButton(navController)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
            )
        },
    ) { innerPadding ->
        NewFriendContent(
            innerPadding = innerPadding,
        )
    }
}

@Composable
fun NewFriendContent(
    innerPadding: PaddingValues
) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "New Friend",
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = "Enter Friend birthday information",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NewFriendScreenPreview() {
    NewFriendScreen(
        navController = rememberNavController()
    )
}
