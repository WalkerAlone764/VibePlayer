package com.upsidedowndev.vibeplayer.core.database.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.upsidedowndev.vibeplayer.core.database.data.entity.AudioEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AudioMetadataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(audioEntity: AudioEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(audioMetadataEntities: List<AudioEntity>)

    @Query("SELECT * FROM audio_metadata")
    fun getAll(): Flow<List<AudioEntity>>

    @Query("DELETE FROM audio_metadata")
    suspend fun deleteAll()
}
