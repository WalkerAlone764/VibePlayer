package com.upsidedowndev.vibeplayer.song.data.audio.mappers

import androidx.core.net.toUri
import com.upsidedowndev.vibeplayer.song.data.audio.AudioMetadataDto
import com.upsidedowndev.vibeplayer.song.domain.audio.AudioMetadata

/**
 * Maps the data layer [AudioMetadataDto] to the domain layer [AudioMetadata].
 */
fun AudioMetadataDto.toDomain(): AudioMetadata {
    return AudioMetadata(
        title = this.title,
        artist = this.artist,
        duration = this.duration,
        filePath = this.filePath,
        thumbnail = this.thumbnail
    )
}

/**
 * Maps the domain layer [AudioMetadata] to the data layer [AudioMetadataDto].
 */
fun AudioMetadata.toDto(): AudioMetadataDto {
    return AudioMetadataDto(
        title = this.title,
        artist = this.artist,
        duration = this.duration,
        filePath = this.filePath,
        thumbnail = this.thumbnail
    )
}