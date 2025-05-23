package com.senacgrupovinteecinco.pet_connect.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.senacgrupovinteecinco.pet_connect.data.repository.PetRepository
import com.senacgrupovinteecinco.pet_connect.model.Pet
import com.senacgrupovinteecinco.pet_connect.model.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PetViewModel : ViewModel() {
    private val repository = PetRepository()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _pets = MutableStateFlow<List<Pet>>(emptyList())
    val pets = _pets.asStateFlow()

    fun loadPets() {
        viewModelScope.launch {
            when (val result = repository.getPets()) {
                is Result.Success<*> -> _pets.value = result.data as List<Pet>
                is Result.Failure<*> -> showError(result.error.toString())
            }
        }
    }

    private fun showError(error: String) {
        // Trate erros (ex: mostrar Toast/Snackbar)
    }
}