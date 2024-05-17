package com.example.primerxmlmvvm.ui.coches.listado

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.primerxmlmvvm.di.IoDispatcher
import com.example.primerxmlmvvm.domain.modelo.Coche
import com.example.primerxmlmvvm.domain.usecases.coches.DelCoche
import com.example.primerxmlmvvm.domain.usecases.coches.GetCochesFlow
import com.example.primerxmlmvvm.domain.usecases.coches.InsertCoche
import com.example.primerxmlmvvm.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMap
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject




@HiltViewModel
class ListadoViewModel @Inject constructor(
    getCochesFlow: GetCochesFlow,
    val delCoche: DelCoche,
    val insertCoche: InsertCoche,
    @IoDispatcher val dispatcher: CoroutineDispatcher

) : ViewModel() {

    private var cocheBorrado: Coche? = null
    private val _uiEvent = MutableStateFlow<UiEvent?>(null)
    private val _filtro = MutableStateFlow<String?>(null)


    val uiState: StateFlow<ListadoState> =
        combine( getCochesFlow(_filtro.value), _uiEvent) { coches, error ->
            ListadoState(coches, false, error)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ListadoState()
        )


    fun handleEvent(event: ListadoEvent) {
        when (event) {
            is ListadoEvent.UiEventDone -> {
                _uiEvent.update { null }
            }

            is ListadoEvent.DeleteCoche -> viewModelScope.launch(dispatcher) {
                cocheBorrado = event.coche
                delCoche(event.coche)
                _uiEvent.update { UiEvent.ShowSnackbar("Coche eliminado", "UNDO") }
            }

            ListadoEvent.UndoDeleteCoche -> {
                cocheBorrado?.let {
                    viewModelScope.launch(dispatcher) { insertCoche(it) }
                }
            }

            is ListadoEvent.changeFiltro -> _filtro.update{ event.filtro }
        }

    }
}