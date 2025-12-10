package com.upsidedowndev.vibeplayer.app.di

import com.upsidedowndev.vibeplayer.app.VibeApplication
import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModule = module {

    single<CoroutineScope> {
        (androidApplication() as VibeApplication).applicationScope
    }
}