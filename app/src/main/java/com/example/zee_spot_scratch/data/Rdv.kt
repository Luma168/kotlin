package com.example.zee_spot_scratch.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "rdv_table")
data class Rdv(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val client: String?,
    val formule: String?,
    val details: String?,
    val startDate: LocalDate?,
    val endDate: LocalDate?,
    val startTime: LocalTime?,
    val endTime: LocalTime?,
)