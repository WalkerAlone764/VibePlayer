package com.upsidedowndev.vibeplayer.song.presentation.songScreen

import com.upsidedowndev.vibeplayer.song.domain.model.AudioMetadata
import com.upsidedowndev.vibeplayer.song.presentation.songScreen.models.ScanningState

data class SongsState(
    val scanningState: ScanningState = ScanningState.LOADING,
    val isLoading: Boolean = true,
    val listOfAudioFiles: List<AudioMetadata> = emptyList(),
    val isFabVisible: Boolean = false
)