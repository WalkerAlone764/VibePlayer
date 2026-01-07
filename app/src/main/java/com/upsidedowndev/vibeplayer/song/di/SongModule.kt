package com.upsidedowndev.vibeplayer.song.di

import com.upsidedowndev.vibeplayer.song.data.audio.AndroidAudioFileDataSource
import com.upsidedowndev.vibeplayer.song.data.audio.AndroidAudioPlayer
import com.upsidedowndev.vibeplayer.song.data.audio.AndroidSongMetadataReader
import com.upsidedowndev.vibeplayer.song.data.repository.AudioRepositoryImpl
import com.upsidedowndev.vibeplayer.song.domain.audio.AudioFileDataSource
import com.upsidedowndev.vibeplayer.song.domain.audio.AudioPlayer
import com.upsidedowndev.vibeplayer.song.domain.audio.SongMetadataReader
import com.upsidedowndev.vibeplayer.song.domain.repository.AudioRepository
import com.upsidedowndev.vibeplayer.song.presentation.player.MusicPlayerViewModel
import com.upsidedowndev.vibeplayer.song.presentation.scan_result.ScanResultViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val songModule = module {

    single<AudioPlayer> {
        AndroidAudioPlayer(get())
    }

    factory<SongMetadataReader> {
        AndroidSongMetadataReader
    }

    single<AudioFileDataSource> {
        AndroidAudioFileDataSource(androidApplication())
    }

    single<AudioRepository> {
        AudioRepositoryImpl(get(), get(), get())
    }

    viewModelOf(::MusicPlayerViewModel)
    viewModelOf(::ScanResultViewModel)


}