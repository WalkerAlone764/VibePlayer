package com.upsidedowndev.vibeplayer.app.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route: NavKey {
    @Serializable
    data object PermissionScreen: Route

    @Serializable
    data object SongScreen: Route

    @Serializable
    data object ScanMusicScreen: Route

    @Serializable
    data object PlayerScreen: Route
}