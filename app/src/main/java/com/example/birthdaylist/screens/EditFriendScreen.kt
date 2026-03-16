package com.example.birthdaylist.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.birthdaylist.components.FriendContent
import com.example.birthdaylist.data.Friend
import java.util.Calendar
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditFriendScreen(
    friend: Friend?,
    onUpdate: (Friend) -> Unit,
    onNavigateBack: () -> Unit,
    onLogout: () -> Unit
) {
    if (friend == null) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text("Friend not found")
        }
        return
    }

    val initialMillis = remember(friend) {
        Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
            set(Calendar.YEAR, friend.birthYear ?: 2000)
            set(Calendar.MONTH, (friend.birthMonth ?: 1) - 1)
            set(Calendar.DAY_OF_MONTH, friend.birthDayOfMonth ?: 1)
            set(Calendar.HOUR_OF_DAY, 0); set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0); set(Calendar.MILLISECOND, 0)
        }.timeInMillis
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Friend") },
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
            title = "Update Friend",
            subtitle = "Update friend information and click save",
            initialName = friend.name,
            initialBirthday = initialMillis,
            onCancel = onNavigateBack,
            onSave = { newName, millis ->
                millis?.let {
                    val c = Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply { timeInMillis = it }
                    val updated = friend.copy(
                        name = newName.trim(),
                        birthYear = c.get(Calendar.YEAR),
                        birthMonth = c.get(Calendar.MONTH) + 1,
                        birthDayOfMonth = c.get(Calendar.DAY_OF_MONTH),
                        userId = "Test@test.dk" // TODO: remove when login is added
                    )
                    onUpdate(updated)
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EditFriendScreenPreview() {
    EditFriendScreen(
        friend = Friend(id = 1, name = "Anna", birthYear = 1998, birthMonth = 3, birthDayOfMonth = 12, age = 25),
        onUpdate = {},
        onNavigateBack = {},
        onLogout = {}
    )
}
