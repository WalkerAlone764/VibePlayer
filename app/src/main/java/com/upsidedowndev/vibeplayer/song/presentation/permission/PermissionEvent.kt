package com.upsidedowndev.vibeplayer.song.presentation.permission

sealed interface PermissionEvent {
    data object LaunchPermissionRequest : PermissionEvent
    data object NavigateToSongScreen: PermissionEvent
}