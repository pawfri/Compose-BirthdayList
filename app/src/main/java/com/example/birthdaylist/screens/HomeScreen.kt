package com.example.birthdaylist.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import com.example.birthdaylist.Friend
import com.example.birthdaylist.FriendsViewModel
import com.example.birthdaylist.NavRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    friendsViewModel: FriendsViewModel = viewModel()) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Home") },
                actions = {LogoutButton(navController)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
            )
        },
        floatingActionButton = {
            AddFriendButton(navController)
        },
    ) { innerPadding ->
        HomeContent(
            innerPadding = innerPadding,
            friends = friendsViewModel.friends,
            navController = navController,
            viewModel = friendsViewModel
        )
    }
}

@Composable
fun LogoutButton(navController: NavController) {
    Button(
        onClick = {
            //TODO: logout logic
            navController.navigate("login") {
                popUpTo(0)
            }
        }
    ) {
        Text("Logout")
    }
}

@Composable
fun AddFriendButton(navController: NavController) {
    FloatingActionButton(
        onClick = {navController.navigate("NewFriend")},
    ) {
        Icon(Icons.Default.Add, contentDescription = "Add Friend")
    }
}

@Composable
fun HomeContent(
    innerPadding: PaddingValues,
    friends: List<Friend>,
    navController: NavController,
    viewModel: FriendsViewModel
) {
    LazyColumn(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
    ) {
        items(friends) { friend ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                onClick = { navController.navigate("EditFriend") }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(friend.name)
                        Text(friend.birthday)
                    }

                    IconButton(onClick = { viewModel.deleteFriend(friend) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete Friend")
                    }
                }
            }
        }
    }
}