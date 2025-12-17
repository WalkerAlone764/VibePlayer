package com.upsidedowndev.vibeplayer.song.domain.audio

import com.upsidedowndev.vibeplayer.song.domain.model.AudioMetadata
import com.upsidedowndev.vibeplayer.song.presentation.scanMusic.models.ScanFilterState
import kotlinx.coroutines.flow.Flow

interface AudioFileRepository {
    fun getAudioFiles(filters: ScanFilterState): Flow<List<AudioMetadata>>
}