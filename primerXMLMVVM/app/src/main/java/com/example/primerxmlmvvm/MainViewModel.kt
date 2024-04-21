package com.example.primerxmlmvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MainState(0))
    val uiState: StateFlow<MainState> get() = _uiState


    fun sumar() {
        _uiState.update { _uiState.value.copy(contador = _uiState.value.contador + 1) }
    }

    fun restar() {
        _uiState.update { _uiState.value.copy(contador = _uiState.value.contador - 1) }
    }


}


/**
 * Factory class to instantiate the [ViewModel] instance.
 */
class MainViewModelFactory(


    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(

            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}