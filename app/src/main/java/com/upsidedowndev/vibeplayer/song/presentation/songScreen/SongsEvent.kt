package com.upsidedowndev.vibeplayer.song.presentation.songScreen

import com.upsidedowndev.vibeplayer.song.presentation.models.ScanFilterState

sealed interface SongsEvent {
    data object ScrollToTop: SongsEvent
    data class NavigateToScanMusicScreen(val filter: ScanFilterState): SongsEvent
    data class OnScanComplete(val songsCount: Int): SongsEvent
}