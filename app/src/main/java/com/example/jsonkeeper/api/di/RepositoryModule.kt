package com.example.jsonkeeper.api.di

import com.example.jsonkeeper.api.IJsonKeeperAPIClient
import com.example.jsonkeeper.api.repository.IRepository
import com.example.jsonkeeper.api.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun provideRepository(jsonKeeperAPIClient: IJsonKeeperAPIClient): IRepository =
        RepositoryImpl(jsonKeeperAPIClient)
}