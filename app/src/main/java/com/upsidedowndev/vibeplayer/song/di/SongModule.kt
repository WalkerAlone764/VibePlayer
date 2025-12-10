package com.upsidedowndev.vibeplayer.song.di

import com.upsidedowndev.vibeplayer.song.data.audio.AndroidAudioPlayer
import com.upsidedowndev.vibeplayer.song.data.audio.AndroidSongMetadataReader
import com.upsidedowndev.vibeplayer.song.domain.audio.AudioPlayer
import com.upsidedowndev.vibeplayer.song.domain.audio.SongMetadataReader
import com.upsidedowndev.vibeplayer.song.presentation.player.MusicPlayerViewModel
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

    viewModelOf(::MusicPlayerViewModel)


}