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
import com.example.birthdaylist.components.LogoutButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewFriendScreen(
    navController: NavHostController,
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add New Friend") },
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
        NewFriendContent(
            innerPadding = innerPadding,
        )
    }
}

@Composable
fun NewFriendContent(
    innerPadding: PaddingValues,
) {
    var name by remember { mutableStateOf("") }
    var birthday by remember { mutableStateOf<Long?>(null) }
    var showDatePicker by remember { mutableStateOf(false) }

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

        FriendNameInput(
            value = name,
            onValueChange = { name = it }
        )

        val birthdayButtonText = birthday?.toString() ?: "Select Birthday"
        OutlinedButton(onClick = { showDatePicker = true }) {
            Text(birthdayButtonText)
        }

        if (showDatePicker) {
            DatePickerDialog(
                onDateSelected = {
                    birthday = it
                    showDatePicker = false
                },
                onDismiss = {
                    showDatePicker = false
                }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TextButton(onClick = { /* TODO: Cancel logic */ }) {
                Text("Cancel",
                    style = MaterialTheme.typography.titleLarge)
            }
            TextButton(onClick = { /* TODO: Create logic */ }) {
                Text("Save",
                    style = MaterialTheme.typography.titleLarge)
            }
        }
    }
}

@Composable
fun FriendNameInput(
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Name") },
        modifier = Modifier.fillMaxWidth()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    Dialog(onDismissRequest = onDismiss) {
        Surface(shape = MaterialTheme.shapes.medium) {
            Column(modifier = Modifier.padding(16.dp)) {
                DatePicker(state = datePickerState)

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel")
                    }
                    TextButton(onClick = {
                        onDateSelected(datePickerState.selectedDateMillis)
                    }) {
                        Text("OK")
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun NewFriendScreenPreview() {
    NewFriendScreen(
        navController = rememberNavController()
    )
}
