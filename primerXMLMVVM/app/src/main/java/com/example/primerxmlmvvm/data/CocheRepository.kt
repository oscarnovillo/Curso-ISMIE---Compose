package com.example.primerxmlmvvm.data

import com.example.primerxmlmvvm.data.local.CocheDao
import com.example.primerxmlmvvm.data.modelo.toCoche
import com.example.primerxmlmvvm.data.modelo.toCocheEntity
import com.example.primerxmlmvvm.di.IoDispatcher
import com.example.primerxmlmvvm.domain.modelo.Coche
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CocheRepository @Inject constructor(
    private val cocheDao: CocheDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    fun getCochesFlow(): Flow<List<Coche>> {
        return cocheDao.getAllFlow().map { listaCoches ->
            listaCoches.map { it.toCoche() }
        }
    }

    fun getCoches(): List<Coche> {
        return cocheDao.getAll().map { it.toCoche() }
    }

    fun insertAll(coches: List<Coche>) {
        cocheDao.insertAll(coches.map { it.toCocheEntity() } )
    }

    fun getCoche(matricula: String): Coche? {
        return cocheDao.getCoche(matricula)?.toCoche()

    }

    fun delCoche(coche: Coche): Any {
        return cocheDao.delete(coche.toCocheEntity())
    }
}


