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
        durationMs = this.durationMs,
        uriString = this.uri.toString(),
        image = this.image
    )
}

/**
 * Maps the domain layer [AudioMetadata] to the data layer [AudioMetadataDto].
 */
fun AudioMetadata.toDto(): AudioMetadataDto {
    return AudioMetadataDto(
        title = this.title,
        artist = this.artist,
        durationMs = this.durationMs,
        uri = this.uriString.toUri(),
        image = this.image
    )
}