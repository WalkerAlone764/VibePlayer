package com.upsidedowndev.vibeplayer.core.di

import androidx.room.Room
import com.upsidedowndev.vibeplayer.core.database.data.AudioDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            AudioDatabase::class.java,
            "vibe_player_db"
        ).build()
    }

    single {
        val database = get<AudioDatabase>()
        database.audioMetadataDao()
    }

}
