package com.upsidedowndev.vibeplayer.song.presentation.songs

import com.upsidedowndev.vibeplayer.song.domain.model.AudioMetadata
import com.upsidedowndev.vibeplayer.song.presentation.songs.models.ScanningState

data class SongsState(
    val scanningState: ScanningState = ScanningState.LOADING,
    val isLoading: Boolean = true,
    val listOfAudioMetadata: List<AudioMetadata> = emptyList()
)