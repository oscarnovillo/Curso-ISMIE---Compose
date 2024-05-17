package com.example.primerxmlmvvm.domain.usecases.coches

import com.example.primerxmlmvvm.data.CocheRepository
import com.example.primerxmlmvvm.domain.modelo.Coche
import javax.inject.Inject

class InsertCoche  @Inject constructor(val cocheRepository: CocheRepository){

    operator fun invoke(coche: Coche) = cocheRepository.insert(coche)
}