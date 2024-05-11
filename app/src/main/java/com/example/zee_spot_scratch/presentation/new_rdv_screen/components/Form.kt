package com.example.zee_spot_scratch.presentation.new_rdv_screen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.zee_spot_scratch.R
import com.example.zee_spot_scratch.presentation.navigation.Screen
import com.example.zee_spot_scratch.domain.model.Rdv
import com.example.zee_spot_scratch.presentation.RdvViewModel
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockConfig
import com.maxkeppeler.sheets.clock.models.ClockSelection
import java.time.LocalDate
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Form(navController: NavController, viewModel: RdvViewModel){
    var client by remember { mutableStateOf("") }

    val formuleList = listOf("Formule 1", "Formule 2", "Formule 3", "Formule 4")
    var expanded by remember { mutableStateOf(false) }
    var formule by remember { mutableStateOf(formuleList[0]) }

    var details by remember { mutableStateOf("") }
    val startDate = remember { mutableStateOf<LocalDate?>(LocalDate.now()) }
    val endDate = remember { mutableStateOf<LocalDate?>(LocalDate.now()) }
    val startTime = remember { mutableStateOf(
        LocalTime.of(
            LocalTime.now().hour,
            LocalTime.now().minute,0))}
    val endTime = remember { mutableStateOf(
        LocalTime.of(
            LocalTime.now().hour,
            LocalTime.now().minute,0))}


    val startCalendarState = rememberSheetState()
    val endCalendarState = rememberSheetState()
    CalendarDialog(
        state = startCalendarState,
        config = CalendarConfig(
            yearSelection = true,
            monthSelection = true,
            style = CalendarStyle.MONTH,
        ),
        selection = CalendarSelection.Date(
            selectedDate = startDate.value
        ) { newDate ->
            startDate.value = newDate
        },
    )
    CalendarDialog(
        state = endCalendarState,
        config = CalendarConfig(
            yearSelection = true,
            monthSelection = true,
            style = CalendarStyle.MONTH,
        ),
        selection = CalendarSelection.Date(
            selectedDate = endDate.value
        ) { newDate ->
            endDate.value = newDate
        },
    )

    val startClockState = rememberSheetState()
    val endClockState = rememberSheetState()
    ClockDialog(
        state = startClockState,
        selection = ClockSelection.HoursMinutes { hours, minutes ->
            startTime.value = LocalTime.of(hours, minutes, 0)
        },
        config = ClockConfig(
            defaultTime = startTime.value,
            is24HourFormat = true
        )
    )
    ClockDialog(
        state = endClockState,
        selection = ClockSelection.HoursMinutes { hours, minutes ->
            endTime.value = LocalTime.of(hours, minutes, 0)
        },
        config = ClockConfig(
            defaultTime = endTime.value,
            is24HourFormat = true
        )
    )



    Column (
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.size(20.dp))
        OutlinedTextField(
            value = client,
            onValueChange = { client = it },
            label = { Text("Client") },
            modifier = Modifier,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFF231F1D),
                focusedBorderColor = Color(0xFFE8B923),
                focusedLabelColor = Color(0xFF231F1D)
            )
        )
        Spacer(modifier = Modifier.size(20.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = formule,
                    onValueChange = {},
                    label = { Text("Formule") },
                    modifier = Modifier.menuAnchor(),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color(0xFF231F1D),
                        focusedBorderColor = Color(0xFFE8B923),
                        focusedLabelColor = Color(0xFF231F1D)
                    ),
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)}
                )
                ExposedDropdownMenu(
                    modifier = Modifier.background(Color.White),
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    formuleList.forEachIndexed { index, text ->
                        DropdownMenuItem(
                            modifier = Modifier.background(Color.White),
                            text = { Text(text = text) },
                            onClick = {
                                formule = formuleList[index]
                                expanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.size(20.dp))
        OutlinedTextField(
            value = details,
            onValueChange = { details = it },
            label = { Text("Détails") },
            modifier = Modifier,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFF231F1D),
                focusedBorderColor = Color(0xFFE8B923),
                focusedLabelColor = Color(0xFF231F1D)
            )
        )
        Spacer(modifier = Modifier.size(30.dp))
        Text(text = "Début: ")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            Row(
                modifier = Modifier
                    .border(BorderStroke(1.dp, Color(0xFF231F1D)), RoundedCornerShape(8.dp)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.size(10.dp))
                Text(text = startDate.value.toString())
                Spacer(modifier = Modifier.size(4.dp))
                IconButton(onClick = {startCalendarState.show()}) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Calendar"
                    )
                }
            }

            Spacer(modifier = Modifier.size(45.dp))

            Row(
                modifier = Modifier
                    .border(BorderStroke(1.dp, Color(0xFF231F1D)), RoundedCornerShape(8.dp)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.size(10.dp))
                Text(text = startTime.value.toString())
                Spacer(modifier = Modifier.size(4.dp))
                IconButton(onClick = {startClockState.show()}) {
                    val imagePainter: Painter = painterResource(R.drawable.clock)
                    Image(
                        painter = imagePainter,
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
        }

        Text(text = "Fin: ")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .border(BorderStroke(1.dp, Color(0xFF231F1D)), RoundedCornerShape(8.dp)),
                verticalAlignment = Alignment.CenterVertically
            )  {
                Spacer(modifier = Modifier.size(10.dp))
                Text(text = endDate.value.toString())
                Spacer(modifier = Modifier.size(4.dp))
                IconButton(onClick = {endCalendarState.show()}) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Calendar"
                    )
                }
            }

            Spacer(modifier = Modifier.size(45.dp))

            Row(
                modifier = Modifier
                    .border(BorderStroke(1.dp, Color(0xFF231F1D)), RoundedCornerShape(8.dp)),
                verticalAlignment = Alignment.CenterVertically
            )  {
                Spacer(modifier = Modifier.size(10.dp))
                Text(text = endTime.value.toString())
                Spacer(modifier = Modifier.size(4.dp))
                IconButton(onClick = {endClockState.show()}) {
                    val imagePainter: Painter = painterResource(R.drawable.clock)
                    Image(
                        painter = imagePainter,
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.size(50.dp))
        Button(
            onClick = {
                val rdv = startDate.value?.let { start ->
                    endDate.value?.let { end ->
                        Rdv(0, client, formule, details, start, end, startTime.value, endTime.value)
                    }
                }
                if (rdv != null) {
                    viewModel.addRdv(rdv)
                }
                navController.navigate(route = Screen.Home.route)
            },
            enabled = client.isNotEmpty() &&
                    formule.isNotEmpty() &&
                    details.isNotEmpty() &&
                    startDate.value != null &&
                    startTime.value != null &&
                    endDate.value != null &&
                    endTime.value != null,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE8B923),
            )
        ) {
            Text(text = "Ajouter")
        }
    }
}