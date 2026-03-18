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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.birthdaylist.components.SimpleTopAppBar
import com.example.birthdaylist.data.Friend

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    friends: List<Friend>,
    modifier: Modifier = Modifier,
    onAdd: () -> Unit,
    onEdit: (Int) -> Unit,
    onDelete: (Int) -> Unit,
    onLogout: () -> Unit,
    navigateToLogin: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            SimpleTopAppBar(
                title = "Home",
                onLogout = {
                    onLogout()
                    navigateToLogin()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAdd) {
                Icon(Icons.Default.Add, contentDescription = "Add Friend")
            }
        },
    ) { innerPadding ->
        HomeContent(
            innerPadding = innerPadding,
            friends = friends,
            onEdit = onEdit,
            onDelete = onDelete
        )
    }
}

@Composable
fun HomeContent(
    innerPadding: PaddingValues,
    friends: List<Friend>,
    onEdit: (Int) -> Unit,
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
                onClick = { onEdit(friend.id) }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = friend.name,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Row{
                            Text(friend.age?.toString() ?: "-")
                            Text(
                                if (friend.age == 1) " year" else " years"
                            )
                        }
                        Text("${friend.birthDayOfMonth}/${friend.birthMonth}/${friend.birthYear}")
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
        friends = listOf(
            Friend(id = 1, name = "Anna", birthYear = 1998, birthMonth = 3, birthDayOfMonth = 12, age = 25)
        ),
        onAdd = {},
        onEdit = {},
        onDelete = {},
        onLogout = {},
        navigateToLogin = {}
    )
}
