package com.upsidedowndev.vibeplayer.song.presentation.list

data class ListState(
    val paramOne: String = "default",
    val paramTwo: List<String> = emptyList(),
)