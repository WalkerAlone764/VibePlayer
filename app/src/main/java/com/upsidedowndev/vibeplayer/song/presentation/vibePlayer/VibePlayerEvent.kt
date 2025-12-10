package com.upsidedowndev.vibeplayer.song.presentation.vibePlayer

sealed interface VibePlayerEvent {
    data object LaunchPermissionRequest : VibePlayerEvent
    data object OpenSettings: VibePlayerEvent
}