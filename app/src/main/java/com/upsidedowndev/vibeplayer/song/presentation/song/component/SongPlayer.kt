package com.upsidedowndev.vibeplayer.song.presentation.song.component

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.upsidedowndev.vibeplayer.core.presentation.designSystem.theme.Pause
import com.upsidedowndev.vibeplayer.core.presentation.designSystem.theme.Play
import com.upsidedowndev.vibeplayer.core.presentation.designSystem.theme.PrimaryText
import com.upsidedowndev.vibeplayer.core.presentation.designSystem.theme.SecondaryText
import com.upsidedowndev.vibeplayer.core.presentation.designSystem.theme.Skip_Next
import com.upsidedowndev.vibeplayer.core.presentation.designSystem.theme.Skip_Previous
import com.upsidedowndev.vibeplayer.core.presentation.designSystem.theme.Surface
import com.upsidedowndev.vibeplayer.song.util.IconContainer

@Composable
fun SongPlayer(
    onSkipPrevious: () -> Unit,
    onPause: () -> Unit,
    onPlay: () -> Unit,
    onSkipNext: () -> Unit,
    modifier: Modifier = Modifier,
    progress: Float,
    isPlaying: Boolean
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .background(Surface)
    ) {
        Box {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                color = PrimaryText,
                trackColor = SecondaryText.copy(0.5f),
                progress = { progress }
            )
        }
        Spacer(
            modifier = Modifier
                .height(4.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            IconContainer(
                onClick = onSkipPrevious,
                modifier = Modifier,
                icon = Icons.Filled.Skip_Previous,
                contentDescription = "Skip Previous"
            )
            Crossfade(
                targetState = isPlaying,
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearEasing
                )
            ) { playing ->
                if (playing) {
                    IconContainer(
                        onClick = onPause,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .size(60.dp),
                        icon = Icons.Filled.Pause,
                        contentDescription = "Pause",
                        backgroundColor = PrimaryText,
                        iconTint = Surface,
                        iconSize = 24.dp
                    )
                } else {
                    IconContainer(
                        onClick = onPlay,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .size(60.dp),
                        icon = Icons.Filled.Play,
                        contentDescription = "Play",
                        backgroundColor = PrimaryText,
                        iconTint = Surface,
                        iconSize = 24.dp
                    )
                }
            }
            IconContainer(
                onClick = onSkipNext,
                modifier = Modifier,
                icon = Icons.Filled.Skip_Next,
                contentDescription = "Skip Next"
            )

        }
    }

}

@Preview
@Composable
private fun SongPlayerPreview() {
    val isPlaying = remember { mutableStateOf(false) }
    SongPlayer(
        onSkipPrevious = {},
        onPause = { isPlaying.value = false },
        onPlay = { isPlaying.value = true },
        onSkipNext = {},
        progress = 0.7f,
        isPlaying = isPlaying.value
    )
}