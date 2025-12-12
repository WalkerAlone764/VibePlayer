package com.upsidedowndev.vibeplayer.song.presentation.permission


sealed interface PermissionAction {
    data object GrantPermissionClicked : PermissionAction
    data object DismissPermissionDialog : PermissionAction
    data object OnRationaleOkClicked : PermissionAction
    data class OnPermissionResult(val isGranted: Boolean) : PermissionAction


}