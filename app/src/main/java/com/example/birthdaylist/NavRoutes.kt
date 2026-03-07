package com.example.birthdaylist

sealed class NavRoutes(val route: String) {
    data object Home : NavRoutes("home")
    data object Login : NavRoutes("login")
    data object NewFriend : NavRoutes("new_friend")
    data object EditFriend : NavRoutes("edit_friend")
}