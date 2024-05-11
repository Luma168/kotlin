package com.example.zee_spot_scratch.data.repository


import com.example.zee_spot_scratch.data.local.RdvDao
import com.example.zee_spot_scratch.domain.model.Rdv
import kotlinx.coroutines.flow.Flow


class RdvRepository(private val rdvDao: RdvDao) {

    fun getAllRdvs() : Flow<List<Rdv>> = rdvDao.getAllRdvs()

    suspend fun getRdvById(id: Int): Rdv = rdvDao.getRdvById(id)
    suspend fun addRdv(rdv: Rdv) {
        rdvDao.addRdv(rdv)
    }

    suspend fun deleteRdv(rdv: Rdv) {
        rdvDao.deleteRdv(rdv)
    }

    suspend fun updateRdv(rdv: Rdv) {
        rdvDao.updateRdv(rdv)
    }
}