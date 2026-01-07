package com.upsidedowndev.vibeplayer.song.domain.audio


interface SongMetadataReader {

    fun getMetadata(filePath: String): AudioMetadata
}