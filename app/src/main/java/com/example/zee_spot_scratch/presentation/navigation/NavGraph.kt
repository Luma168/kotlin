package com.example.zee_spot_scratch.presentation.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.zee_spot_scratch.presentation.home_screen.HomeScreen
import com.example.zee_spot_scratch.presentation.new_rdv_screen.NewRdvScreen
import com.example.zee_spot_scratch.presentation.notification_screen.NotificationScreen
import com.example.zee_spot_scratch.presentation.update_rdv_screen.UpdateRdvScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(
            route = Screen.Home.route,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(300)
                )
            }
        ) {
            HomeScreen(navController = navController)
        }
        composable(
            route = Screen.NewRdv.route,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(300)
                )
            }
        ) {
            NewRdvScreen(navController = navController)
        }
        composable(
            route = "${Screen.UpdateRdv.route}/{id}",
            arguments = listOf(navArgument("id"){
                type = NavType.IntType
            }),
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(300)
                )
            }
        ) { backStackEntry ->
            backStackEntry.arguments?.getInt("id").let { id ->
                    UpdateRdvScreen(id = id!!, navController = navController)
            }
        }
        composable(
            route = Screen.Notification.route,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(300)
                )
            }
        ) {
            NotificationScreen(navController = navController)
        }
    }
}