package com.example.birthdaylist.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.example.birthdaylist.viewmodel.FriendsViewModel
import org.koin.androidx.compose.koinViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditFriendScreen(
    navController: NavHostController,
    friendId: Int,
    viewModel: FriendsViewModel = koinViewModel()
) {
    val ui by viewModel.friendsUIState.collectAsState()
    val friend = ui.friends.firstOrNull { it.id == friendId } ?: return

    val initialMillis = remember(friend) {
        Calendar.getInstance().apply {
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
                actions = { LogoutButton(navController) },
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
            onCancel = { navController.popBackStack() },
            onSave = { newName, millis ->
                millis?.let {
                    val c = Calendar.getInstance().apply { timeInMillis = it }
                    val updated = friend.copy(
                        name = newName.trim(),
                        birthYear = c.get(Calendar.YEAR),
                        birthMonth = c.get(Calendar.MONTH) + 1,
                        birthDayOfMonth = c.get(Calendar.DAY_OF_MONTH),
                        userId = "Test@test.dk" // TODO: remove when login is added
                    )
                    viewModel.updateFriend(friendId, updated)
                }
                navController.popBackStack()
            }
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun EditFriendScreenPreview() {
//    EditFriendScreen(
//        navController = rememberNavController()
//    )
//}
