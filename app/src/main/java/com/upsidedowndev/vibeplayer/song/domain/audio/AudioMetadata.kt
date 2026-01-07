package com.upsidedowndev.vibeplayer.song.domain.audio

import com.upsidedowndev.vibeplayer.core.presentation.util.formatDuration

data class AudioMetadata(
    val filePath: String,
    val title: String?,
    val artist: String?,
    val thumbnail: ByteArray?,
    val duration: Long
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AudioMetadata

        if (duration != other.duration) return false
        if (filePath != other.filePath) return false
        if (title != other.title) return false
        if (artist != other.artist) return false
        if (!thumbnail.contentEquals(other.thumbnail)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = duration.hashCode()
        result = 31 * result + filePath.hashCode()
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + (artist?.hashCode() ?: 0)
        result = 31 * result + (thumbnail?.contentHashCode() ?: 0)
        return result
    }

    val formattedDuration: String
        get() = formatDuration(duration)

}