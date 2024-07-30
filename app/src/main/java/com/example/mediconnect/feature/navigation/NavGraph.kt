package com.example.mediconnect.feature.navigation

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.Composable

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mediconnect.feature.screens.AdminScreen
import com.example.mediconnect.feature.screens.ChooseSubjectScreen
import com.example.mediconnect.feature.screens.ChooseTopicScreen
import com.example.mediconnect.feature.screens.HistoryScreen
import com.example.mediconnect.feature.screens.HomeScreen
import com.example.mediconnect.feature.screens.RateScreen
import com.example.mediconnect.feature.screens.SignInScreen
import com.example.mediconnect.feature.screens.SignUpScreen
import com.example.mediconnect.feature.screens.TestScreen

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.ChooseSubjectScreen.route) {
        composable(route = Screen.SignInScreen.route) {
            SignInScreen(navController = navController)

        }

        composable(route = Screen.SignUpScreen.route) {
            SignUpScreen(navController = navController)
        }

        composable(route = Screen.ChooseSubjectScreen.route) {
            ChooseSubjectScreen(navController = navController)
        }

        composable(route = Screen.HomeScreen.route) {
            HomeScreen(navController = navController)
        }

        composable(route = Screen.ChooseTopicScreen.route) {
            ChooseTopicScreen(navController = navController)
        }

        composable(route = Screen.TestScreen.route) {
            TestScreen(navController = navController)
        }

        composable(route = Screen.HistoryScreen.route) {
            HistoryScreen(navController = navController)
        }

        composable(route = Screen.RateScreen.route) {
            RateScreen(navController = navController)
        }

        composable(route = Screen.AdminScreen.route) {
            AdminScreen(navController = navController)
        }

    }
}
