package com.example.primerxmlmvvm.ui.users.detalle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.primerxmlmvvm.data.remote.NetworkResult
import com.example.primerxmlmvvm.di.IoDispatcher
import com.example.primerxmlmvvm.domain.usecases.users.DelUser
import com.example.primerxmlmvvm.domain.usecases.users.GetUser
import com.example.primerxmlmvvm.ui.common.UiEvent
import com.example.primerxmlmvvm.ui.users.detalle.DetalleUsersContract.DetalleUsersEvent
import com.example.primerxmlmvvm.ui.users.detalle.DetalleUsersContract.DetalleUsersState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetalleUsersViewModel @Inject constructor(
    val getUser: GetUser,
    val delUser: DelUser,
    @IoDispatcher val dispatcher: CoroutineDispatcher
) : ViewModel() {


    private val _uiState: MutableStateFlow<DetalleUsersState> by lazy {
        MutableStateFlow(DetalleUsersState())
    }
    val uiState: StateFlow<DetalleUsersState> = _uiState.asStateFlow()


    fun handleEvent(event: DetalleUsersEvent) {
        when (event) {
            DetalleUsersEvent.UiEventDone -> {
                _uiState.update { it.copy(event = null) }
            }

            is DetalleUsersEvent.GetUser -> viewModelScope.launch(dispatcher) {
                getUser.invoke(event.id).apply {
                    when (this) {
                        is NetworkResult.Success -> _uiState.update {
                            it.copy(user = this.data, isLoading = false)
                        }

                        is NetworkResult.Error -> tratarError(this.message)

                        is NetworkResult.Loading -> _uiState.update { it.copy(isLoading = true) }

                    }
                }
            }

            is DetalleUsersEvent.DelUser -> viewModelScope.launch(dispatcher) {
                delUser.invoke(event.id).apply {
                    when (this) {
                        is NetworkResult.Success -> {
                            _uiState.update {
                                it.copy(event = UiEvent.PopBackStack, isLoading = false)
                            }
                        }

                        is NetworkResult.Error -> tratarError(this.message)

                        is NetworkResult.Loading -> _uiState.update { it.copy(isLoading = true) }
                    }
                }
            }
        }
    }

    private fun tratarError(message: String?) {
        _uiState.update {
            it.copy(
                event = message?.let { message -> UiEvent.ShowSnackbar(message) },
                isLoading = false
            )
        }
    }
}