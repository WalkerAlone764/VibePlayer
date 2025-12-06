package com.upsidedowndev.vibeplayer.song.presentation.song.screen

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.upsidedowndev.vibeplayer.R
import com.upsidedowndev.vibeplayer.core.presentation.designSystem.button.VibeFabButton
import com.upsidedowndev.vibeplayer.core.presentation.designSystem.theme.Surface
import com.upsidedowndev.vibeplayer.song.presentation.component.songCard.Song
import com.upsidedowndev.vibeplayer.song.presentation.component.songCard.SongCard
import com.upsidedowndev.vibeplayer.song.presentation.component.topBar.MainTopBar
import kotlin.random.Random

@Composable
fun SongScreen(
    onScanClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            MainTopBar(
                onScanClick = onScanClick
            )
        },
        floatingActionButton = {
            VibeFabButton(
                onClick = {
                    // scroll to top
                }
            )
        },
        contentWindowInsets = WindowInsets.safeDrawing,
        containerColor = Surface
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            items(100) { songCard ->
                val randomImage = if (Random.nextBoolean()) {
                    R.drawable.song_img
                } else {
                    R.drawable.song_default
                }
                SongCard(
                    song = Song(
                        songId = songCard,
                        songImage = randomImage,
                        songTitle = "505",
                        songArtist = "Arctic Monkey",
                        songDuration = "4:14"
                    )
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SongScreenPreview() {
    SongScreen(
        onScanClick = {}
    )
}