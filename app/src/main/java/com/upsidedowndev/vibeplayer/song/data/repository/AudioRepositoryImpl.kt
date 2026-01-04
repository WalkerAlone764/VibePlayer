package com.upsidedowndev.vibeplayer.song.data.repository

import android.util.Log
import com.upsidedowndev.vibeplayer.core.database.data.dao.AudioMetadataDao
import com.upsidedowndev.vibeplayer.song.domain.audio.AudioFileDataSource
import com.upsidedowndev.vibeplayer.song.domain.audio.AudioMetadata
import com.upsidedowndev.vibeplayer.song.domain.audio.SongMetadataReader
import com.upsidedowndev.vibeplayer.song.domain.mappers.toEntity
import com.upsidedowndev.vibeplayer.song.domain.repository.AudioRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.collections.map

class AudioRepositoryImpl(
    private val dao: AudioMetadataDao,
    private val audioFileDataSource: AudioFileDataSource,
    private val reader: SongMetadataReader
): AudioRepository {

    override suspend fun getAudioFiles() {
        val audios = audioFileDataSource.getAudioFiles()
        val audiosEntity = audios.map { it.toEntity() }
        dao.insertAll(audiosEntity)
    }

    override fun collectAudioFiles(): Flow<List<AudioMetadata>> {
        return dao
            .getAll()
            .map { audios ->
                audios.map { file ->
                    reader.getMetadata(file.uriString)
                }
            }
    }
}