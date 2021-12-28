package com.chus.clua.breakingbad

import android.app.Application
import com.chus.clua.breakingbad.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        initDi()

    }

    private fun initDi() {
        startKoin {

            androidContext(this@App)

            modules(
                listOf(
                    apiModule,
                    retrofitModule,
                    dataBaseModule,
                    repositoriesModule,
                    useCasesModule,
                    viewModelModule
                )
            )
        }
    }
}