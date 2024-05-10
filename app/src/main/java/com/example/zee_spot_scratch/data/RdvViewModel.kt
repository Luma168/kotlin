package com.example.zee_spot_scratch.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RdvViewModel @Inject constructor(
    private val repository: RdvRepository
) : ViewModel() {
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

    fun updateRdv(rdv: Rdv){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateRdv(rdv)
        }
    }
}