package com.example.primerxmlmvvm.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

import dagger.hilt.android.qualifiers.ApplicationContext
import com.example.primerxmlmvvm.data.local.AppDatabase
import com.example.primerxmlmvvm.data.local.CocheDao
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "app.db"
        ).fallbackToDestructiveMigration()
            .createFromAsset("database/coches.db")
            .build()
    }



    @Provides
    fun provideMovieDao(appDatabase: AppDatabase): CocheDao {
        return appDatabase.cocheDao()
    }
}