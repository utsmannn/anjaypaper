package com.utsmannn.anjaypaper

import android.app.Application
import android.content.Context
import com.utsmannn.anjaypaper.di.repository
import com.utsmannn.anjaypaper.di.viewModel
import com.utsmannn.pocketdb.Pocket
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AnjayApplication : Application() {

    companion object {
        lateinit var instance: AnjayApplication
        fun contextinisme(): Context = instance.applicationContext
    }

    private val secretKey = "utsmangantenkyah"

    override fun onCreate() {
        instance = this
        super.onCreate()
        startKoin {
            androidContext(this@AnjayApplication)
            Pocket.installKoinModule(this, secretKey)
            modules(repository, viewModel)
        }
    }
}