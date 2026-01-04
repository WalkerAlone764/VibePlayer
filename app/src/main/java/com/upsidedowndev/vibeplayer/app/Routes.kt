package com.upsidedowndev.vibeplayer.app

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Routes: NavKey {

    @Serializable
    data object Permission: Routes, NavKey

    @Serializable
    data object ScanResult: Routes, NavKey

    @Serializable
    data class MusicPlayer(val path: String): Routes, NavKey
}