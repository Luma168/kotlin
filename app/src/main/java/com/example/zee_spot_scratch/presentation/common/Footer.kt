package com.example.zee_spot_scratch.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.zee_spot_scratch.presentation.navigation.Screen

@Composable
fun Footer(navController: NavController){
    Row(
        modifier = Modifier
        .background(Color(0xFF231F1D))
        .fillMaxWidth()
        .heightIn(80.dp, 80.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BarButton(icon = Icons.Default.DateRange, onClick = {
            navController.navigate(route = Screen.Home.route)
        })
        BarButton(icon = Icons.Default.AddCircle, onClick = {
            navController.navigate(route = Screen.NewRdv.route)
        })
        BarButton(icon = Icons.Default.Notifications, onClick = {
            navController.navigate(route = Screen.Notification.route)
        })
    }
}

@Composable
fun BarButton(icon: ImageVector, onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(icon, contentDescription = null, tint = Color(0xFFE8B923), modifier = Modifier.size(100.dp))
    }
}