package com.upsidedowndev.vibeplayer.song.domain.audio

interface AudioFileDataSource {

    fun getAudioFiles(): List<AudioMetadata>
}