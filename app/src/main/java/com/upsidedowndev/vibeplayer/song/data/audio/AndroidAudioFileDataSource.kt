package com.upsidedowndev.vibeplayer.song.data.audio

import android.content.ContentUris
import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.upsidedowndev.vibeplayer.song.data.audio.mappers.toDomain
import com.upsidedowndev.vibeplayer.song.domain.audio.AudioFileDataSource
import com.upsidedowndev.vibeplayer.song.domain.audio.AudioMetadata
import java.io.ByteArrayOutputStream
import java.io.File

class AndroidAudioFileDataSource(
    private val context: Context
) : AudioFileDataSource {
    override fun getAudioFiles(): List<AudioMetadata> {
        val audioMetadataDto = mutableListOf<AudioMetadataDto>()
        val collection = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.DISPLAY_NAME
        )

        val selection = "${MediaStore.Audio.Media.IS_MUSIC} = ?"
        val selectionArgs = arrayOf("1")
        val sortOrder = "${MediaStore.Audio.Media.TITLE} ASC"

        try {
            val audioDir = File(context.filesDir, "audio")
            if (!audioDir.exists()) {
                audioDir.mkdirs()
            }

            return context.contentResolver.query(
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
                val displayNameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)

                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val title = cursor.getString(titleColumn)
                    val artist = cursor.getString(artistColumn)
                    val duration = cursor.getLong(durationColumn)
                    val albumId = cursor.getLong(albumIdColumn)
                    val displayName = cursor.getString(displayNameColumn)

                    val contentUri: Uri = ContentUris.withAppendedId(
                        collection,
                        id
                    )

                    val filePath = try {
                        val destinationFile = File(audioDir, "$id-${displayName.replace("/", "_")}")
                        if (destinationFile.exists()) {
                            destinationFile.absolutePath
                        } else {
                            context.contentResolver.openInputStream(contentUri)?.use { inputStream ->
                                destinationFile.outputStream().use { outputStream ->
                                    inputStream.copyTo(outputStream)
                                }
                            }
                            destinationFile.absolutePath
                        }
                    } catch (e: Exception) {
                        Log.e("AudioFileDataSource", "Error copying audio file: $displayName", e)
                        null
                    }

                    if (filePath == null) {
                        continue
                    }

                    val albumArtUri = ContentUris.withAppendedId(
                        Uri.parse("content://media/external/audio/albumart"),
                        albumId
                    )

                    val image = try {
                        context.contentResolver.openInputStream(albumArtUri)?.use { inputStream ->
                            // Get the dimensions of the bitmap
                            val options = BitmapFactory.Options().apply { inJustDecodeBounds = true }
                            BitmapFactory.decodeStream(inputStream, null, options)

                            // Calculate inSampleSize
                            options.inSampleSize = calculateInSampleSize(options, 128, 128)

                            // Decode bitmap with inSampleSize set
                            options.inJustDecodeBounds = false

                            context.contentResolver.openInputStream(albumArtUri)?.use { scaledInputStream ->
                                val bitmap = BitmapFactory.decodeStream(scaledInputStream, null, options)
                                val stream = ByteArrayOutputStream()
                                bitmap?.compress(android.graphics.Bitmap.CompressFormat.JPEG, 80, stream)
                                stream.toByteArray()
                            } ?: ByteArray(0)
                        } ?: ByteArray(0)
                    } catch (e: Exception) {
                        ByteArray(0)
                    }

                    val audioFile = AudioMetadataDto(
                        title = title,
                        artist = artist,
                        duration = duration,
                        thumbnail = image,
                        filePath = filePath
                    )
                    audioMetadataDto.add(
                        audioFile
                    )
                }

                return audioMetadataDto.map { it.toDomain() }
            } ?: emptyList<AudioMetadata>()
        } catch (e: Exception) {
            Log.e("AudioFileDataSource", "Error getting audio files", e)
            return emptyList()

        }
    }

    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val (height: Int, width: Int) = options.outHeight to options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }
}
