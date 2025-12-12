package com.upsidedowndev.vibeplayer.core.presentation.util

interface PermissionTextProvider {
    fun getDescription(isPermanentlyDeclined: Boolean): String
}