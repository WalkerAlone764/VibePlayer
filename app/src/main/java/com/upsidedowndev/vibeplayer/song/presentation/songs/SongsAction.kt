package com.upsidedowndev.vibeplayer.song.presentation.songs

sealed interface SongsAction {
    data object OnScanAgainButtonClick: SongsAction
//    data object OnScanFilerClick: SongsAction
}