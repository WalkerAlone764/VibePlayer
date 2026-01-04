package com.upsidedowndev.vibeplayer.song.domain.audio

import kotlin.time.Duration

data class AudioTrack(
    val filePath: String,
    val totalDuration: Duration = Duration.ZERO,
    val durationPlayed: Duration = Duration.ZERO,
    val isPlaying: Boolean = false
)