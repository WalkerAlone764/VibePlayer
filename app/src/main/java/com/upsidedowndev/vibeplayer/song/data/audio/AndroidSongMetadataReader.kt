package com.upsidedowndev.vibeplayer.song.data.audio

import android.media.MediaMetadataRetriever
import android.util.Log
import com.upsidedowndev.vibeplayer.song.domain.audio.SongMetadataReader
import com.upsidedowndev.vibeplayer.song.domain.model.SongMetadata

object AndroidSongMetadataReader: SongMetadataReader {

    override fun getMetadata(filePath: String): SongMetadata {
        val retriever = MediaMetadataRetriever()
        try {
            retriever.setDataSource(filePath)

            val title = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)
            val artist = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)
            val duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)?.toLong() ?: 0
            val image = retriever.embeddedPicture

            Log.d("SongMetadataReader", "Title: $title, Artist: $artist, Duration: $duration, Image size: ${image?.size}")

            return SongMetadata(filePath, title, artist, image, duration)
        } catch (e: Exception) {
            Log.e("SongMetadataReader", "Error reading metadata for $filePath", e)
            // Return empty metadata on error
            return SongMetadata(filePath, null, null, null, 0)
        } finally {
            retriever.release()
        }
    }

}