package com.upsidedowndev.vibeplayer.song.data.audio

import android.net.Uri

data class AudioMetadataDto(
    val filePath: String,
    val title: String?,
    val artist: String?,
    val thumbnail: ByteArray?,
    val duration: Long
) {

}