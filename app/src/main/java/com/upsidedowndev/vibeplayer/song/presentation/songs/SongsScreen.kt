package com.upsidedowndev.vibeplayer.song.presentation.songs

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

import com.upsidedowndev.vibeplayer.R
import com.upsidedowndev.vibeplayer.core.presentation.designSystem.button.VibeFabButton
import com.upsidedowndev.vibeplayer.core.presentation.designSystem.theme.VibePlayerTheme
import com.upsidedowndev.vibeplayer.song.presentation.component.songCard.Song
import com.upsidedowndev.vibeplayer.song.presentation.component.songCard.SongCard
import com.upsidedowndev.vibeplayer.song.presentation.component.topBar.MainTopBar
import com.upsidedowndev.vibeplayer.song.presentation.songs.models.ScanningState

// Add this class to your SongsScreen.kt file or preferably in SongsViewModel.kt

class SongsViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SongsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SongsViewModel(context.applicationContext) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


@Composable
fun SongsRoot() {
    val context = LocalContext.current
    val viewModel: SongsViewModel = viewModel(
        factory = SongsViewModelFactory(context)
    )

    val state by viewModel.state.collectAsStateWithLifecycle()

    SongsScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun SongsScreen(
    state: SongsState,
    onAction: (SongsAction) -> Unit,
) {
    Scaffold(
        modifier = Modifier,
        topBar = {
            MainTopBar(
                onScanClick = {
                    // scan music screen open
                }
            )
        },
        floatingActionButton = {
            if (state.scanningState == ScanningState.FOUND) {
                VibeFabButton(
                    onClick = {
                        // scroll to top
                    }
                )
            }
        },
        contentWindowInsets = WindowInsets.safeDrawing,
        containerColor = Color(0xFFFFFF)
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {
                items(state.listOfAudioMetadata) { songCard ->
                    SongCard(
                        song = Song(
                            songId = songCard.durationMs.length,
                            songImage = R.drawable.song_default,
                            songTitle = songCard.title,
                            songArtist = songCard.artist,
                            songDuration = songCard.durationMs
                        )
                    )
                }
            }


            /*when (state.scanningState) {
                ScanningState.LOADING -> {
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
                }

                ScanningState.NOT_FOUND -> {
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
                }

                ScanningState.FOUND -> {
                    LazyColumn(
                        modifier = Modifier
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
            }*/
        }

    }
}

@Preview
@Composable
private fun Preview() {
    VibePlayerTheme {
        SongsScreen(
            state = SongsState(),
            onAction = {}
        )
    }
}