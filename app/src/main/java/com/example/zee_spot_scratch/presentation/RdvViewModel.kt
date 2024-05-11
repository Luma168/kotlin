package com.example.zee_spot_scratch.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zee_spot_scratch.data.repository.RdvRepository
import com.example.zee_spot_scratch.domain.model.Rdv
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class RdvViewModel @Inject constructor(
    private val repository: RdvRepository
) : ViewModel() {
    var rdv by mutableStateOf(
        Rdv(
        0,
        "",
        "",
        "",
            LocalDate.now(),
            LocalDate.now(),
            LocalTime.of(LocalTime.now().hour,LocalTime.now().minute,0),
            LocalTime.of(LocalTime.now().hour,LocalTime.now().minute,0)
        )
    )
        private set


    val allRdvs = repository.getAllRdvs()

    fun addRdv(rdv: Rdv){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addRdv(rdv)
        }
    }

    fun deleteRdv(rdv: Rdv){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteRdv(rdv)
        }
    }

    fun getRdvById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            rdv = repository.getRdvById(id)
        }
    }

    fun updateRdv(rdv: Rdv){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateRdv(rdv)
        }
    }

    fun updateClient(newValue: String) {
        rdv = rdv.copy(client = newValue)
    }

    fun updateFormule(newValue: String) {
        rdv = rdv.copy(formule = newValue)
    }

    fun updateDetails(newValue: String) {
        rdv = rdv.copy(details = newValue)
    }

    fun updateStartDate(newValue: LocalDate) {
        rdv = rdv.copy(startDate = newValue)
    }

    fun updateEndDate(newValue: LocalDate) {
        rdv = rdv.copy(endDate = newValue)
    }

    fun updateStartTime(newValue: LocalTime) {
        rdv = rdv.copy(startTime = newValue)
    }

    fun updateEndTime(newValue: LocalTime) {
        rdv = rdv.copy(endTime = newValue)
    }

}