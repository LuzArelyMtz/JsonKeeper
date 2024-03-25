package com.example.jsonkeeper

import android.app.Application
import com.example.jsonkeeper.di.ApplicationComponent
import com.example.jsonkeeper.di.ApplicationModule
import com.example.jsonkeeper.di.DaggerApplicationComponent

class MyApplication : Application() {
    lateinit var appComponent: ApplicationComponent

    private fun initDagger(application: MyApplication): ApplicationComponent =
        DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(application))
            .build()

    override fun onCreate() {
        super.onCreate()
        appComponent = initDagger(this)
    }
}
