package com.upsidedowndev.vibeplayer.core.database.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.upsidedowndev.vibeplayer.core.database.data.dao.AudioMetadataDao
import com.upsidedowndev.vibeplayer.core.database.data.entity.AudioEntity

@Database(
    entities = [AudioEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AudioDatabase : RoomDatabase() {

    abstract fun audioMetadataDao(): AudioMetadataDao
}
