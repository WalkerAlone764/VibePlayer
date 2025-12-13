package com.upsidedowndev.vibeplayer.song.presentation.songs

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upsidedowndev.vibeplayer.song.domain.audio.getAudioFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SongsViewModel(
    private val context: Context
): ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(SongsState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                // load initial data
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = SongsState()
        )


    fun onAction(action: SongsAction) {
        when (action) {
            SongsAction.OnScanAgainButtonClick -> onScanAgainButtonClick()
        }
    }

    init {
        loadAudioMetadata()
    }

    private fun loadAudioMetadata(){
        val audioMetadata = getAudioFile(context)
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                it.copy(
                    isLoading = false,
                    listOfAudioMetadata = audioMetadata
                )
            }
        }
    }

    private fun onScanAgainButtonClick() {
        _state.update {
            it.copy(
                isLoading = true
            )
        }
    }

}
