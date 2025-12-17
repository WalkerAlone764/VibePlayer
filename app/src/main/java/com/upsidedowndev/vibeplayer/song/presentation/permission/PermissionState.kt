package com.upsidedowndev.vibeplayer.song.presentation.permission

data class PermissionState(
    val hasPermissionGranted: Boolean = false,
    val showRationaleDialog: Boolean = false
)