package com.upsidedowndev.vibeplayer.song.presentation.song

data class VibePlayerState(
    val hasPermissionGranted: Boolean = false,
    val isRequestingPermission: Boolean = false,
    val permissionDialogQueue: List<String> = emptyList()
)
