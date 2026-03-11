package com.example.birthdaylist.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

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
