package com.upsidedowndev.vibeplayer.core.presentation.util

fun formatDuration(milliSeconds: Long): String {
    val minutes = milliSeconds / 1000 / 60
    val seconds = milliSeconds / 1000 % 60
    return String.format("%02d:%02d", minutes, seconds)
}