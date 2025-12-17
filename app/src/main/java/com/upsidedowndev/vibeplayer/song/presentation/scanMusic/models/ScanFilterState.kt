package com.upsidedowndev.vibeplayer.song.presentation.scanMusic.models

data class ScanFilterState(
    val ignoreDurationLessThanMs: Long = 0L,
    val ignoreSizeLessThanKb: Long = 0L
)