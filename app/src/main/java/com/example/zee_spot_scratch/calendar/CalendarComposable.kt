package com.example.zee_spot_scratch.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.zee_spot_scratch.data.Rdv
import com.example.zee_spot_scratch.data.RdvViewModel
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.OutDateStyle
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.YearMonth



private val pageBackgroundColor: Color @Composable get() = Color.White
private val itemBackgroundColor: Color @Composable get() = Color.White
private val toolbarColor: Color @Composable get() = Color(0xFF231F1D)
private val selectedItemColor: Color @Composable get() = Color(0xFF231F1D)
private val inActiveTextColor: Color @Composable get() = Color.Gray

@Composable
fun CalendarComposable(
    viewModel: RdvViewModel = hiltViewModel(),
) {
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(500) }
    val endMonth = remember { currentMonth.plusMonths(500) }
    var selection by remember { mutableStateOf<CalendarDay?>(null) }
    val daysOfWeek = remember { daysOfWeek() }

    val rdvs by viewModel.allRdvs.collectAsState(initial = emptyList() )
    val sortedRdvs = rdvs.groupBy { it.startDate }

    Column(
        modifier = Modifier
            .background(pageBackgroundColor),
        verticalArrangement = Arrangement.Top
    ) {
        val state = rememberCalendarState(
            startMonth = startMonth,
            endMonth = endMonth,
            firstVisibleMonth = currentMonth,
            firstDayOfWeek = daysOfWeek.first(),
            outDateStyle = OutDateStyle.EndOfGrid,
        )
        val coroutineScope = rememberCoroutineScope()
        val visibleMonth = rememberFirstCompletelyVisibleMonth(state)

        MonthBar(
            modifier = Modifier
                .background(toolbarColor)
                .padding(horizontal = 8.dp, vertical = 12.dp),
            currentMonth = visibleMonth.yearMonth,
            goToPrevious = {
                coroutineScope.launch {
                    state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.previousMonth)
                }
            },
            goToNext = {
                coroutineScope.launch {
                    state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.nextMonth)
                }
            },
        )

        HorizontalCalendar(
            modifier = Modifier.wrapContentWidth(),
            state = state,
            dayContent = { day ->
                val containsRdvs = day.position == DayPosition.MonthDate && sortedRdvs[day.date].orEmpty().isNotEmpty()
                Day(
                    day = day,
                    isSelected = selection == day,
                    containsRdvs = containsRdvs,
                ) { clicked ->
                    selection = clicked
                }
            },
            monthHeader = {
                DayBar(
                    modifier = Modifier.padding(vertical = 8.dp),
                    daysOfWeek = daysOfWeek,
                )
            },
        )

        Divider(color = toolbarColor, thickness = 4.dp)

        CurrentDay(selection = selection)

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(items = sortedRdvs[selection?.date]?.sortedBy { it.startTime } ?: emptyList()) { rdv ->
                RdvInformation(rdv, viewModel)
            }
        }

    }
}

@Composable
private fun CurrentDay(
    selection: CalendarDay?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        selection?.date?.dayOfWeek?.let { Text(text = it.displayText(), color = toolbarColor, fontSize = 20.sp, fontWeight = FontWeight.Bold)}
        Spacer(modifier = Modifier.width(4.dp))
        selection?.date?.dayOfMonth?.let { Text(text = it.toString(), color = toolbarColor, fontSize = 20.sp, fontWeight = FontWeight.Bold) }
        Spacer(modifier = Modifier.width(4.dp))
        selection?.date?.month?.let { Text(text = it.displayText(), color = toolbarColor, fontSize = 20.sp, fontWeight = FontWeight.Bold) }
        Spacer(modifier = Modifier.width(4.dp))
        selection?.date?.year?.let { Text(text = it.toString(), color = toolbarColor, fontSize = 20.sp, fontWeight = FontWeight.Bold) }
    }
}

@Composable
private fun Day(
    day: CalendarDay,
    isSelected: Boolean = false,
    containsRdvs: Boolean,
    onClick: (CalendarDay) -> Unit = {},
) {
    ConstraintLayout(
        modifier = Modifier
            .size(70.dp)
            .aspectRatio(1f)
            .background(color = itemBackgroundColor)
            // Disable clicks on inDates/outDates
            .clickable(
                enabled = day.position == DayPosition.MonthDate,
                onClick = { onClick(day) },
            ),
    ) {
        val (dateBox, bubble) = createRefs()
        Box(
            modifier = Modifier
                .zIndex(1F)
                .size(45.dp)
                .clip(CircleShape)
                .background(color = if (isSelected) selectedItemColor else itemBackgroundColor)
                .constrainAs(dateBox) {
                    centerHorizontallyTo(parent)
                    centerVerticallyTo(parent)
                },
        ) {
            val textColor = when (day.position) {
                DayPosition.MonthDate -> if (isSelected) itemBackgroundColor else Color.Black
                DayPosition.InDate, DayPosition.OutDate -> inActiveTextColor
            }
            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = day.date.dayOfMonth.toString(),
                color = textColor,
                fontSize = 18.sp,
            )
        }
        if (containsRdvs) {
            Box(
                modifier = Modifier
                    .size(15.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE8B923))
                    //.background(Color.White)
                    .zIndex(2F)
                    .constrainAs(bubble) {
                        top.linkTo(parent.top, margin = 10.dp)
                        absoluteRight.linkTo(parent.absoluteRight, margin = 5.dp)
                    },
            )
        }
    }
}

@Composable
private fun DayBar(
    modifier: Modifier = Modifier,
    daysOfWeek: List<DayOfWeek> = emptyList(),
) {
    Row(
        modifier= Modifier
            .fillMaxWidth()
            .background(color = itemBackgroundColor)
            .height(30.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                fontSize = 15.sp,
                color = Color.Black,
                text = dayOfWeek.displayText(uppercase = true),
                fontWeight = FontWeight.Medium,
            )
        }
    }
}

@Composable
private fun LazyItemScope.RdvInformation(
    rdv: Rdv,
    viewModel: RdvViewModel,
) {
    Row(
        modifier = Modifier
            .height(130.dp)
            .padding(7.dp)
            .background(Color(0xFFE8B923))
            .border(width = 2.dp, color = toolbarColor),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(5.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = "Agenda", tint = toolbarColor )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    rdv.startTime?.let {
                        Text(
                            text = it.toString(),
                            fontSize = 15.sp,
                            color = toolbarColor,
                        )
                    }
                    rdv.endTime?.let {
                        Text(
                            text = it.toString(),
                            fontSize = 15.sp,
                            color = toolbarColor,
                        )
                    }
                }
            }
            Row(
            ) {
                rdv.formule?.let {
                    Text(
                        text = it,
                        textAlign = TextAlign.Center,
                        lineHeight = 17.sp,
                        fontSize = 14.sp,
                        color = toolbarColor,
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .weight(4f)
                .padding(5.dp)
                .fillMaxHeight(),
        ) {
            rdv.client?.let {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row{
                        Text(
                            text = "Rdv Pro : ",
                            textAlign = TextAlign.Center,
                            lineHeight = 17.sp,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = toolbarColor
                        )
                        Text(
                            text = it,
                            textAlign = TextAlign.Center,
                            lineHeight = 17.sp,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            color = toolbarColor
                        )
                    }
                    IconButton(onClick = { viewModel.deleteRdv(rdv) }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            tint = toolbarColor,
                        )
                    }
                }
            }
            Text(
                text = "Détails : ",
                textAlign = TextAlign.Center,
                lineHeight = 17.sp,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = toolbarColor
            )
            rdv.details?.let {
                Text(
                    text = it,
                    textAlign = TextAlign.Center,
                    lineHeight = 17.sp,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = toolbarColor
                )
            }
        }
    }
}