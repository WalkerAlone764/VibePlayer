package com.upsidedowndev.vibeplayer.song.domain.audio

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.upsidedowndev.vibeplayer.song.domain.model.AudioMetadata

fun getAudioFile(context: Context): List<AudioMetadata> {
    val audioMetadata = mutableListOf<AudioMetadata>()
    val collection = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
    val projection = arrayOf(
        MediaStore.Audio.Media._ID,
        MediaStore.Audio.Media.TITLE,
        MediaStore.Audio.Media.ARTIST,
        MediaStore.Audio.Media.DURATION,
        MediaStore.Audio.Media.SIZE
    )

    val selection = "${MediaStore.Audio.Media.IS_MUSIC} != 0"
    val sortOrder = "${MediaStore.Audio.Media.TITLE} ASC"

    context.contentResolver.query(
        collection,
        projection,
        selection,
        null,
        sortOrder
    )?.use { cursor ->
        val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
        val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
        val artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
        val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
        val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)

        while (cursor.moveToNext()) {
            val id = cursor.getLong(idColumn)
            val title = cursor.getString(titleColumn)
            val artist = cursor.getString(artistColumn)
            val duration = cursor.getLong(durationColumn)
            val size = cursor.getLong(sizeColumn)
            val contentUri: Uri = ContentUris.withAppendedId(
                collection,
                id
            )

            val durationMs = formatDuration(duration)
            val sizeKb = size / 1024

            audioMetadata.add(
                AudioMetadata(
                    title = title,
                    artist = artist,
                    durationMs = durationMs,
                    uri = contentUri,
                    image = ByteArray(0)
                )
            )
        }
    }
    return audioMetadata
}

/*class AudioReader(
    private val context: Context
) {
    fun getAudioFiles(): List<AudioFile> {
        val audioFile = mutableListOf<AudioFile>()
        val collection = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.SIZE
        )
        val selection = "${MediaStore.Audio.Media.IS_MUSIC} != 0"
        val selectionArgs = arrayOf(
            TimeUnit.MILLISECONDS.convert(5, TimeUnit.MINUTES).toString()
        )
        val sortOrder = "${MediaStore.Video.Media.DISPLAY_NAME} ASC"

        context.contentResolver.query(
            collection,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
            val artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
            val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
            val albumIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)
            val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val title = cursor.getString(titleColumn)
                val artist = cursor.getString(artistColumn)
                val duration = cursor.getLong(durationColumn)
                val albumId = cursor.getLong(albumIdColumn)
                val size = cursor.getLong(sizeColumn)

                val contentUri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id
                )

                val imageUri = ContentUris.withAppendedId(
                    Uri.parse("content://media/external/audio/*"),
                    albumId
                )

                val durationMs = formatDuration(duration)
                val sizeKb = size / 1024

                if (duration > 30_000 && sizeKb > 100)
                    audioFile.add(
                        AudioFile(
                            filePath = imageUri.path.toString(),
                            title = title,
                            artist = artist,
                            thumbnail = imageUri.toString().toByteArray(),
                            duration = durationMs.toLong()
                        )
                    )
            }
        }
        return audioFile
    }
}*/
*/

private fun formatDuration(milliSeconds: Long): String {
    val minutes = milliSeconds / 1000 / 60
    val seconds = milliSeconds / 1000 % 60
    return String.format("%02d:%02d", minutes, seconds)
}

