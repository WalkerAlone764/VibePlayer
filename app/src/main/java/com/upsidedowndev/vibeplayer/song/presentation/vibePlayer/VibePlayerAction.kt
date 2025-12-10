package com.upsidedowndev.vibeplayer.song.presentation.vibePlayer

sealed interface VibePlayerAction {
    data object GrantPermissionClicked : VibePlayerAction
    data object DismissPermissionDialog : VibePlayerAction
    data object OnRationaleOkClicked : VibePlayerAction
    data class OnPermissionResult(val isGranted: Boolean) : VibePlayerAction
}
