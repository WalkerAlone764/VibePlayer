package com.upsidedowndev.vibeplayer.song.presentation.song.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.upsidedowndev.vibeplayer.song.presentation.song.component.SongPlayer
import com.upsidedowndev.vibeplayer.R
import com.upsidedowndev.vibeplayer.core.presentation.designSystem.theme.PrimaryText
import com.upsidedowndev.vibeplayer.core.presentation.designSystem.theme.SecondaryText
import com.upsidedowndev.vibeplayer.core.presentation.designSystem.theme.Surface
import com.upsidedowndev.vibeplayer.song.presentation.component.songCard.Song
import com.upsidedowndev.vibeplayer.song.presentation.component.topBar.PlayerTopBar
import com.upsidedowndev.vibeplayer.util.hostgroteskFamily

@Composable
fun PlayerScreen(
    song: Song,
    onSkipPrevious: () -> Unit,
    onPause: () -> Unit,
    onPlay: () -> Unit,
    onSkipNext: () -> Unit,
    modifier: Modifier = Modifier,
    progress: Float,
    isPlaying: Boolean
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            PlayerTopBar()
        },
        bottomBar = {
            SongPlayer(
                onSkipPrevious = onSkipPrevious,
                onPause = onPause,
                onPlay = onPlay,
                onSkipNext = onSkipNext,
                progress = progress,
                isPlaying = isPlaying,
                modifier = Modifier
                    .padding(bottom = 20.dp)
            )
        },
        contentWindowInsets = WindowInsets.safeContent,
        containerColor = Surface
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(song.songImage),
                contentDescription = null,
                modifier = Modifier
                    .size(320.dp),
                contentScale = ContentScale.FillBounds
            )
            Spacer(
                modifier = Modifier
                    .height(20.dp)
            )
            Text(
                text = song.songTitle,
                fontFamily = hostgroteskFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 28.sp,
                color = PrimaryText
            )
            Spacer(
                modifier = Modifier
                    .height(2.dp)
            )
            Text(
                text = song.songArtist,
                fontFamily = hostgroteskFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = SecondaryText
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PlayerScreenPreview() {
    PlayerScreen(
        song = Song(
            songId = 1,
            songImage = R.drawable.song_img,
            songTitle = "505",
            songArtist = "Arctic Monkeys",
            songDuration = "4:14"
        ),
        onSkipPrevious = {},
        onPause = {},
        onPlay = {},
        onSkipNext = {},
        progress = 0.7f,
        isPlaying = true
    )
}