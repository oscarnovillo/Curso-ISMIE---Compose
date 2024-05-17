package com.example.primerxmlmvvm.ui.coches.listado

import com.example.primerxmlmvvm.domain.modelo.Coche
import com.example.primerxmlmvvm.ui.common.UiEvent

data class ListadoState(
    val coches: List<Coche> = emptyList(),
    val isLoading: Boolean = false,
    val event: UiEvent? = null
)
