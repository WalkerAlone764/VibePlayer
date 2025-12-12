package com.upsidedowndev.vibeplayer.song.presentation.permission.use_cases

import com.upsidedowndev.vibeplayer.core.presentation.util.PermissionTextProvider


class ReadMediaAudioPermissionTextProvider: PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if(isPermanentlyDeclined) {
            "VibePlayer needs access to your music files to " +
                    "function properly. Without this permission, " +
                    "the app cannot build your music library or play " +
                    "songs."
        } else "VibePlayer needs access to your music\n" +
                "files to build your library and play songs."
    }
}