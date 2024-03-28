package com.example.jsonkeeper.di

import com.example.jsonkeeper.api.repository.IRepository
import com.example.jsonkeeper.api.repository.RepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun  bindRepositoryImpl(impl : RepositoryImpl): IRepository
}