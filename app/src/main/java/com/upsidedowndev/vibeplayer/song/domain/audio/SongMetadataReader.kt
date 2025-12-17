package com.upsidedowndev.vibeplayer.song.domain.audio

import com.upsidedowndev.vibeplayer.song.domain.model.SongMetadata

interface SongMetadataReader {
    fun getMetadata(filePath: String): SongMetadata
}