package com.upsidedowndev.vibeplayer.song.presentation.scan_result

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upsidedowndev.vibeplayer.song.domain.repository.AudioRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ScanResultViewModel(
    private val audioRepository: AudioRepository
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(ScanResultState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                loadData()
                observeAudioFiles()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = ScanResultState()
        )

    fun onAction(action: ScanResultAction) {
        when (action) {
            else -> TODO("Handle actions")
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            val files = audioRepository.getAudioFiles()
            Log.d("files", files.toString())
        }
    }

    private fun observeAudioFiles() {
            audioRepository
                .collectAudioFiles()
                .onStart {
                    _state.update {
                        it.copy(
                            isScanning = true
                        )
                    }
                }
                .map { audios ->
                    Log.d("Audios Viewmodel", audios.toString())
                    _state.update {
                        it.copy(
                            audios = audios
                        )
                    }
                    _state.update {
                        it.copy(
                            isScanning = false
                        )
                    }
                }
                .launchIn(viewModelScope)
    }

}