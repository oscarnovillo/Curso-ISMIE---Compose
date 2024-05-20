package com.example.primerxmlmvvm.domain.usecases.coches

import com.example.primerxmlmvvm.data.CocheRepository
import com.example.primerxmlmvvm.domain.modelo.Coche
import javax.inject.Inject

class DelCoches @Inject constructor(val cocheRepository: CocheRepository){

    operator fun invoke(coches: List<Coche>) = cocheRepository.delCoches(coches)
}