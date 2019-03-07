package com.chucknorrisfacts.search

import android.annotation.SuppressLint
import android.content.Context
import androidx.multidex.MultiDexApplication
import org.koin.android.ext.android.startKoin

open class App : MultiDexApplication() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var appContext: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
        startKoin(
            this,
            listOf(viewModelModule, useCaseModule, repositoryModule, dataSourceModule, networkModule, cacheModule)
        )
    }
}
