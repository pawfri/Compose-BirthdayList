package com.example.birthdaylist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
        enableEdgeToEdge()
        setContent {
            BirthdayListTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val friendsViewModel: FriendsViewModel = koinViewModel()
    val friendsUIState by friendsViewModel.friendsUIState.collectAsStateWithLifecycle()

    NavHost(navController = navController, startDestination = NavRoutes.Home.route) {
        composable(NavRoutes.Home.route) {
            HomeScreen(
                friends = friendsUIState.friends,
                modifier = modifier,
                onAdd = { navController.navigate(NavRoutes.NewFriend.route) },
                onEdit = { id: Int -> navController.navigate("${NavRoutes.EditFriend.route}/$id") },
                onDelete = { id: Int -> friendsViewModel.deleteFriend(id) },
                onLogout = {
                    navController.navigate(NavRoutes.Login.route) {
                        popUpTo(0)
                    }
                }
            )
        }
        composable(
            route = "${NavRoutes.EditFriend.route}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: -1
            val friend = friendsUIState.friends.find { it.id == id }
            
            EditFriendScreen(
                friend = friend,
                onUpdate = { updatedFriend ->
                    friendsViewModel.updateFriend(id, updatedFriend)
                    navController.popBackStack()
                },
                onNavigateBack = { navController.popBackStack() },
                onLogout = {
                    navController.navigate(NavRoutes.Login.route) {
                        popUpTo(0)
                    }
                }
            )
        }
        composable(NavRoutes.NewFriend.route) {
            NewFriendScreen(
                onNavigateBack = { navController.popBackStack() },
                onAdd = { name: String, birthdayMillis: Long? ->
                    friendsViewModel.addFriend(name, birthdayMillis)
                    navController.popBackStack()
                },
                onLogout = {
                    navController.navigate(NavRoutes.Login.route) {
                        popUpTo(0)
                    }
                }
            )
        }
    }
}
