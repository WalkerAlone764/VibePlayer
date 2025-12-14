package com.upsidedowndev.vibeplayer.song.domain.audio

data class AudioMetadata(
    val title: String,
    val artist: String,
    val durationMs: String,
    val uriString: String,
    val image: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AudioMetadata

        if (durationMs != other.durationMs) return false
        if (title != other.title) return false
        if (artist != other.artist) return false
        if (uriString != other.uriString) return false
        if (!image.contentEquals(other.image)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = durationMs.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + artist.hashCode()
        result = 31 * result + uriString.hashCode()
        result = 31 * result + image.contentHashCode()
        return result
    }
}