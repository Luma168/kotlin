package com.example.zee_spot_scratch

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(
            route = Screen.Home.route
        ) {
            HomeScreen(navController = navController)
        }
        composable(
            route = Screen.NewRdv.route
        ) {
            NewRdvScreen(navController = navController)
        }
        composable(
            route = Screen.Notification.route
        ) {
            NotificationScreen(navController = navController)
        }
    }
}