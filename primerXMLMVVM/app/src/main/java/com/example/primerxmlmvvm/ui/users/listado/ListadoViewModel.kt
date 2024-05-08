package com.example.primerxmlmvvm.ui.users.listado

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.primerxmlmvvm.data.remote.NetworkResult
import com.example.primerxmlmvvm.di.IoDispatcher
import com.example.primerxmlmvvm.domain.usecases.users.GetUsers
import com.example.primerxmlmvvm.domain.usecases.users.GetUser
import com.example.primerxmlmvvm.ui.users.listado.ListadoContract.ListadoEvent
import com.example.primerxmlmvvm.ui.users.listado.ListadoContract.ListadoState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class ListadoViewModel @Inject constructor(
    val getUsers: GetUsers,
    val getUser: GetUser,
    @IoDispatcher val dispatcher: CoroutineDispatcher

) : ViewModel() {
    private val _uiState: MutableStateFlow<ListadoState> by lazy {
        MutableStateFlow(ListadoState())
    }
    val uiState: StateFlow<ListadoState> = _uiState.asStateFlow()

    fun handleEvent(event: ListadoEvent) {
        when (event) {
            is ListadoEvent.ErrorMostrado -> {
                _uiState.value = _uiState.value.copy(error = null)
            }

            ListadoEvent.getUsers ->
                viewModelScope.launch(dispatcher) {
                    getUsers.invoke().let {
                        when (it) {
                            is NetworkResult.Error -> _uiState.value =
                                _uiState.value.copy(error = it.message, isLoading = false)

                            is NetworkResult.Loading -> _uiState.value =
                                _uiState.value.copy(isLoading = true)

                            is NetworkResult.Success -> _uiState.value =
                                _uiState.value.copy(users = it.data ?: emptyList(), isLoading = false)
                        }

                    }
                }

            is ListadoEvent.getUser -> viewModelScope.launch(dispatcher) {
                val user = getUser.invoke(event.id).data
                    Timber.d(user?.name)


            }
        }
    }




}