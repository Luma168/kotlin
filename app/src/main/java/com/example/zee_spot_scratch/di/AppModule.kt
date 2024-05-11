package com.example.zee_spot_scratch.di

import android.content.Context
import androidx.room.Room
import com.example.zee_spot_scratch.data.local.RdvDao
import com.example.zee_spot_scratch.data.local.RdvDatabase
import com.example.zee_spot_scratch.data.repository.RdvRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideRdvDb(
        @ApplicationContext
        context: Context
    ) = Room.databaseBuilder(
        context,
        RdvDatabase::class.java,
        "rdv_database"
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideRdvDao(
        rdvDatabase: RdvDatabase
    ) = rdvDatabase.rdvDao()

    @Provides
    fun provideRdvRepository(
        rdvDao: RdvDao
    ): RdvRepository = RdvRepository(
        rdvDao
    )
}