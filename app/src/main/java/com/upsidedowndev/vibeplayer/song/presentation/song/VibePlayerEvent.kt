package com.upsidedowndev.vibeplayer.song.presentation.song

sealed interface VibePlayerEvent {
    data object LaunchPermissionRequest : VibePlayerEvent
    data object OpenSettings: VibePlayerEvent
}