package com.upsidedowndev.vibeplayer.song.presentation.player

import com.upsidedowndev.vibeplayer.song.domain.model.SongMetadata
import com.upsidedowndev.vibeplayer.song.presentation.player.model.PlaybackState
import kotlin.time.Duration

data class MusicPlayerState(
    val songDetails: SongMetadata,
    val playerState: PlaybackState = PlaybackState.STOPPED,
    val playedDuration: Duration = Duration.ZERO,
    val totalDuration: Duration = Duration.ZERO,
    val hasActiveMusic: Boolean = false

) {
    val progress: Float
        get() = if (totalDuration > Duration.ZERO) {
            (playedDuration.inWholeMilliseconds / totalDuration.inWholeMilliseconds.toFloat())
        } else 0f
}