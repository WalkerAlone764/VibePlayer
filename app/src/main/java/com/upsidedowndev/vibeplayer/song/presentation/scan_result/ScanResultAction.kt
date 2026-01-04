package com.upsidedowndev.vibeplayer.song.presentation.scan_result

sealed interface ScanResultAction {

    data class OnSelectSong(val songUrl: String): ScanResultAction

}