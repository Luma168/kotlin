package com.example.zee_spot_scratch

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.zee_spot_scratch.components.Footer
import com.example.zee_spot_scratch.components.Header

@Composable
fun NotificationScreen(
    navController: NavController,
) {
    Scaffold(
        topBar = { Header() },
        bottomBar = { Footer(navController = navController) },
        content = { padding ->
            Column(
                modifier = Modifier.padding(padding).fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(text = "Cette fonctionnalité est actuellement en développement.")
            }
        }
    )
}

@Preview
@Composable
fun NotificationScreenPreview() {
    NewRdvScreen(navController = rememberNavController())
}
