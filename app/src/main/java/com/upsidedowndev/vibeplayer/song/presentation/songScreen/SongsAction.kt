package com.upsidedowndev.vibeplayer.song.presentation.songScreen

sealed interface SongsAction {
    data object OnScanAgainButtonClick: SongsAction
    data object OnScanFilerClick: SongsAction
    data object OnFabClicked: SongsAction
    data class FirstVisibleItemIndexChanged(val index: Int): SongsAction
}