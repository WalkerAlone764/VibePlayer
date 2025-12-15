package com.upsidedowndev.vibeplayer.song.presentation.scan_result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ScanResultViewModel : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(ScanResultState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                loadData()
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
            delay(2000)
            _state.update {
                it.copy(
                    isScanning = false
                )
            }

        }
    }

}