package com.upsidedowndev.vibeplayer.song.presentation.player

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upsidedowndev.vibeplayer.song.domain.audio.AudioPlayer
import com.upsidedowndev.vibeplayer.song.domain.audio.SongMetadataReader
import com.upsidedowndev.vibeplayer.song.domain.repository.AudioRepository
import com.upsidedowndev.vibeplayer.song.presentation.player.model.PlaybackState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class  MusicPlayerViewModel(
    private val audioPath: String,
    private val audioPlayer: AudioPlayer,
    private val songMetadataReader: SongMetadataReader
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(MusicPlayerState(
        songDetails = songMetadataReader.getMetadata(audioPath)
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
                songMetadataReader.getMetadata(audioPath)
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

                if (activeTrack.filePath == audioPath) {
                    _state.update { it.copy(
                        hasActiveMusic = true,
                        playerState = if(activeTrack.isPlaying) PlaybackState.PLAYING else PlaybackState.PAUSED,
                        playedDuration = activeTrack.durationPlayed,
                        totalDuration = activeTrack.totalDuration
                    ) }
                } else {
                    _state.update { it.copy(
                        hasActiveMusic = false,
                    ) }
                    audioPlayer.stop()
                    onClickStart()
                }

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