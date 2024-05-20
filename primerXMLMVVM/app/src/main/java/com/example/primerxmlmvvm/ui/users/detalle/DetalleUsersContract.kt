package com.example.primerxmlmvvm.ui.users.detalle

import com.example.primerxmlmvvm.domain.modelo.User
import com.example.primerxmlmvvm.ui.common.UiEvent

interface DetalleUsersContract {
    sealed class DetalleUsersEvent {
        class GetUser(val id: Int) : DetalleUsersEvent()
        class DelUser(val id: Int) : DetalleUsersEvent()
        object UiEventDone : DetalleUsersEvent()
    }

    data class DetalleUsersState(
        val user: User? = null,
        val isLoading: Boolean = false,
        val event: UiEvent? = null,
        )
}