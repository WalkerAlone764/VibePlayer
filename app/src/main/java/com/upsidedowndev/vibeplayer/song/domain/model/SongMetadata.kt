package com.upsidedowndev.vibeplayer.song.domain.model

data class SongMetadata(
    val filePath: String,
    val title: String?,
    val artist: String?,
    val thumbnail: ByteArray?,
    val duration: Long
)
