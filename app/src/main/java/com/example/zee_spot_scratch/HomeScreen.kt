package com.example.zee_spot_scratch

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.zee_spot_scratch.calendar.CalendarComposable
import com.example.zee_spot_scratch.components.Footer
import com.example.zee_spot_scratch.components.Header
import com.example.zee_spot_scratch.data.RdvViewModel


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
                CalendarComposable()
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