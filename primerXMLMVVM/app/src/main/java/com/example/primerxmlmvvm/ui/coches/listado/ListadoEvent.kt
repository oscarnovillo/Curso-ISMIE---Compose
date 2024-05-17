package com.example.primerxmlmvvm.ui.coches.listado

import com.example.primerxmlmvvm.domain.modelo.Coche

sealed class ListadoEvent {
    class DeleteCoche(val coche: Coche) : ListadoEvent()
    object UndoDeleteCoche : ListadoEvent()

    class changeFiltro(val filtro: String) : ListadoEvent()

    object UiEventDone : ListadoEvent()
}