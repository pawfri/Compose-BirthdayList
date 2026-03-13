package com.example.birthdaylist.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.birthdaylist.components.AddFriendButton
import com.example.birthdaylist.components.LogoutButton
import com.example.birthdaylist.viewmodel.FriendsViewModel
import com.example.birthdaylist.data.Friend
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    friendsViewModel: FriendsViewModel = koinViewModel()) {

    val uiState = friendsViewModel.friendsUIState.collectAsState()

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
            friends = uiState.value.friends,
            navController = navController,
            onDelete = { id -> friendsViewModel.deleteFriend(id)}
        )
    }
}

@Composable
fun HomeContent(
    innerPadding: PaddingValues,
    friends: List<Friend>,
    navController: NavController,
    onDelete: (Int) -> Unit

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
                        Row {
                            Text(friend.name)
                            Spacer(modifier= Modifier.width(8.dp))
                            Text(friend.age?.toString() ?: "-")
                        }
                        Text(friend.birthday ?: "")
                    }

                    IconButton(onClick = { onDelete(friend.id) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete Friend")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        navController = rememberNavController()
    )
}