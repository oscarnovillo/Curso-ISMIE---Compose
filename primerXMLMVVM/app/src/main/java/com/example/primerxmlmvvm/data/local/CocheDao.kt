package com.example.primerxmlmvvm.data.local

import androidx.room.*
import com.example.primerxmlmvvm.data.modelo.CocheEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CocheDao {


    @Query("SELECT * FROM coches")
    fun getAllFlow(): Flow<List<CocheEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<CocheEntity>)

    @Delete
    fun delete(movie: CocheEntity)


    @Delete
    fun deleteAll(movie: List<CocheEntity>)

    @Query("SELECT * FROM coches")

    fun getAll(): List<CocheEntity>
    @Query("SELECT * FROM coches WHERE matricula = :matricula")
    fun getCoche(matricula: String): CocheEntity?
}