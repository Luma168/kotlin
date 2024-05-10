package com.example.zee_spot_scratch.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.zee_spot_scratch.R

@Composable
fun Header() {
    Row(
        modifier = Modifier
            .background(Color(0xFF231F1D))
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val imagePainter: Painter = painterResource(R.drawable.zeespot)
        Image(
            painter = imagePainter,
            contentDescription = null,
            modifier = Modifier.size(80.dp)
        )
    }
}