package com.example.primerxmlmvvm.ui.coches.listado

sealed class ListadoEvent {
        object getCoches : ListadoEvent()
        object ErrorMostrado: ListadoEvent()
}