package com.example.primerxmlmvvm.ui.coches.detalle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.primerxmlmvvm.di.IoDispatcher
import com.example.primerxmlmvvm.domain.usecases.coches.GetCoche
import com.example.primerxmlmvvm.ui.coches.detalle.DetalleContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetalleViewModel @Inject constructor(
    val getCoche: GetCoche,
    @IoDispatcher val dispatcher: CoroutineDispatcher
) : ViewModel() {


    private val _uiState: MutableStateFlow<DetalleState> by lazy {
        MutableStateFlow(DetalleState())
    }
    val uiState: StateFlow<DetalleState> = _uiState.asStateFlow()


    fun handleEvent(event: DetalleEvent) {
        when (event) {
            DetalleEvent.MensajeMostrado -> {
                _uiState.value = _uiState.value.copy(error = null)
            }

            is DetalleEvent.GetCoche ->
                viewModelScope.launch(dispatcher) {
                    _uiState.value = _uiState.value.copy(coche = getCoche.invoke(event.matricula))
                }

        }
    }


}