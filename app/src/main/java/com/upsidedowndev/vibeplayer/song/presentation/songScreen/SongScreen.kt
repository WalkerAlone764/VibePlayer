package com.upsidedowndev.vibeplayer.song.presentation.songScreen

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
import com.upsidedowndev.vibeplayer.song.presentation.component.songCard.Song
import com.upsidedowndev.vibeplayer.song.presentation.component.songCard.SongCard
import kotlin.random.Random

@Composable
fun SongScreen(
    onScanClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
        },
        floatingActionButton = {
            VibeFabButton(
                onClick = {
                    // scroll to top
                }
            )
        },
        contentWindowInsets = WindowInsets.safeDrawing,
//        containerColor = Surface
    ) { paddingValues ->

        // 1 loader
        /*Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LoaderRadar(
                size = 120.dp
            )
            Spacer(
                modifier = Modifier
                    .height(20.dp)
            )
            Text(
                text = "Scanning your device for music...",
                fontFamily = hostgroteskFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = SecondaryText,
                textAlign = TextAlign.Center
            )
        }*/

        // 2 scan again
        /*Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "No music found",
                fontFamily = hostgroteskFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 28.sp,
                color = PrimaryText
            )
            Spacer(
                modifier = Modifier
                    .height(4.dp)
            )
            Text(
                text = "Try scanning again or check your folders.",
                fontFamily = hostgroteskFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = SecondaryText,
                textAlign = TextAlign.Center
            )
            Spacer(
                modifier = Modifier
                    .height(20.dp)
            )
            VibeButton(
                text = "Scan Again",
                onClick = {},
                isEnabled = true,
                isPressed = false
            )
        }*/

        // 3 song result
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