package com.upsidedowndev.vibeplayer.app

import android.app.Application
import com.upsidedowndev.vibeplayer.app.di.appModule
import com.upsidedowndev.vibeplayer.song.di.songModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class VibeApplication: Application() {

    val applicationScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@VibeApplication)
            modules(appModule,songModule)
        }
    }

}