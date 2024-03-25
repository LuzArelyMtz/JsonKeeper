package com.example.jsonkeeper.di

import com.example.jsonkeeper.api.JsonKeeperAPIModule
import com.example.jsonkeeper.api.di.RepositoryModule
import com.example.jsonkeeper.ui.ListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, ViewModelModule::class, JsonKeeperAPIModule::class, RepositoryModule::class])
interface ApplicationComponent {
    fun inject(fragment: ListFragment)
}