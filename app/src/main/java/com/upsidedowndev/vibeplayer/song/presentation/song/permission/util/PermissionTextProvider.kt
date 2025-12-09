package com.upsidedowndev.vibeplayer.song.presentation.song.permission.util

interface PermissionTextProvider {
    fun getDescription(isPermanentlyDeclined: Boolean): String
}