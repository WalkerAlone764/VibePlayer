package com.upsidedowndev.vibeplayer.song.data.audio

import android.content.ContentUris
import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.provider.MediaStore
import com.upsidedowndev.vibeplayer.song.presentation.scanMusic.models.ScanFilterState

fun getAudioFilesFromMediaStore(
    context: Context,
    filters: ScanFilterState
): List<AudioMetadataDto> {
    val audioMetadata = mutableListOf<AudioMetadataDto>()
    val collection = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
    val projection = arrayOf(
        MediaStore.Audio.Media._ID,
        MediaStore.Audio.Media.TITLE,
        MediaStore.Audio.Media.ARTIST,
        MediaStore.Audio.Media.DURATION,
        MediaStore.Audio.Media.SIZE
    )

//    val selection = "${MediaStore.Audio.Media.IS_MUSIC} != 0"

    val selection = mutableListOf<String>()
    val selectionArgs = mutableListOf<String>()

    selection.add("${MediaStore.Audio.Media.IS_MUSIC} != 0")

    if (filters.ignoreDurationLessThanMs > 0) {
        selection.add("${MediaStore.Audio.Media.DURATION} > ?")
        selectionArgs.add(filters.ignoreDurationLessThanMs.toString())
    }

    if (filters.ignoreSizeLessThanKb > 0) {
        selection.add("${MediaStore.Audio.Media.SIZE} > ?")
        selectionArgs.add((filters.ignoreSizeLessThanKb * 1024).toString())
    }

    val sortOrder = "${MediaStore.Audio.Media.TITLE} ASC"

    context.contentResolver.query(
        collection,
        projection,
        selection.joinToString(" AND "),
        selectionArgs.toTypedArray(),
        sortOrder
    )?.use { cursor ->
        val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
//        val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
//        val artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
//        val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
        val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)

        while (cursor.moveToNext()) {
            val id = cursor.getLong(idColumn)
//            val title = cursor.getString(titleColumn)
//            val artist = cursor.getString(artistColumn)
//            val duration = cursor.getLong(durationColumn)
            val contentUri: Uri = ContentUris.withAppendedId(
                collection,
                id
            )

            val size = cursor.getLong(sizeColumn)
            val sizeKb = size / 1024

            val retriever = MediaMetadataRetriever()
            try {
                retriever.setDataSource(context, contentUri)

                val title =
                    retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)
                        ?: contentUri.lastPathSegment ?: "Unknown Title"
                val artist =
                    retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)
                        ?: "Unknown Artist"
                val image =
                    retriever.embeddedPicture ?: ByteArray(0)

                val durationString =
                    retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
                val durationMs = formatDuration(durationString?.toLongOrNull() ?: 0L)

                audioMetadata.add(
                    AudioMetadataDto(
                        id = id,
                        title = title,
                        artist = artist,
                        durationMs = durationMs,
                        uri = contentUri,
                        image = image
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                retriever.release()
            }
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

