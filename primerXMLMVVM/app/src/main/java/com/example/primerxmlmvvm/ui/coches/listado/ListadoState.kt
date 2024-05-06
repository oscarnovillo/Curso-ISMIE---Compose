package com.example.primerxmlmvvm.ui.coches.listado

import com.example.primerxmlmvvm.domain.modelo.Coche

data class ListadoState(
    val coches: List<Coche> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
