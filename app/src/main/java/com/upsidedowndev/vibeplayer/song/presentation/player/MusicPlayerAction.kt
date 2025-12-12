package com.upsidedowndev.vibeplayer.song.presentation.player

sealed interface MusicPlayerAction {

    data object OnClickStart: MusicPlayerAction
    data object OnClickPause: MusicPlayerAction
    data object OnClickResume: MusicPlayerAction
}