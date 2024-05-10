package com.example.zee_spot_scratch

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.zee_spot_scratch.components.Footer
import com.example.zee_spot_scratch.components.Form
import com.example.zee_spot_scratch.components.Header
import com.example.zee_spot_scratch.data.RdvViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewRdvScreen(
    viewModel: RdvViewModel = hiltViewModel(),
    navController: NavController
) {
    Scaffold(
        topBar = { Header() },
        bottomBar = { Footer(navController = navController) },
        content = { padding ->
            Box(modifier = Modifier.padding(padding)){
                Form(navController = navController, viewModel = viewModel )
            }
        }
    )
}

@Preview
@Composable
fun NewRdvScreenPreview() {
    NewRdvScreen(navController = rememberNavController())
}