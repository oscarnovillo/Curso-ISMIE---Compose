package com.example.primerxmlmvvm.ui.users.listado

import com.example.primerxmlmvvm.domain.modelo.Coche
import com.example.primerxmlmvvm.domain.modelo.User

interface ListadoContract {

    sealed class ListadoEvent {
        object getUsers : ListadoEvent()

        object ErrorMostrado: ListadoEvent()
    }

    data class ListadoState(
        val users: List<User> = emptyList(),
        val isLoading: Boolean = false,
        val error: String? = null
    )
}