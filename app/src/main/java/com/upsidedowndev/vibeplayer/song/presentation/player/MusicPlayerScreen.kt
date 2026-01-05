package com.upsidedowndev.vibeplayer.song.presentation.player

import android.graphics.BitmapFactory
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.upsidedowndev.vibeplayer.R
import com.upsidedowndev.vibeplayer.song.presentation.player.components.MediaControls
import com.upsidedowndev.vibeplayer.song.presentation.player.components.PlayerTopBar
import com.upsidedowndev.vibeplayer.song.presentation.player.model.PlaybackState
import com.upsidedowndev.vibeplayer.util.hostgroteskFamily
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun MusicPlayerRoot(
    audioPath: String,
    onGoBack: () -> Unit,
    viewModel: MusicPlayerViewModel = koinViewModel(
        parameters = { parametersOf(audioPath) }
    )
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    MusicPlayerScreen(
        state = state, onAction = { action ->
            when(action) {
                is MusicPlayerAction.OnClickBack -> onGoBack()
                else -> viewModel.onAction(action)
            }
        }
    )
}

@Composable
fun MusicPlayerScreen(
    state: MusicPlayerState,
    onAction: (MusicPlayerAction) -> Unit,
) {

    Scaffold(
        modifier = Modifier,
        topBar = {
            PlayerTopBar(
                onBackClick = {
                    onAction(MusicPlayerAction.OnClickBack)
                }
            )
        },
        bottomBar = {
            MediaControls(
                isPlaying = state.playerState == PlaybackState.PLAYING,
                progress = state.progress,
                onSeek = {
                    onAction(MusicPlayerAction.OnSeek(it))
                },
                onPause = {
                    onAction(MusicPlayerAction.OnClickPause)
                },
                onPlay = {
                    if (state.playerState == PlaybackState.PLAYING) {
                        onAction(MusicPlayerAction.OnClickPause)
                    } else {
                        if (state.hasActiveMusic) {
                            onAction(MusicPlayerAction.OnClickResume)
                        } else {
                            onAction(MusicPlayerAction.OnClickStart)
                        }
                    }
                },
                onSkipPrevious = {
                    onAction(MusicPlayerAction.OnClickPrevious)
                },
                onSkipNext = {
                    onAction(MusicPlayerAction.OnClickNext)
                },

                modifier = Modifier
                    .padding(bottom = 20.dp)
            )
        },
        contentWindowInsets = WindowInsets.safeContent,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            state.songDetails.thumbnail?.let { thumbnail ->
                val bitmap = BitmapFactory.decodeByteArray(thumbnail, 0, thumbnail.size)
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "Song thumbnail",
                    modifier = Modifier
                        .size(320.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.FillBounds
                )
            } ?: Image(
                painter = painterResource(id = R.drawable.album_default_image),
                contentDescription = "Song thumbnail",
                modifier = Modifier
                    .size(320.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.FillBounds
            )

            Spacer(
                modifier = Modifier
                    .height(20.dp)
            )
            Text(
                text = state.songDetails.title ?: "Vibe",
                fontFamily = hostgroteskFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 28.sp,
            )
            Spacer(
                modifier = Modifier
                    .height(2.dp)
            )
            Text(
                text = state.songDetails.artist ?: "404 Artist",
                fontFamily = hostgroteskFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }

}

