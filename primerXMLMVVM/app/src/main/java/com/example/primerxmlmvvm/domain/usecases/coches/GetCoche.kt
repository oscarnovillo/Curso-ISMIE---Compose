package com.example.primerxmlmvvm.domain.usecases.coches

import com.example.primerxmlmvvm.data.CocheRepository
import javax.inject.Inject

class GetCoche @Inject constructor(val cocheRepository: CocheRepository){

        operator fun invoke(matricula: String) = cocheRepository.getCoche(matricula)
}