package com.example.jsonkeeper.di

import androidx.lifecycle.ViewModel
import com.example.jsonkeeper.viewmodel.JsonKeeperViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(JsonKeeperViewModel::class)
    abstract fun bindViewModel(viewModel: JsonKeeperViewModel): ViewModel
}