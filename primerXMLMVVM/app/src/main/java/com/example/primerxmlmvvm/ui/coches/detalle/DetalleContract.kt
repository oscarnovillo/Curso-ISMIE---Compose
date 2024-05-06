package com.example.primerxmlmvvm.ui.coches.detalle

import com.example.primerxmlmvvm.domain.modelo.Coche

interface DetalleContract {
    sealed class DetalleEvent {
        class GetCoche(val matricula: String) : DetalleEvent()
        object DelCoche : DetalleEvent()

        object MensajeMostrado : DetalleEvent()
        object BorradoMostrado : DetalleEvent()
    }

    data class DetalleState(
        val coche: Coche? = null,
        val borrado : Boolean = false,
        val isLoading: Boolean = false,
        val error: String? = null,

        )
}