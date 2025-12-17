package com.upsidedowndev.vibeplayer.song.presentation.permission

import android.Manifest
import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upsidedowndev.vibeplayer.song.presentation.vibePlayer.VibePlayerEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PermissionViewModel : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(PermissionState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = PermissionState()
        )

    private val _eventChannel = Channel<PermissionEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    fun onAction(action: PermissionAction) {
        when (action) {
            PermissionAction.GrantPermissionClicked -> {
                if (!state.value.hasPermissionGranted) {
                    getRequiredPermission()
                } else {
                    navigateToSongScreen()
                }
            }

            PermissionAction.OnRationaleOkClicked,
            PermissionAction.OnRationaleTryAgainClicked -> {
                _state.update { it.copy(showRationaleDialog = false) }
            }

            is PermissionAction.OnPermissionResult -> {
                if (action.isGranted) {
                    _state.update { it.copy(hasPermissionGranted = true) }
                    navigateToSongScreen()
                } else {
                    _state.update { it.copy(showRationaleDialog = true) }
                }
            }
        }
    }

    private fun getRequiredPermission() = viewModelScope.launch {
        _eventChannel.send(PermissionEvent.LaunchPermissionRequest)
    }

    private fun navigateToSongScreen() = viewModelScope.launch {
        _eventChannel.send(PermissionEvent.NavigateToSongScreen)
    }

    /*private fun getRequiredPermission(): String {
        return if (Build.VERSION.SDK_INT >= 33) {
            Manifest.permission.READ_MEDIA_AUDIO
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }
    }*/

}