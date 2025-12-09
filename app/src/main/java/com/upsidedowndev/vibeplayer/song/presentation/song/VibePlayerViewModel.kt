package com.upsidedowndev.vibeplayer.song.presentation.song

import android.Manifest
import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class VibePlayerViewModel : ViewModel() {
    private val _state = MutableStateFlow(VibePlayerState())
    val state = _state.asStateFlow()

    private val _eventChannel = Channel<VibePlayerEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    fun onAction(action: VibePlayerAction) {
        when (action) {
            VibePlayerAction.GrantPermissionClicked -> {
                if (!state.value.hasPermissionGranted) {
                    viewModelScope.launch {
                        _eventChannel.send(VibePlayerEvent.LaunchPermissionRequest)
                    }
                }else{
                    // navigation to song screen
                }
            }

            VibePlayerAction.DismissPermissionDialog -> {
                _state.update {
                    it.copy(
                        permissionDialogQueue = it.permissionDialogQueue.drop(1)
                    )
                }
            }

            VibePlayerAction.OnRationaleOkClicked -> {
                _state.update {
                    it.copy(
                        permissionDialogQueue = it.permissionDialogQueue.drop(1)
                    )
                }
                /*viewModelScope.launch {
                    _eventChannel.send(VibePlayerEvent.LaunchPermissionRequest)
                }*/
            }

            is VibePlayerAction.OnPermissionResult -> {
                if (!action.isGranted) {
                    _state.update {
                        it.copy(
                            permissionDialogQueue = it.permissionDialogQueue
                                    + getRequiredPermission()
                        )
                    }
                }
                _state.update {
                    it.copy(
                        hasPermissionGranted = action.isGranted
                    )
                }
            }
        }
    }

    private fun getRequiredPermission(): String {
        return if (Build.VERSION.SDK_INT >= 33) {
            Manifest.permission.READ_MEDIA_AUDIO
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }
    }
}

