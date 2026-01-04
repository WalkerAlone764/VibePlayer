package com.upsidedowndev.vibeplayer.song.domain.mappers

import com.upsidedowndev.vibeplayer.core.database.data.entity.AudioEntity
import com.upsidedowndev.vibeplayer.song.domain.audio.AudioMetadata

fun AudioMetadata.toEntity(): AudioEntity {
    return AudioEntity(
        uriString = this.filePath,
    )
}

//fun AudioMetadataEntity.toDomain(): AudioMetadata {
//    return AudioMetadata(
//        uriString = uriString,
//    )
//}