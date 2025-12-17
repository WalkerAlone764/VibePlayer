package com.upsidedowndev.vibeplayer.song.data.audio

import android.content.Context
import com.upsidedowndev.vibeplayer.song.data.audio.mappers.toDomain
import com.upsidedowndev.vibeplayer.song.domain.audio.AudioFileRepository
import com.upsidedowndev.vibeplayer.song.domain.model.AudioMetadata
import com.upsidedowndev.vibeplayer.song.presentation.scanMusic.models.ScanFilterState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AudioFileRepositoryImpl(
    private val context: Context
) : AudioFileRepository {
    override fun getAudioFiles(filters: ScanFilterState): Flow<List<AudioMetadata>> = flow {
        val audioFiles = getAudioFilesFromMediaStore(context, filters)
        emit(audioFiles.map { it.toDomain() })
    }.flowOn(Dispatchers.IO)
}