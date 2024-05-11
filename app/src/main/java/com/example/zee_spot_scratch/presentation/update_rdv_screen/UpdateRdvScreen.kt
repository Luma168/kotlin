package com.example.zee_spot_scratch.presentation.update_rdv_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.zee_spot_scratch.R
import com.example.zee_spot_scratch.presentation.common.Footer
import com.example.zee_spot_scratch.presentation.common.Header
import com.example.zee_spot_scratch.presentation.RdvViewModel
import com.example.zee_spot_scratch.presentation.navigation.Screen
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockConfig
import com.maxkeppeler.sheets.clock.models.ClockSelection
import java.time.LocalTime


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateRdvScreen(
    id: Int,
    viewModel: RdvViewModel = hiltViewModel(),
    navController: NavController
) {
    Scaffold(
        topBar = { Header() },
        bottomBar = { Footer(navController = navController) },
        content = { padding ->
            Box(modifier = Modifier.padding(padding)){
                LaunchedEffect(key1 = true, block = {
                    viewModel.getRdvById(id = id)
                })

                val client = viewModel.rdv.client

                val formuleList = listOf("Formule 1", "Formule 2", "Formule 3", "Formule 4")
                var expanded by remember { mutableStateOf(false) }
                var formule = viewModel.rdv.formule

                val details = viewModel.rdv.details
                var startDate = viewModel.rdv.startDate
                var endDate = viewModel.rdv.endDate
                var startTime = viewModel.rdv.startTime
                var endTime = viewModel.rdv.endTime


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
                        selectedDate = startDate
                    ) { newDate ->
                        startDate = newDate
                        viewModel.updateStartDate(newDate)
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
                        selectedDate = endDate
                    ) { newDate ->
                        endDate = newDate
                        viewModel.updateEndDate(newDate)
                    },
                )

                val startClockState = rememberSheetState()
                val endClockState = rememberSheetState()
                ClockDialog(
                    state = startClockState,
                    selection = ClockSelection.HoursMinutes { hours, minutes ->
                        startTime = LocalTime.of(hours, minutes, 0)
                        viewModel.updateStartTime(LocalTime.of(hours, minutes, 0))
                    },
                    config = ClockConfig(
                        defaultTime = startTime,
                        is24HourFormat = true
                    )
                )
                ClockDialog(
                    state = endClockState,
                    selection = ClockSelection.HoursMinutes { hours, minutes ->
                        endTime = LocalTime.of(hours, minutes, 0)
                        viewModel.updateEndTime(LocalTime.of(hours, minutes, 0))
                    },
                    config = ClockConfig(
                        defaultTime = endTime,
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
                    client?.let {
                        OutlinedTextField(
                            value = it,
                            onValueChange = { newValue -> viewModel.updateClient(newValue) },
                            label = { Text("Client") },
                            modifier = Modifier,
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color(0xFF231F1D),
                                focusedBorderColor = Color(0xFFE8B923),
                                focusedLabelColor = Color(0xFF231F1D)
                            )
                        )
                    }
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
                                formule?.let {
                                    OutlinedTextField(
                                        value = it,
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
                                }
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
                                            viewModel.updateFormule(formuleList[index])
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
                    details?.let {
                        OutlinedTextField(
                            value = it,
                            onValueChange = { newValue -> viewModel.updateDetails(newValue) },
                            label = { Text("Détails") },
                            modifier = Modifier,
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color(0xFF231F1D),
                                focusedBorderColor = Color(0xFFE8B923),
                                focusedLabelColor = Color(0xFF231F1D)
                            )
                        )
                    }
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
                            Text(text = startDate.toString())
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
                            Text(text = startTime.toString())
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
                            Text(text = endDate.toString())
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
                            Text(text = endTime.toString())
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
                            viewModel.updateRdv(viewModel.rdv)
                            navController.navigate(route = Screen.Home.route)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFE8B923),
                        )
                    ) {
                        Text(text = "Modifier")
                    }
                }

            }
        }
    )
}

@Preview
@Composable
fun UpdateRdvScreenPreview() {
    UpdateRdvScreen(id = 0, navController = rememberNavController())
}