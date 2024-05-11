package com.example.zee_spot_scratch.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.zee_spot_scratch.converters.DateConverter
import com.example.zee_spot_scratch.domain.model.Rdv

@TypeConverters(value = [DateConverter::class])
@Database(entities = [Rdv::class], version = 7, exportSchema = false)
abstract class RdvDatabase : RoomDatabase() {
    abstract fun rdvDao() : RdvDao
}