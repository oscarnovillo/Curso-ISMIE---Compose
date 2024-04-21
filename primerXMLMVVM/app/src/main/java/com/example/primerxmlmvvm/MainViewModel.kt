package com.example.primerxmlmvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MainState(0,null))
    val uiState: StateFlow<MainState> get() = _uiState
    fun handleEvent(event: MainEvent) {
        when (event) {
            MainEvent.Sumar -> {
                sumar()
            }
            MainEvent.Restar -> {
                restar()
            }

            MainEvent.ErrorMostrado -> TODO()
        }
    }

    private fun sumar() {
        _uiState.update { _uiState.value.copy(contador = _uiState.value.contador + 1) }
    }

    private fun restar() {
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