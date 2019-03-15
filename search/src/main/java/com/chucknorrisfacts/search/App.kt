package com.chucknorrisfacts.search

import android.annotation.SuppressLint
import android.content.Context
import androidx.multidex.MultiDexApplication
import com.chucknorrisfacts.cache.CacheLibrary
import com.pacoworks.rxpaper2.RxPaperBook
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

//        CacheLibrary.init(this)
        RxPaperBook.init(this)

        startKoin(
            this,
            listOf(viewModelModule, useCaseModule, repositoryModule, dataSourceModule, networkModule, cacheModule)
        )
    }
}
