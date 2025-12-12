package com.upsidedowndev.vibeplayer.song.presentation.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upsidedowndev.vibeplayer.song.domain.audio.AudioPlayer
import com.upsidedowndev.vibeplayer.song.domain.audio.SongMetadataReader
import com.upsidedowndev.vibeplayer.song.presentation.player.model.PlaybackState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class  MusicPlayerViewModel(
    private val audioPlayer: AudioPlayer,
    private val songMetadataReader: SongMetadataReader
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(MusicPlayerState(
        songDetails = songMetadataReader.getMetadata("/data/data/com.upsidedowndev.vibeplayer/files/saphire.mp3")
    ))
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = MusicPlayerState(
                songMetadataReader.getMetadata("/data/data/com.upsidedowndev.vibeplayer/files/saphire.mp3")
            )
        )

    init {

        audioPlayer
            .activeTrack
            .onEach { activeTrack ->
               if (activeTrack == null) {
                   _state.update { it.copy(
                       hasActiveMusic = false,
                   ) }
                   return@onEach
               }
                _state.update { it.copy(
                    hasActiveMusic = true,
                    playerState = if(activeTrack.isPlaying) PlaybackState.PLAYING else PlaybackState.PAUSED,
                    playedDuration = activeTrack.durationPlayed,
                    totalDuration = activeTrack.totalDuration
                ) }
            }

            .launchIn(viewModelScope)
    }

    fun onAction(action: MusicPlayerAction) {
        when (action) {
            MusicPlayerAction.OnClickStart -> onClickStart()
            MusicPlayerAction.OnClickPause -> onClickPause()
            MusicPlayerAction.OnClickResume -> onClickResume()
        }
    }

    private fun onClickResume() {
        audioPlayer.resume()

    }

    private fun onClickPause() {
        audioPlayer.pause()
//        _state.update { it.copy(
//            playerState = PlaybackState.PAUSED
//        ) }
    }

    private fun onClickStart() {
        audioPlayer.play(
            _state.value.songDetails.filePath,
            onComplete = {
                _state.update { it.copy(
                    playerState = PlaybackState.STOPPED
                ) }
            }
        )
    }

}