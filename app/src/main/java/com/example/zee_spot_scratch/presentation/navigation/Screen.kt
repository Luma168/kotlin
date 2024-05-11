package com.example.zee_spot_scratch.presentation.navigation

sealed class Screen(val route: String) {
    object Home: Screen(route = "home_screen")
    object NewRdv: Screen(route = "new_rdv_screen")
    object UpdateRdv: Screen(route = "update_rdv_screen")
    object Notification: Screen(route = "notifications")
}