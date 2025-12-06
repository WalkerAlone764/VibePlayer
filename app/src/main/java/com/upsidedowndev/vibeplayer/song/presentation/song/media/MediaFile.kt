package com.upsidedowndev.vibeplayer.song.presentation.song.media

import android.net.Uri

data class MediaFile(
    val uri: Uri,
    val name: String,
    val type: MediaType
)
