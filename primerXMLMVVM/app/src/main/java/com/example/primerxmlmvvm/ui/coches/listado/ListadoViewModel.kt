package com.example.primerxmlmvvm.ui.coches.listado

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.primerxmlmvvm.data.CocheRepository
import com.example.primerxmlmvvm.di.IoDispatcher
import com.example.primerxmlmvvm.domain.modelo.Coche
import com.example.primerxmlmvvm.domain.usecases.coches.GetCoches
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ListadoViewModel @Inject constructor(
    val getCoches: GetCoches,
    @IoDispatcher val dispatcher: CoroutineDispatcher

) : ViewModel() {




    fun handleEvent(event: ListadoEvent) {
        when (event) {
            is ListadoEvent.ErrorMostrado -> {
                _uiState.value = _uiState.value.copy(error = null)
            }

            ListadoEvent.getCoches ->
                viewModelScope.launch(dispatcher) {
                    getCoches.invoke().let {
                        _uiState.value = _uiState.value.copy(coches = it)
                    }
                }
        }
    }

    private val _uiState: MutableStateFlow<ListadoState> by lazy {
        MutableStateFlow(ListadoState())
    }
    val uiState: StateFlow<ListadoState> = _uiState.asStateFlow()


}