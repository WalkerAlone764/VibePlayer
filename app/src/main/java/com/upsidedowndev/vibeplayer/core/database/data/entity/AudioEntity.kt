package com.upsidedowndev.vibeplayer.core.database.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "audio_metadata")
data class AudioEntity(
    @PrimaryKey val uriString: String,
)
