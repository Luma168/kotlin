package com.example.zee_spot_scratch.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.zee_spot_scratch.converters.DateConverter

@TypeConverters(value = [DateConverter::class])
@Database(entities = [Rdv::class], version = 6, exportSchema = false)
abstract class RdvDatabase : RoomDatabase() {
    abstract fun rdvDao() : RdvDao
}