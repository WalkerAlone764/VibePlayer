package com.upsidedowndev.vibeplayer.song.domain.repository

import com.upsidedowndev.vibeplayer.song.domain.audio.AudioMetadata
import kotlinx.coroutines.flow.Flow

interface AudioRepository {
    suspend fun getAudioFiles()

    fun collectAudioFiles(): Flow<List<AudioMetadata>>
}