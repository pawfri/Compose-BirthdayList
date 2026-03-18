package com.example.birthdaylist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.birthdaylist.screens.EditFriendScreen
import com.example.birthdaylist.screens.HomeScreen
import com.example.birthdaylist.screens.LoginScreen
import com.example.birthdaylist.screens.NewFriendScreen
import com.example.birthdaylist.ui.theme.BirthdayListTheme
import com.example.birthdaylist.viewmodel.AuthenticationViewModel
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
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    authenticationViewModel: AuthenticationViewModel = viewModel()
) {
    val friendsViewModel: FriendsViewModel = koinViewModel()
    val friendsUIState by friendsViewModel.friendsUIState.collectAsStateWithLifecycle()
    val currentUserEmail = authenticationViewModel.user?.email ?: ""

    NavHost(
        navController = navController,
        startDestination = NavRoutes.Login.route
    ) {
        composable(NavRoutes.Home.route) {
            HomeScreen(
                friends = friendsUIState.friends,
                modifier = modifier,
                onAdd = { navController.navigate(NavRoutes.NewFriend.route) },
                onEdit = { id: Int -> navController.navigate("${NavRoutes.EditFriend.route}/$id") },
                onDelete = { id: Int -> friendsViewModel.deleteFriend(id, currentUserEmail) },
                onLogout = { authenticationViewModel.signOut() },
                navigateToLogin = {
                    navController.popBackStack(NavRoutes.Login.route, inclusive = false)
                })
        }
        composable(NavRoutes.Login.route) {
            LoginScreen(
                navController = navController,
                user = authenticationViewModel.user,
                message = authenticationViewModel.message,
                signIn = { email, password -> authenticationViewModel.signIn(email, password) },
                register = { email, password -> authenticationViewModel.register(email, password) },
                navigateToNextScreen = {
                    friendsViewModel.getFriends(currentUserEmail)
                    navController.navigate(NavRoutes.Home.route) {
                        popUpTo(NavRoutes.Login.route) { inclusive = true }
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
                onLogout = { authenticationViewModel.signOut() },
                navigateToLogin = {
                    navController.popBackStack(NavRoutes.Login.route, inclusive = false)
                })
        }
        composable(NavRoutes.NewFriend.route) {
            NewFriendScreen(
                onNavigateBack = { navController.popBackStack() },
                onAdd = { name: String, birthdayMillis: Long? ->
                    friendsViewModel.addFriend(currentUserEmail, name, birthdayMillis)
                    navController.popBackStack()
                },
                onLogout = { authenticationViewModel.signOut() },
                navigateToLogin = {
                    navController.popBackStack(NavRoutes.Login.route, inclusive = false)
                })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BirthdayListTheme {
        MainScreen()
    }
}
