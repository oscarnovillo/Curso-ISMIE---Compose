package com.example.primerxmlmvvm.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.primerxmlmvvm.data.modelo.CocheEntity

@Database(entities = [CocheEntity::class], version = 2)
@TypeConverters(LocalDateConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cocheDao(): CocheDao
}