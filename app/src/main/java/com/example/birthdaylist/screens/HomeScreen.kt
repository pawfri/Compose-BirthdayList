package com.example.birthdaylist.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.birthdaylist.components.SimpleTopAppBar
import com.example.birthdaylist.data.Friend
import kotlin.math.roundToInt

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
    sortByBirthday: (Boolean) -> Unit = {},
    filterByName: (String) -> Unit = {},
    filterByAge: (Int, Int) -> Unit = { _, _ -> }
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
            sortByBirthday = sortByBirthday,
            onFilterByName = filterByName,
            onFilterByAge = filterByAge
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier,
    friends: List<Friend>,
    onEdit: (Int) -> Unit,
    onDelete: (Int) -> Unit,
    sortByName: (Boolean) -> Unit = {},
    sortByAge: (Boolean) -> Unit = {},
    sortByBirthday: (Boolean) -> Unit = {},
    onFilterByName: (String) -> Unit = {},
    onFilterByAge: (Int, Int) -> Unit = { _, _ -> }
) {
    var sortNameAscending by remember { mutableStateOf(true) }
    var sortAgeAscending by remember { mutableStateOf(true) }
    var sortBirthdayAscending by remember { mutableStateOf(true) }
    var filtersExpanded by remember { mutableStateOf(false) }
    var nameFragment by remember { mutableStateOf("") }
    var ageRange by remember { mutableStateOf(0f..120f) }
    val keyboardController = LocalSoftwareKeyboardController.current


    Column(modifier = modifier.padding(innerPadding)) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { filtersExpanded = !filtersExpanded }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.Right
        ) {
            Icon(Icons.Default.Tune, contentDescription = "Filters")
            Spacer(Modifier.width(4.dp))
            Text(
                text = "Filters",
                style = MaterialTheme.typography.titleLarge
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
                OutlinedTextField(
                    value = nameFragment,
                    onValueChange = { 
                        nameFragment = it
                        onFilterByName(it)
                    },
                    label = { Text("Filter by name") },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    trailingIcon = {
                        if (nameFragment.isNotEmpty()) {
                            IconButton(onClick = {
                                nameFragment = ""
                                onFilterByName("") // Clear filter immediately
                            }) {
                                Icon(Icons.Default.Close, contentDescription = "Clear")
                            }
                        }
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {
                        onFilterByName(nameFragment)
                        keyboardController?.hide()
                    })
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(text = "Age range: ${ageRange.start.roundToInt()} - ${ageRange.endInclusive.roundToInt()}")
                RangeSlider(
                    value = ageRange,
                    onValueChange = { range ->
                        ageRange = range
                        onFilterByAge(range.start.roundToInt(), range.endInclusive.roundToInt())
                    },
                    valueRange = 0f..120f,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

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
                    imageVector = if (sortNameAscending) Icons.Default.ArrowDownward else Icons.Default.ArrowUpward,
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
                    imageVector = if (sortAgeAscending) Icons.Default.ArrowDownward else Icons.Default.ArrowUpward,
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
                    imageVector = if (sortBirthdayAscending) Icons.Default.ArrowDownward else Icons.Default.ArrowUpward,
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
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
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
                        }

                        Text(
                            text = "${friend.birthDayOfMonth}/${friend.birthMonth}/${friend.birthYear}",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )

                        IconButton(onClick = { onDelete(friend.id) }) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "Delete Friend",
                                tint = MaterialTheme.colorScheme.error
                            )
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
        navigateToLogin = {}
    )
}
