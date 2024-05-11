package com.example.zee_spot_scratch.presentation.home_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.zee_spot_scratch.presentation.home_screen.components.CalendarComposable
import com.example.zee_spot_scratch.presentation.common.Footer
import com.example.zee_spot_scratch.presentation.common.Header
import com.example.zee_spot_scratch.presentation.RdvViewModel


@Composable
fun HomeScreen(
    viewModel: RdvViewModel = hiltViewModel(),
    navController: NavController,
) {
    Scaffold(
        topBar = { Header() },
        bottomBar = { Footer(navController = navController) },
        content = { padding -> 
            Box(modifier = Modifier.padding(padding)){
                CalendarComposable(viewModel, navController)
            }
        }
    )
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        navController = rememberNavController(),
    )
}