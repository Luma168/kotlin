package com.example.zee_spot_scratch.data


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface RdvDao  {
    @Query("SELECT * FROM rdv_table ORDER BY id ASC")
    fun getAllRdvs(): Flow<List<Rdv>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addRdv(rdv: Rdv)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateRdv(rdv: Rdv)

    @Delete
    suspend fun deleteRdv(rdv: Rdv)
}