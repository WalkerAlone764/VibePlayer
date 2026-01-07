package com.upsidedowndev.vibeplayer.song.presentation.player

sealed interface MusicPlayerAction {

    data object OnClickBack: MusicPlayerAction

    data object OnClickStart: MusicPlayerAction
    data object OnClickPause: MusicPlayerAction
    data object OnClickResume: MusicPlayerAction
    data object OnClickNext: MusicPlayerAction
    data object OnClickPrevious: MusicPlayerAction
    data class OnSeek(val progress: Float): MusicPlayerAction
}