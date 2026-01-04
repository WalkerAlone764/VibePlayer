package com.upsidedowndev.vibeplayer.core.database.domain.repository

import com.upsidedowndev.vibeplayer.song.domain.audio.AudioMetadata
import kotlinx.coroutines.flow.Flow

interface RoomAudioDataSource {

    suspend fun insert(audioMetadata: AudioMetadata)

    fun getAll(): Flow<List<AudioMetadata>>

    suspend fun deleteAll()
}
