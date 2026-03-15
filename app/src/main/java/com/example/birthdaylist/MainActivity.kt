package com.example.birthdaylist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.birthdaylist.screens.EditFriendScreen
import com.example.birthdaylist.screens.HomeScreen
import com.example.birthdaylist.screens.NewFriendScreen
import com.example.birthdaylist.ui.theme.BirthdayListTheme
import com.example.birthdaylist.viewmodel.FriendsViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BirthdayListTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val friendsViewModel: FriendsViewModel = koinViewModel()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.Home.route
    ) {
        composable(NavRoutes.Home.route) {
            HomeScreen(navController, friendsViewModel)
        }
        composable(NavRoutes.NewFriend.route) {
            NewFriendScreen(navController, friendsViewModel)
        }
        composable(
            route = "${NavRoutes.EditFriend.route}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")!!
            EditFriendScreen(navController, friendId = id, viewModel = friendsViewModel)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BirthdayListTheme {
        MainScreen()
    }
}
