package com.example.birthdaylist.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    navigateToLogin: () -> Unit,
    sortByName: (Boolean) -> Unit = {},
    sortByAge: (Boolean) -> Unit = {},
    sortByBirthday: (Boolean) -> Unit = {}
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
            onDelete = onDelete,
            sortByName = sortByName,
            sortByAge = sortByAge,
            sortByBirthday = sortByBirthday
        )
    }
}

@Composable
fun HomeContent(
    innerPadding: PaddingValues,
    friends: List<Friend>,
    onEdit: (Int) -> Unit,
    onDelete: (Int) -> Unit,
    sortByName: (Boolean) -> Unit = {},
    sortByAge: (Boolean) -> Unit = {},
    sortByBirthday: (Boolean) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    var sortNameAscending by remember { mutableStateOf(true) }
    var sortAgeAscending by remember { mutableStateOf(true) }
    var sortBirthdayAscending by remember { mutableStateOf(true) }
    var filtersExpanded by remember { mutableStateOf(false) }


    Column(modifier = modifier.padding(innerPadding)) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { filtersExpanded = !filtersExpanded }
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Filters",
                style = MaterialTheme.typography.titleMedium
            )
            Icon(
                imageVector = if (filtersExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = "Toggle filters"
            )
        }
        if (filtersExpanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Text("TODO") //TODO: Add filter name here
                Text("TODO") //TODO: Add filter for age here, slider
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = {
                    sortByName(sortNameAscending)
                    sortNameAscending = !sortNameAscending
                }) {
                Text(text = "Name")
                Icon(
                    imageVector = if (sortNameAscending) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                    contentDescription = if (sortNameAscending) "Sort Name Ascending" else "Sort Name Descending",
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = {
                    sortByAge(sortAgeAscending)
                    sortAgeAscending = !sortAgeAscending
                }) {
                Text(text = "Age")
                Icon(
                    imageVector = if (sortAgeAscending) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                    contentDescription = if (sortAgeAscending) "Sort Age Ascending" else "Sort Age Descending",
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = {
                    sortByBirthday(sortBirthdayAscending)
                    sortBirthdayAscending = !sortBirthdayAscending
                }) {
                Text(text = "Birthday")
                Icon(
                    imageVector = if (sortBirthdayAscending) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                    contentDescription = if (sortBirthdayAscending) "Sort Age Ascending" else "Sort Age Descending",
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }

        LazyColumn(
            modifier = Modifier
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

                            Row {
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
        navigateToLogin = {},
        sortByName = {},
        sortByAge = {},
        sortByBirthday = {}
    )
}
