package com.upsidedowndev.vibeplayer.song.presentation.songScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upsidedowndev.vibeplayer.song.domain.audio.AudioFileRepository
import com.upsidedowndev.vibeplayer.song.presentation.models.ScanFilterState
import com.upsidedowndev.vibeplayer.song.presentation.songScreen.models.ScanningState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SongsViewModel(
    private val audioFileRepository: AudioFileRepository
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(SongsState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                scanFilter(ScanFilterState())
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = SongsState()
        )

    private val _eventChannel = Channel<SongsEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    fun onAction(action: SongsAction) {
        when (action) {
            SongsAction.OnScanAgainButtonClick -> onScanAgainButtonClick()
            SongsAction.OnScanFilerClick -> onScanFilerClick()
            SongsAction.OnFabClicked -> onFabClicked()
            is SongsAction.FirstVisibleItemIndexChanged -> firstVisibleItemIndexChanged(action.index)
        }
    }

    private fun onScanFilerClick() {
        viewModelScope.launch {
            _eventChannel.send(SongsEvent.NavigateToScanMusicScreen(ScanFilterState()))
        }
    }

    private fun firstVisibleItemIndexChanged(index: Int) {
        val isFabVisible = index > 9
        _state.update {
            it.copy(
                isFabVisible = isFabVisible
            )
        }
    }

    private fun onFabClicked() {
        viewModelScope.launch {
            _eventChannel.send(SongsEvent.ScrollToTop)
        }
    }

    private fun onScanAgainButtonClick() {
        scanFilter(ScanFilterState())
    }

    private fun scanFilter(filterState: ScanFilterState) {
        viewModelScope.launch {
            audioFileRepository.getAudioFiles(filterState)
                .collect { audioFiles ->
                    _state.update {
                        it.copy(
                            listOfAudioFiles = audioFiles,
                            scanningState = if (audioFiles.isEmpty()){
                                ScanningState.NOT_FOUND
                            }else {
                                ScanningState.FOUND
                            }
                        )
                    }
                    _eventChannel.send(SongsEvent.OnScanComplete(audioFiles.size))
                }
        }
    }

    /*private fun loadAllAudioFiles() {
        audioFileRepository.getAudioFiles().onEach { audioFiles ->
            _state.update {
                it.copy(
                    isLoading = false,
                    listOfAudioFiles = audioFiles
                )
            }
        }.launchIn(viewModelScope)
    }*/

}
