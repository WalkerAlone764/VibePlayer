package com.upsidedowndev.vibeplayer.song.presentation.permission

data class PermissionState(
    val hasPermissionGranted: Boolean = false,
    val isRequestingPermission: Boolean = false,
    val permissionDialogQueue: List<String> = emptyList()
)