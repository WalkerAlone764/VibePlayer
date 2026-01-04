package com.upsidedowndev.vibeplayer.song.presentation.scan_result

import com.upsidedowndev.vibeplayer.song.domain.audio.AudioMetadata


data class ScanResultState(
    val isScanning: Boolean = true,
    val audios: List<AudioMetadata> = emptyList()

)