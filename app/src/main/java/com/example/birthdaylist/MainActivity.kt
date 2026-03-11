package com.example.birthdaylist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.birthdaylist.screens.HomeScreen
import com.example.birthdaylist.screens.NewFriendScreen
import com.example.birthdaylist.ui.theme.BirthdayListTheme

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

    NavHost(
        navController = navController,
        startDestination = NavRoutes.Home.route
    ) {
//        composable(NavRoutes.Login.route) {
//            LoginScreen(navController)
//        }
        composable(NavRoutes.Home.route) {
            HomeScreen(navController)
        }
        composable(NavRoutes.NewFriend.route) {
            NewFriendScreen(navController)
        }
//        composable(NavRoutes.EditFriend.route) {
//            EditFriendScreen(navController)
//        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BirthdayListTheme {
        MainScreen()
    }
}