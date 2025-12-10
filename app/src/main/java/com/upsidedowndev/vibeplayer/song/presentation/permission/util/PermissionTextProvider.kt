package com.upsidedowndev.vibeplayer.song.presentation.permission.util

interface PermissionTextProvider {
    fun getDescription(isPermanentlyDeclined: Boolean): String
}