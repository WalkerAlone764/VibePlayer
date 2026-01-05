package com.upsidedowndev.vibeplayer.song.presentation.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upsidedowndev.vibeplayer.song.domain.audio.AudioMetadata
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

class  MusicPlayerViewModel(
    private val audioPath: String,
    private val audioPlayer: AudioPlayer,
    private val songMetadataReader: SongMetadataReader,
    private val repository: AudioRepository
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val selectedAudioIndex = MutableStateFlow(0)

    private val audioFiles = MutableStateFlow(emptyList<AudioMetadata>())


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

        repository
            .collectAudioFiles()
            .onEach { audios ->
                audioFiles.update { audios }
                val metaData = audios.firstOrNull { it.filePath == audioPath }
                if (metaData != null) {
                    selectedAudioIndex.update { audios.indexOf(metaData) }
                }
            }

            .launchIn(viewModelScope)

        audioPlayer
            .activeTrack
            .onEach { activeTrack ->
                if (activeTrack == null) {
                    _state.update {
                        it.copy(
                            hasActiveMusic = false,
                        )
                    }
                    return@onEach
                }

                if (activeTrack.filePath != state.value.songDetails.filePath) {
                    if (activeTrack.isPlaying) {
                        audioPlayer.stop()
                        audioPlayer.play(state.value.songDetails.filePath, onComplete = { onComplete() })
                    }
                    return@onEach
                }

                val metaData = audioFiles.value.firstOrNull { it.filePath == state.value.songDetails.filePath }

                if (metaData != null) {
                    selectedAudioIndex.update { audioFiles.value.indexOf(metaData) }
                    _state.update {
                        it.copy(
                            hasActiveMusic = true,
                            playerState = if (activeTrack.isPlaying) PlaybackState.PLAYING else PlaybackState.PAUSED,
                            playedDuration = activeTrack.durationPlayed,
                            totalDuration = activeTrack.totalDuration,
                            songDetails = songMetadataReader.getMetadata(activeTrack.filePath)
                        )
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: MusicPlayerAction) {
        when (action) {
            MusicPlayerAction.OnClickBack -> Unit
            MusicPlayerAction.OnClickStart -> onClickStart()
            MusicPlayerAction.OnClickPause -> onClickPause()
            MusicPlayerAction.OnClickResume -> onClickResume()
            MusicPlayerAction.OnClickNext -> onClickNext()
            MusicPlayerAction.OnClickPrevious -> onClickPrevious()
            is MusicPlayerAction.OnSeek -> onSeek(action.progress)
        }
    }

    private fun onSeek(progress: Float) {
        val newPosition = (progress * state.value.totalDuration.inWholeMilliseconds)
        if (state.value.playerState != PlaybackState.PLAYING) {
            audioPlayer.play(state.value.songDetails.filePath, onComplete = {
                onComplete()
            })
        }
        audioPlayer.seekTo(newPosition.toInt())
    }

    private fun onClickNext() {
        audioPlayer.stop()
        val nextIndex = selectedAudioIndex.value + 1
        if (nextIndex >= audioFiles.value.size) {
            _state.update { it.copy(
                playerState = PlaybackState.STOPPED,
                hasActiveMusic = false
            ) }
            return
        }
        val nextAudioFile = audioFiles.value[nextIndex]
        _state.update { it.copy(
            songDetails = nextAudioFile
        ) }
        audioPlayer.play(
            nextAudioFile.filePath,
            onComplete = { onComplete() }
        )
    }

    private fun onClickPrevious() {
        audioPlayer.stop()
        val prevIndex = selectedAudioIndex.value - 1
        if (prevIndex < 0) {
            _state.update { it.copy(
                playerState = PlaybackState.STOPPED,
                hasActiveMusic = false
            ) }
            return
        }

        val prevAudioFile = audioFiles.value[prevIndex]
        _state.update { it.copy(
            songDetails = prevAudioFile
        ) }
        audioPlayer.play(
            prevAudioFile.filePath,
            onComplete = { onComplete() }
        )
    }

    private fun onClickResume() {
        audioPlayer.resume()

    }

    private fun onClickPause() {
        audioPlayer.pause()
    }

    private fun onClickStart() {
        audioPlayer.play(
            _state.value.songDetails.filePath,
            onComplete = {
                onComplete()
            }
        )
    }

    private fun onComplete() {
        val nextIndex = selectedAudioIndex.value + 1
        if (nextIndex >= audioFiles.value.size) {
            audioPlayer.stop()
            _state.update { it.copy(
                playerState = PlaybackState.STOPPED,
                hasActiveMusic = false
            ) }
            return
        }
        val nextAudioFile = audioFiles.value[nextIndex]

        audioPlayer.stop()
        audioPlayer.play(nextAudioFile.filePath, onComplete = { onComplete() })
    }

}
